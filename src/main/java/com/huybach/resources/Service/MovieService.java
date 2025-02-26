/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.huybach.resources.Service;

import com.huybach.Validate;
import com.huybach.resources.Model.Episode;
import com.huybach.resources.Model.Movie;
import com.huybach.resources.Model.Response;
import com.huybach.resources.Model.User;
import com.huybach.resources.Service.repo.MovieJDBCTemplate;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
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

    //1.0
    public ResponseEntity<Response> getTrendingMovies() {
        try {
            List<Movie> result = movieDb.getTrendingMovies();
            return ResponseEntity.status(200).body(new Response(200, "trending movies", result));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new Response(500, e.getMessage(), null));
        }
    }

    public ResponseEntity<Response> getAllMoviesSortByCriterion(String criterion, String direction) {
        try {
            List<Movie> result = movieDb.getAllMovieSortByCriterion(criterion, direction);

            if (!result.isEmpty()) {
                for (Movie movie : result) {
                    movie.setGenre(movieDb.getMovieGenre(movie.getTitle()));
                }
                return ResponseEntity.status(200).body(new Response(200, "Get success", result));
            } else {
                return ResponseEntity.status(404).body(new Response(404, "Database haven't had any movie yet"));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(500).body(new Response(500, "Get failed"));
        }

    }

    //1.0
    public ResponseEntity<Response> getLatestMoviesByCategory(String category) {
        try {
            List<Movie> result = movieDb.getLatestMoviesByCategory(category);
            return ResponseEntity.status(200).body(new Response(200, "ok latest movies by category", result));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new Response(500, e.getMessage(), null));
        }
    }

    //1.0
    public ResponseEntity<Response> loadHomePage() {
        try {
            List<Movie> trending = movieDb.getTrendingMovies();
            List<Movie> latestBo = movieDb.getLatestMoviesByCategory("Phim bộ");
            List<Movie> latestLe = movieDb.getLatestMoviesByCategory("Phim lẻ");
            String extraQuery = " where c.name = N'Anime' ";
            List<Episode> anime = movieDb.searchMovie(extraQuery, "");
            List<Object> result = new ArrayList<>();
            result.add(trending);
            result.add(latestBo);
            result.add(latestLe);
            result.add(anime);
            return ResponseEntity.status(200).body(new Response(200, "load successfully", null, result));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new Response(500, e.getMessage(), null));
        }

    }

    //fix 1.0
    public ResponseEntity<Response> getEpisodeData(String movieTitle, long episode) {
        Episode result;
        try {
            result = movieDb.getEpisode(movieTitle, episode);
            List<String> genre = movieDb.getMovieGenre(movieTitle);
            result.setGenre(genre);
            String genreQuery = "";
            for (String string : genre) {
                genreQuery += "N'" + string + "',";
            }
            genreQuery = genreQuery.substring(0, genreQuery.length() - 1);
            List<Movie> trending = movieDb.getTrendingMovies();
            List<Movie> relativeMovie = movieDb.getMovieByRelativeGenre(genreQuery);
            List<Object> listResult = new ArrayList<>();
            listResult.add(trending);
            listResult.add(relativeMovie);
            return ResponseEntity.status(200).body(new Response(200, "load successfully", result, listResult));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new Response(500, e.getMessage(), null));
        }
    }

    //fix 1.0
    public ResponseEntity<Response> updateEpisodeView(long movieId, int episode) {
        try {
            int result = movieDb.updateEpisodeView(movieId, episode);
            if (result > 0) {
                return ResponseEntity.status(200).body(new Response(200, "ok", null));
            } else {
                return ResponseEntity.status(500).body(new Response(500, "failed"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new Response(500, e.getMessage()));
        }
    }

    //fixed 1.0 
    public ResponseEntity<Response> searchMovie(String title, String genre, String country, int releaseDate, String category, String sort, String direction) {
        //a: movies
        //b: bang trung gian movies_genres
        //c:genres
        String extraQuery = "";
        String extraTitle = "";
        String extraGenre = "";
        String extraCountry = "";
        String extraReleaseDate = "";
        String extraCategory = "";
        String sortQuery = "";
        if (!sort.isBlank() && !direction.isBlank()) {
            sortQuery += "order by " + sort + " " + direction;
        }
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
            List<Episode> result = movieDb.searchMovie(extraQuery, sortQuery);
            return ResponseEntity.status(200).body(new Response(200, "search successfully", result));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new Response(500, e.getMessage(), null));
        }

    }

    //1.0
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

    public ResponseEntity<Response> getWatchList(long userId) {
        try {
            List<Episode> result = movieDb.getWatchLaterList(userId);

            return ResponseEntity.status(200).body(new Response(200, "ok", result));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new Response(500, e.getMessage(), null));
        }
    }

    //1.0
    public ResponseEntity<Response> deleteFromWatchLaterList(long userId, long movieId) {
        try {
            movieDb.deleteFromWatchLaterList(userId, movieId);
            return ResponseEntity.status(200).body(new Response(200, "ok"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new Response(500, e.getMessage()));
        }
    }

    //1.0
    public ResponseEntity<Response> getMostViewByDuration(String criterion) {
        List<Object> result = new ArrayList<>();
        try {
            result.add(movieDb.getMostViewByDuration(criterion));
            return ResponseEntity.status(200).body(new Response(200, "ok", result));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new Response(500, e.getMessage(), null));
        }
    }

    //error need rewrite
    public ResponseEntity<Response> addEpisode(String title, String description, String category, int releaseDate, String country, String imageURL, int episode, String videoURL, List<String> genreList) {
        long movieId = -1;
        try {
            movieId = movieDb.getMovieIdByTitle(title);
            if (movieId == -1) {
                movieDb.addMovie(title, description, category, releaseDate, country, imageURL);
                movieId = movieDb.getMovieIdByTitle(title);
                List<Integer> genreIdList = movieDb.getGenreId(genreList);
                movieDb.addMovieGenre(genreIdList, movieId);
            }
            movieDb.addNewEpisode(movieId, episode, videoURL);
            return ResponseEntity.status(200).body(new Response(200, "ok"));
        } catch (DataAccessException e) {
            return ResponseEntity.status(500).body(new Response(500, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new Response(500, e.getMessage()));
        }
    }

    //1.0
    public ResponseEntity<Response> deleteUserFromWatchLater(long userId) {
        try {
            int check = movieDb.deleteUserIdFromWatchLater(userId);
            if (check > 0) {
                return ResponseEntity.status(200).body(new Response(200, "delete successfully"));
            } else {
                return ResponseEntity.status(500).body(new Response(500, "delete failed"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new Response(500, e.getMessage()));
        }
    }

    public ResponseEntity<Response> createMovie(Movie movie, User user) {
        if (user.getRole() == 0) {
            return ResponseEntity.status(500).body(new Response(500, "what are you looking for?"));
        }
        try {
            int check = 0;
            if (user.getRole() == 1) {
                check = movieDb.adminCreateMovie(movie);
                Long id = movieDb.getMovieIdByTitle(movie.getTitle());
                List<Integer> genreList = movieDb.getGenreId(movie.getGenre());
                movieDb.addMovieGenre(genreList, id);
            } else if (user.getRole() == 2) {
                check = movieDb.managerCreateMovie(movie, "pending");
            }
            if (check > 0) {
                return ResponseEntity.status(200).body(new Response(200, "create successfully"));
            } else {
                return ResponseEntity.status(500).body(new Response(500, "create failed"));
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(500).body(new Response(500, e.getMessage()));
        }

    }

    //fix 1.0
    public ResponseEntity<Response> removeMovieById(long movieId, User user) {
        if (user.getRole() == 0) {
            return ResponseEntity.status(500).body(new Response(500, "what are you looking for?"));
        }
        Movie movie = movieDb.getMovieWithoutGenre(movieId);
        movie.setGenre(movieDb.getMovieManagementGenre(movie.getTitle()));

        try {
            int check = 0;
            if (user.getRole() == 1) {
                check = movieDb.adminDeleteMovieById(movieId);
            } else if (user.getRole() == 2) {

                check = movieDb.managerDeleteMovie(movie, "pending");
            }
            if (check > 0) {
                return ResponseEntity.status(200).body(new Response(200, "delete successfully"));
            } else {
                return ResponseEntity.status(500).body(new Response(500, "delete failed"));
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(500).body(new Response(500, e.getMessage()));
        }
    }

    //fix 1.0
    public ResponseEntity<Response> updateMovie(Movie movie, User user) {
        if (user.getRole() == 0) {
            return ResponseEntity.status(500).body(new Response(500, "what are you looking for?"));
        }
        try {
            int check = 0;
            if (user.getRole() == 1) {
                check = movieDb.adminUpdateMovie(movie);

            } else if (user.getRole() == 2) {
                check = movieDb.managerUpdateMovie(movie, "pending");
            }
            if (check > 0) {
                return ResponseEntity.status(200).body(new Response(200, "update successfully"));
            } else {
                return ResponseEntity.status(500).body(new Response(500, "update failed"));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(500).body(new Response(500, "update failed"));
        }
    }

    public ResponseEntity<Response> createEpisode(long movieId, long episode, String videoURL, User user) {
        if (user.getRole() == 0) {
            return ResponseEntity.status(500).body(new Response(500, "what are you looking for?"));
        }
        try {
            int check = 0;
            if (user.getRole() == 1) {
                movieDb.addEpisode(movieId, episode, videoURL);
            } else if (user.getRole() == 2) {

            }
            if (check > 0) {
                return ResponseEntity.status(200).body(new Response(200, "create successfully"));
            } else {
                return ResponseEntity.status(500).body(new Response(500, "create failed"));
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(500).body(new Response(500, e.getMessage()));
        }

    }

    public ResponseEntity<Response> getCategoryViewStatics() {
        List<List<Integer>> result = new ArrayList<>();
        try {
            System.out.println(movieDb.getEachCategoryStatics("Phim bộ").size());
            result.add(movieDb.getEachCategoryStatics("Phim Bộ"));
            result.add(movieDb.getEachCategoryStatics("Phim Lẻ"));
            result.add(movieDb.getAllCategoryStatics());
            result.add(movieDb.getTotalView());
            return ResponseEntity.status(200).body(new Response(200, "create successfully", result));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(500).body(new Response(500, e.getMessage()));
        }

    }
}
