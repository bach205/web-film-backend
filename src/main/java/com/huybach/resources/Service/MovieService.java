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

    public ResponseEntity<Response> getLatestMoviesByGenre(String genre) {
        try {
            List<Movie> result = movieDb.getLatestMoviesByGenre(genre);
            System.out.println(result.size());
            return ResponseEntity.status(200).body(new Response(200, "latest movies by genre", result));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new Response(500, e.getMessage(), null));
        }
    }

    public ResponseEntity<Response> loadHomePage() {
        try {
            List<Movie> trending = movieDb.getTrendingMovies();
            List<Movie> latestBo = movieDb.getLatestMoviesByGenre("Phim bộ");
            List<Movie> latestLe = movieDb.getLatestMoviesByGenre("Phim lẻ");
            List<Object> result = new ArrayList<>();
            result.add(trending);
            result.add(latestBo);
            result.add(latestLe);
            return ResponseEntity.status(200).body(new Response(200, "load successfully", null,result));
        }catch (Exception e) {
            return ResponseEntity.status(500).body(new Response(500, e.getMessage(), null));
        }

    }
    public ResponseEntity<Response> getEpisodeData(String movieTitle,long episode){
        Episode result;
        try{
            result = movieDb.getMovieAttributeWithTotalEpisode(movieTitle);
            List<String> genre = movieDb.getMovieGenre(movieTitle);
            long episodeView = movieDb.getEpisodeView(movieTitle, episode);
            if(episodeView == -1){
                return ResponseEntity.status(500).body(new Response(500,"sai trong cau lenh sql lay view",null));
            }
            result.setGenre(genre);
            result.setView(episodeView);
            return ResponseEntity.status(200).body(new Response(200,"load successfully",result));
        }catch(Exception e){
            return ResponseEntity.status(500).body(new Response(500,e.getMessage(),null));
        }
    }
    
    public ResponseEntity<Response> updateEpisodeView(long movieId,int episode){
        try{
             Timestamp version =movieDb.getEpisodeVersionUpdateView(movieId, episode);
             int retry = 0;
             while(retry<5){
                 int result = movieDb.updateEpisodeView(movieId, episode, version);
                 if(result == 1){
                     return ResponseEntity.status(200).body(new Response(200,"ok",null));
                 }else{
                     retry+=1;
                 }              
             }
             throw new Exception("qua tai server roi.......");
             
        }catch(Exception e){
            return ResponseEntity.status(500).body(new Response(500,e.getMessage(),null));
        }
    }
}
