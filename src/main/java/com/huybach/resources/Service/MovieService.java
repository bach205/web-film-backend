/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.huybach.resources.Service;

import com.huybach.resources.Model.Movie;
import com.huybach.resources.Model.Response;
import com.huybach.resources.Service.repo.MovieJDBCTemplate;
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
            return ResponseEntity.status(500).body(new Response(500, "fail", null));
        }
    }

    public ResponseEntity<Response> getLatestMoviesByGenre(String genre) {
        try {
            List<Movie> result = movieDb.getLatestMoviesByGenre(genre);
            System.out.println(result.size());
            return ResponseEntity.status(200).body(new Response(200, "latest movies by genre", result));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new Response(500, "fail", null));
        }
    }
}
