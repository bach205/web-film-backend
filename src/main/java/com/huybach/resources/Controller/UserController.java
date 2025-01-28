/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.huybach.resources.Controller;

import com.huybach.resources.Model.Response;
import com.huybach.resources.Model.User;
import com.huybach.resources.Service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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

    UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/validate")
    public ResponseEntity<Response> loginHandle(@RequestBody User user, HttpServletRequest req, HttpServletResponse res) {
        return userService.loginHandle(user, req, res);
    }

    @PostMapping(value = "/registration")
    public ResponseEntity<Response> registerHandle(@RequestBody User user, HttpServletResponse res) {
        return userService.registerHandle(user, res);
    }

    @PostMapping(value = "/reset-password")
    public ResponseEntity<Response> resetPasswordHandle(@RequestBody User user) {
        return userService.resetPasswordHandle(user);
    }
    
    @PostMapping(value = "/update-info-except-password")
    public ResponseEntity<Response> updateInformationExceptPassword(@RequestBody User user){
        return userService.updateUserInformation(user);
    }
    
    @PostMapping(value = "/get-all")
    public ResponseEntity<Response> getAllUser(){
        return userService.getAllUser();
    }
    
    @PostMapping(value ="/delete-user/{id}")
    public ResponseEntity<Response> deleteUserById(@PathVariable int id){
        return userService.deleteUserById(id);
    }
}
