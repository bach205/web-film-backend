/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.huybach.resources.Controller;

import com.huybach.Validate;
import com.huybach.resources.Model.Movie;
import com.huybach.resources.Model.MovieIdAndEpisode;
import com.huybach.resources.Model.Response;
import com.huybach.resources.Model.UserAndMovie;
import com.huybach.resources.Service.MovieService;
import com.huybach.resources.Service.repo.MovieJDBCTemplate;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author HOME PC
 */
@Controller
@RequestMapping("api/movies")
public class MovieController {
    private MovieService movieService;
    
    private Validate validate;

    @Autowired
    public void setValidate(Validate validate) {
        this.validate = validate;
    }
    
    
    @Autowired
    public void setMovieService(MovieService movieService) {
        this.movieService = movieService;
    }
    
    @GetMapping(value = "/get-trending-movies")
    public ResponseEntity<Response> getTrendingMovies (){
        return movieService.getTrendingMovies();
    }
    
    @GetMapping(value = "/get-latest-movies-by-genre")
    public ResponseEntity<Response> getLatestMoviesByCategory (@RequestBody String category){
        return movieService.getLatestMoviesByCategory(category);
    }
    
    @GetMapping (value = "/load-homepage")
    public ResponseEntity<Response> loadHomePage(){
        return movieService.loadHomePage();
    }
    
    @GetMapping  (value ="/get-episode-data/{title}/{episode}")
    public ResponseEntity<Response> getEpisodeData(@PathVariable String title,@PathVariable long episode){
        return movieService.getEpisodeData(title, episode);
    }
    @PostMapping(value = "/update-episode-view")
    public ResponseEntity<Response> updateEpisodeView(@RequestBody MovieIdAndEpisode data){
        return movieService.updateEpisodeView(data.getMovieId(),data.getEpisode());
    }
    
    @GetMapping(value = "/search")
    public ResponseEntity<Response> searchMovie(@RequestParam String title,@RequestParam String genre,@RequestParam String country,@RequestParam int releaseDate,@RequestParam String category){
        return movieService.searchMovie(title, genre, country, releaseDate, category);
    }
    
    @PostMapping(value = "/add-to-watchlater")
    public ResponseEntity<Response> addToWatchLater(@RequestBody UserAndMovie req){
        return movieService.addToWatchLater(req.getUserId(), req.getMovieId());
    }
    
    @GetMapping(value = "authorization/load-watchlater")
    public ResponseEntity<Response> getWatchLaterList (@RequestParam long userId,HttpServletRequest req){
        if(!validate.isLogin(req)){
            return validate.deniedResponse();
        }
        return movieService.getWatchList(userId);
    }
    
    @PostMapping(value = "authorization/delete-watchlater")
    public ResponseEntity<Response> deleteFromWatchLaterList(@RequestBody UserAndMovie req,HttpServletRequest request){
        if(!validate.isLogin(request)){
            return validate.deniedResponse();
        }
        return movieService.deleteFromWatchLaterList(req.getUserId(), req.getMovieId());
    }
    
    @GetMapping (value= "/load-statics")
    public ResponseEntity<Response> loadGeneralStatics (){
        return movieService.loadGeneralStatics();
    }
    
    @PostMapping (value = "/authorization/add-movie")
    public ResponseEntity<Response> addNewEpisode(@RequestBody List<Object> req, HttpServletRequest request){
        if(!validate.isAuthorization(request)){
            return validate.deniedResponse();
        }
        String title = (String)req.get(0);
        String description =(String) req.get(1);
        String category =(String) req.get(2);
        int releaseDate = (int) req.get(3);
        String country = (String)req.get(4);
        String imageURL = (String)req.get(5);
        int episode = (int)req.get(6);
        String videoURL = (String)req.get(7);
        List<String> genreList =(List<String>) req.get(8);
        return movieService.addEpisode(title, description, category, releaseDate, country, imageURL, episode, videoURL, genreList);
    }
}