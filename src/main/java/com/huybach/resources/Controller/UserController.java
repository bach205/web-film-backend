/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.huybach.resources.Controller;

import com.huybach.Validate;
import com.huybach.resources.Model.Response;
import com.huybach.resources.Model.User;
import com.huybach.resources.Service.MovieService;
import com.huybach.resources.Service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author HOME PC
 */
@Controller
@RequestMapping("/user")
public class UserController {

    private UserService userService;
    private MovieService movieService;
    private Validate validate;

    @Autowired
    public void setMovieService(MovieService movieService) {
        this.movieService = movieService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setValidate(Validate validate) {
        this.validate = validate;
    }

    @PostMapping(value = "/validate")
    public ResponseEntity<Response> loginHandle(@RequestBody User user, HttpServletRequest req, HttpServletResponse res) {
        return userService.loginHandle(user, req, res);
    }

    @PostMapping(value = "/registration")
    public ResponseEntity<Response> registerHandle(@RequestBody User user, HttpServletResponse res) {
        if (!validate.isValidEmail(user.getEmail())) {
            return validate.deniedEmailResponse();
        }
        if (!validate.isUserDataCorrect(user)) {
            return validate.deniedFormatCreateUser();
        }
        return userService.registerHandle(user, res);
    }

    @PostMapping(value = "/reset-password")
    public ResponseEntity<Response> resetPasswordHandle(@RequestBody User user) {
        if(user.getPassword().isBlank()){
            return ResponseEntity.status(500).body(new Response(500,"Password cannot be blank"));
        }
        return userService.resetPasswordHandle(user);
    }

    //khong update(email,password,role)
    @PostMapping(value = "/update-info-except-password")
    public ResponseEntity<Response> updateInformationExceptPassword(@RequestBody User user) {
        if (!validate.isValidEmail(user.getEmail())) {
            return validate.deniedEmailResponse();
        }
        return userService.updateUserInformation(user);
    }

    @GetMapping(value = "/authorization/get-all")
    public ResponseEntity<Response> getAllUser(HttpServletRequest req) {
        if (!validate.isAuthorization(req)) {
            return validate.deniedResponse();
        }
        return userService.getAllUser();
    }

    @PostMapping(value = "/authorization/delete-user/{id}")
    public ResponseEntity<Response> deleteUserById(@PathVariable long id, HttpServletRequest req) {
        if (!validate.isAuthorization(req)) {
            return validate.deniedResponse();
        }
        movieService.deleteUserFromWatchLater(id);
        return userService.deleteUserById(id);
    }

    //khong update(email,password)
    @PostMapping(value = "/authorization/update-by-admin")
    public ResponseEntity<Response> updateUserByAdmin(@RequestBody User user, HttpServletRequest req) {
        if (!validate.isAuthorization(req)) {
            return validate.deniedResponse();
        }
        if (!validate.isValidEmail(user.getEmail())) {
            return validate.deniedEmailResponse();
        }
        if (!validate.isUserDataCorrect(user)) {
            return validate.deniedFormatCreateUser();
        }
        return userService.updateUserByAdmin(user);
    }

    @PostMapping(value = "/authorization/registration")
    public ResponseEntity<Response> addNewUserWithAdminAuthorization(@RequestBody User user, HttpServletRequest req, HttpServletResponse res) {
        if (!validate.isAuthorization(req)) {
            return validate.deniedResponse();
        }
        if (!validate.isValidEmail(user.getEmail())) {
            return validate.deniedEmailResponse();
        }
        if (!validate.isUserDataCorrect(user)) {
            return validate.deniedFormatCreateUser();
        }
        return userService.adminCreateUser(user, res);
    }
}
