/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.huybach.resources.Controller;

import com.huybach.resources.Model.Movie;
import com.huybach.resources.Model.Response;
import com.huybach.resources.Service.MovieService;
import com.huybach.resources.Service.repo.MovieJDBCTemplate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author HOME PC
 */
@Controller
@RequestMapping("api/movies")
public class MovieController {
    MovieService movieService;

    @Autowired
    public void setMovieService(MovieService movieService) {
        this.movieService = movieService;
    }
    
    @PostMapping(value = "/get-trending-movies")
    public ResponseEntity<Response> getTrendingMovies (){
        return movieService.getTrendingMovies();
    }
    
    @PostMapping(value = "/get-latest-movies-by-genre")
    public ResponseEntity<Response> getLatestMoviesByGenre (@RequestBody String genre){
        return movieService.getLatestMoviesByGenre(genre);
    }
}
