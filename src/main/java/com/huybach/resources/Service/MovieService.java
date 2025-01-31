/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.huybach.resources.Service;

import com.huybach.resources.Model.Episode;
import com.huybach.resources.Model.Movie;
import com.huybach.resources.Model.Response;
import com.huybach.resources.Service.repo.MovieJDBCTemplate;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 *
 * @author HOME PC
 */
@Service
public class MovieService {

    MovieJDBCTemplate movieDb;

    @Autowired
    public void setMovieDb(MovieJDBCTemplate movieDb) {
        this.movieDb = movieDb;
    }

    public ResponseEntity<Response> getTrendingMovies() {
        try {
            List<Movie> result = movieDb.getTrendingMovies();
            return ResponseEntity.status(200).body(new Response(200, "trending movies", result));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new Response(500, e.getMessage(), null));
        }
    }

    public ResponseEntity<Response> getLatestMoviesByCategory(String category) {
        try {
            List<Movie> result = movieDb.getLatestMoviesByCategory(category);
            return ResponseEntity.status(200).body(new Response(200, "ok latest movies by category", result));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new Response(500, e.getMessage(), null));
        }
    }

    public ResponseEntity<Response> loadHomePage() {
        try {
            List<Movie> trending = movieDb.getTrendingMovies();
            List<Movie> latestBo = movieDb.getLatestMoviesByCategory("Phim bộ");
            List<Movie> latestLe = movieDb.getLatestMoviesByCategory("Phim lẻ");
            List<Object> result = new ArrayList<>();
            result.add(trending);
            result.add(latestBo);
            result.add(latestLe);
            return ResponseEntity.status(200).body(new Response(200, "load successfully", null, result));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new Response(500, e.getMessage(), null));
        }

    }

    public ResponseEntity<Response> getEpisodeData(String movieTitle, long episode) {
        Episode result;
        try {
            result = movieDb.getMovieAttributeWithTotalEpisode(movieTitle,episode);
            List<String> genre = movieDb.getMovieGenre(movieTitle);
            String genreQuery = "";
            for (String string : genre) {
                genreQuery += "N'" + string + "',";
            }
            genreQuery = genreQuery.substring(0, genreQuery.length() - 1);
            long episodeView = movieDb.getEpisodeView(movieTitle, episode);
            List<Movie> trending = movieDb.getTrendingMovies();
            List<Movie> relativeMovie = movieDb.getMovieByRelativeGenre(genreQuery);
            if (episodeView == -1) {
                return ResponseEntity.status(500).body(new Response(500, "sai trong cau lenh sql lay view", null));
            }
            List<Object> listResult = new ArrayList<>();
            listResult.add(trending);
            listResult.add(relativeMovie);
            result.setGenre(genre);
            result.setView(episodeView);
            return ResponseEntity.status(200).body(new Response(200, "load successfully", result, listResult));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new Response(500, e.getMessage(), null));
        }
    }

    public ResponseEntity<Response> updateEpisodeView(long movieId, int episode) {
        Timestamp version = null;
        try {

            int retry = 0;
            while (retry < 5) {
                version = movieDb.getEpisodeVersionUpdateView(movieId, episode);
                int result = movieDb.updateEpisodeView(movieId, episode, version);
                if (result == 1) {
                    return ResponseEntity.status(200).body(new Response(200, "ok", null));
                } else {
                    Thread.sleep(100);
                    retry += 1;
                }
            }
            throw new Exception("qua tai server roi.......");

        } catch (Exception e) {
            return ResponseEntity.status(500).body(new Response(500, e.getMessage(), version));
        }
    }

    public ResponseEntity<Response> searchMovie(String title, String genre, String country, int releaseDate, String category) {
        //a: movies
        //b: bang trung gian movies_genres
        //c:genres
        String extraQuery = "";
        String extraTitle = "";
        String extraGenre = "";
        String extraCountry = "";
        String extraReleaseDate = "";
        String extraCategory = "";
        if (!title.isBlank()) {
            String[] splitTitle = title.split("\\s+");
            for (String word : splitTitle) {
                extraTitle += " a.title COLLATE SQL_Latin1_General_CP1_CI_AI like '%" + word + "%' or ";
            }
            extraTitle = extraTitle.substring(0, extraTitle.length() - 3);
        }
        extraQuery += extraTitle;

        if (!genre.isBlank()) {
            if (!extraQuery.isBlank()) {
                extraGenre += " and ";
            }
            extraGenre += " c.name = N'" + genre + "' ";
        }
        extraQuery += extraGenre;
        if (!country.isBlank()) {
            if (!extraQuery.isBlank()) {
                extraCountry += " and ";
            }
            extraCountry += " a.country = N'" + country + "' ";
        }
        extraQuery += extraCountry;
        if (releaseDate != 0) {
            if (!extraQuery.isBlank()) {
                extraReleaseDate += " and ";
            }
            extraReleaseDate += " a.releaseDate ";
            if (releaseDate > 0) {
                extraReleaseDate += " = " + releaseDate + " ";
            } else {
                extraReleaseDate += " < 2007 ";
            }
        }
        extraQuery += extraReleaseDate;
        if (!category.isBlank()) {
            if (!extraQuery.isBlank()) {
                extraCategory += " and ";
            }
            extraCategory += " a.category = N'" + category + "' ";
        }
        extraQuery += extraCategory;

        if (!extraQuery.isBlank()) {
            extraQuery = " where " + extraQuery;
        }
        try {
            List<Episode> result = movieDb.searchMovie(extraQuery);
            return ResponseEntity.status(200).body(new Response(200, "search successfully", result));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new Response(500, e.getMessage(), null));
        }

    }

    public ResponseEntity<Response> addToWatchLater(long userId, long movieId) {
        try {
            movieDb.addInWatchLater(userId, movieId);
            return ResponseEntity.status(200).body(new Response(200, "successfully"));
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(409).body(new Response(409, "Phim đã ở trong watch later list!"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new Response(500, e.getMessage()));
        }
    }
    
    public ResponseEntity<Response> getWatchList(long userId){
        try{
            List<Episode> result = movieDb.getWatchLaterList(userId);
            
            return ResponseEntity.status(200).body(new Response(200,"ok",result));
        }catch(Exception e){
            return ResponseEntity.status(500).body(new Response(500,e.getMessage(),null));
        }
    }
    
    public ResponseEntity<Response> deleteFromWatchLaterList(long userId,long movieId){
        try{
            movieDb.deleteFromWatchLaterList(userId, movieId);
            return ResponseEntity.status(200).body(new Response(200,"ok"));
        }catch(Exception e){
            return ResponseEntity.status(500).body(new Response(500,e.getMessage()));
        }
    }
}
