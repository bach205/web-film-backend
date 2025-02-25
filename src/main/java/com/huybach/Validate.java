/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.huybach;

import com.huybach.resources.Model.Movie;
import com.huybach.resources.Model.Response;
import com.huybach.resources.Model.User;
import com.huybach.resources.Service.repo.SessionJDBCTemplate;
import com.huybach.resources.Service.repo.UserJDBCTemplate;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 *
 * @author HOME PC
 */
@Component
public class Validate {

    private UserJDBCTemplate userDb;
    private SessionJDBCTemplate sessionDb;

    @Autowired
    public Validate(UserJDBCTemplate userDb, SessionJDBCTemplate sessionDb) {
        this.userDb = userDb;
        this.sessionDb = sessionDb;
    }

    //kiem tra xem user co phai la management hay 
    public boolean isAuthorization(HttpServletRequest req) {
        try {
            String sessionId = (String) req.getAttribute("sessionId");
            long userId = sessionDb.getSession(sessionId).getUserId();
            User user = userDb.getUserById(userId);
            return user.getRole() != 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    //kiem tra xem da login chua
    public boolean isLogin(HttpServletRequest req) {
        try {
            String sessionId = (String) req.getAttribute("sessionId");
            long userId = sessionDb.getSession(sessionId).getUserId();
            User user = userDb.getUserById(userId);
            return user.getId() > 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    //check format email
    public boolean isValidEmail(String email) {
        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z]{2,}$";
        return email.matches(regex);
    }

    //response khi email invalid
    public ResponseEntity<Response> deniedEmailResponse() {
        return ResponseEntity.status(500).body(new Response(500, "Your email is not right format"));
    }

    //response khi request khong hop le
    public ResponseEntity<Response> deniedResponse() {
        return ResponseEntity.status(403).body(new Response(403, "what are you looking for"));
    }

    //kiem tra xem data duoc gui den co phai la rong khong
    public boolean isUserDataCorrect(User user) {
        return !user.getEmail().isBlank() || !user.getFirstName().isBlank() || !user.getLastName().isBlank()
                || !user.getPassword().isBlank();
    }

    //response khi data khong hop le
    public ResponseEntity<Response> deniedFormatCreateUser() {
        return ResponseEntity.status(500).body(new Response(500, "All input must be include except address field"));
    }

    //kiem tra xem du lieu cua episode co hop le khong
    public boolean IsEpisodeDataValid(String title, String description, String category, int releaseDate, String country, String imageURL, int episode, String videoURL, List<String> genreList) {
        for (String s : genreList) {
            if (s.isBlank()) {
                return false;
            }
        }
        return !(title.isBlank() || description.isBlank() || category.isBlank() || country.isBlank() || imageURL.isBlank() || videoURL.isBlank() || episode < 1 || releaseDate < 1900);
    }
    public boolean CheckMovieData(String title, String description, String category, int releaseDate, String country, String imageURL, List<String> genreList) {
        for (String s : genreList) {
            if (s.isBlank()) {
                return false;
            }
        }
        return !(title.isBlank() || description.isBlank() || category.isBlank() || country.isBlank() || imageURL.isBlank() || releaseDate < 1900);
    }
    public ResponseEntity<Response> responseEmptyInput() {
        return ResponseEntity.status(500).body(new Response(500, "You need to enter all input field"));
    }

    public boolean IsMovieDataValid(Movie movie) {

        String title = movie.getTitle();
        String description = movie.getDescription();
        String category = movie.getCategory();
        int releaseDate = movie.getReleaseDate();
        String country = movie.getCountry();
        String imageURL = movie.getImageURL();
        return !(title.isBlank() || description.isBlank() || category.isBlank() || country.isBlank() || imageURL.isBlank() || releaseDate < 1900);
    }

    public ResponseEntity<Response> deniedUpdateMovie() {
        return ResponseEntity.status(500).body(new Response(500, "You need to enter all input field"));
    }

}
