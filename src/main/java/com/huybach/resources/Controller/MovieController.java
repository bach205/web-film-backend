/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.huybach.resources.Controller;

import com.huybach.Validate;
import com.huybach.resources.Model.Episode;
import com.huybach.resources.Model.Movie;
import com.huybach.resources.Model.MovieIdAndEpisode;
import com.huybach.resources.Model.Response;
import com.huybach.resources.Model.User;
import com.huybach.resources.Model.UserAndMovie;
import com.huybach.resources.Service.MovieService;
import com.huybach.resources.Service.SessionService;
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

//version 1.0
@Controller
@RequestMapping("api/movies")
public class MovieController {
    private MovieService movieService;
    private SessionService sessionService;
    private Validate validate;

    @Autowired
    public void setValidate(Validate validate) {
        this.validate = validate;
    }
    @Autowired
    public void setSessionService(SessionService sessionService){
        this.sessionService=sessionService;
    }
    
    
    @Autowired
    public void setMovieService(MovieService movieService) {
        this.movieService = movieService;
    }
    
    //fix 1.0
    @GetMapping(value = "/authorization/get-all-movies")
    public ResponseEntity<Response> getALlMovies(@RequestParam(defaultValue = "") String criterion, @RequestParam(defaultValue = "") String direction, HttpServletRequest request){
        if(!validate.isAuthorization(request)){
            validate.deniedResponse();
        }
        return movieService.getAllMoviesSortByCriterion(criterion, direction);
    }
    
    //1.0
    @GetMapping(value = "/get-trending-movies")
    public ResponseEntity<Response> getTrendingMovies (){
        return movieService.getTrendingMovies();
    }
    
    //1.0
    @GetMapping(value = "/get-latest-movies-by-genre")
    public ResponseEntity<Response> getLatestMoviesByCategory (@RequestBody String category){
        return movieService.getLatestMoviesByCategory(category);
    }
    
    //1.0
    @GetMapping (value = "/load-homepage")
    public ResponseEntity<Response> loadHomePage(){
        return movieService.loadHomePage();
    }
    
    //fix 1.0
    @GetMapping  (value ="/get-episode-data/{title}/{episode}")
    public ResponseEntity<Response> getEpisodeData(@PathVariable String title,@PathVariable long episode){
        return movieService.getEpisodeData(title, episode);
    }
    //fix 1.0
    @PostMapping(value = "/update-episode-view")
    public ResponseEntity<Response> updateEpisodeView(@RequestBody MovieIdAndEpisode data){
        return movieService.updateEpisodeView(data.getMovieId(),data.getEpisode());
    }
    
    //fix 1.0
    @GetMapping(value = "/search")
    public ResponseEntity<Response> searchMovie(@RequestParam String title,@RequestParam String genre,@RequestParam String country,@RequestParam int releaseDate,@RequestParam String category,@RequestParam String sort,@RequestParam String direction){
        return movieService.searchMovie(title, genre, country, releaseDate, category,sort,direction);
    }
    
    //fix 1.0
    @PostMapping(value = "/authorization/add-to-watchlater")
    public ResponseEntity<Response> addToWatchLater(@RequestBody UserAndMovie req,HttpServletRequest request){
        if(!validate.isLogin(request)){
            return validate.deniedResponse();
        }
        return movieService.addToWatchLater(req.getUserId(), req.getMovieId());
    }
    
    //fix 1.0
    @GetMapping(value = "authorization/load-watchlater")
    public ResponseEntity<Response> getWatchLaterList (@RequestParam long userId,HttpServletRequest req){
        if(!validate.isLogin(req)){
            return validate.deniedResponse();
        }
        return movieService.getWatchList(userId);
    }
    
    //fix 1.0
    @PostMapping(value = "authorization/delete-watchlater")
    public ResponseEntity<Response> deleteFromWatchLaterList(@RequestBody UserAndMovie req,HttpServletRequest request){
        if(!validate.isLogin(request)){
            return validate.deniedResponse();
        }
        return movieService.deleteFromWatchLaterList(req.getUserId(), req.getMovieId());
    }
    
    //done 1.0
    @GetMapping (value= "/load-most-view-by-duration")
    public ResponseEntity<Response> getMostViewByDuration (@RequestParam String duration){
        return movieService.getMostViewByDuration(duration);
    }
    
    //fixing
    @PostMapping (value = "/authorization/add-movie")
    public ResponseEntity<Response> addNewEpisode(@RequestBody List<Object> req, HttpServletRequest request){
        if(!validate.isAuthorization(request)){
            return validate.deniedResponse();
        }
        
        String title = (String)req.get(0);
        String description =(String) req.get(1);
        String category =(String) req.get(2);
        int releaseDate = req.get(3)!= null ? (int) req.get(3) : 0;
        String country = (String)req.get(4);
        String imageURL = (String)req.get(5);
        List<String> genreList =(List<String>) req.get(6);
        if(!validate.CheckMovieData(title, description, category, releaseDate, country, imageURL, genreList)){
            return validate.responseEmptyInput();
        }
        User user = sessionService.getUser(request);
        if(user == null){
            return validate.deniedResponse();
        }
        Movie movie = new Movie(title, description, category, releaseDate, country, imageURL, genreList);
        return movieService.createMovie(movie, user);
    }
    
    //fix 1.0
    @PostMapping (value = "/authorization/remove-movie")
    public ResponseEntity<Response> removeMovie(@RequestBody long id, HttpServletRequest request){
        if(!validate.isAuthorization(request)){
            return validate.deniedResponse();
        }
        User user = sessionService.getUser(request);
        if(user == null){
            return validate.deniedResponse();
        }
        return movieService.removeMovieById(id,user);
    }
    
    //fix 1.0
    @PostMapping (value = "/authorization/update-movie")
    public ResponseEntity<Response> updateMovie(@RequestBody Movie movie, HttpServletRequest request){
        if(!validate.isAuthorization(request)){
            return validate.deniedResponse();
        }
        if(!validate.IsMovieDataValid(movie)){
            return validate.deniedUpdateMovie();
        }
        User user = sessionService.getUser(request);
        if(user == null){
            return validate.deniedResponse();
        }
        return movieService.updateMovie(movie,user);
    }
    @PostMapping (value = "/authorization/add-episode")
    public ResponseEntity<Response> addEpisode(@RequestBody Episode ep, HttpServletRequest request){
        if(!validate.isAuthorization(request)){
            return validate.deniedResponse();
        }
        long id = ep.getMovieId();
        long episode =ep.getEpisode();
        String videoURL =ep.getVideoURL();
        if(id<=0 || episode <= 0 || videoURL.isBlank()){
            return validate.responseEmptyInput();
        }
        User user = sessionService.getUser(request);
        if(user == null){
            return validate.deniedResponse();
        }
        return movieService.createEpisode(id, episode, videoURL, user);
    }
    @GetMapping (value = "/authorization/statics")
    public ResponseEntity<Response> getStatics(HttpServletRequest request){
        if(!validate.isAuthorization(request)){
            return validate.deniedResponse();
        }
        return movieService.getCategoryViewStatics();
    }
}