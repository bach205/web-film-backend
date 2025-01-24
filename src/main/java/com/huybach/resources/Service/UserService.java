/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.huybach.resources.Service;

import com.huybach.resources.Model.Response;
import com.huybach.resources.Model.User;
import com.huybach.resources.Service.repo.UserJDBCTemplate;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

/**
 *
 * @author HOME PC
 */
@Service
public class UserService {

    @Autowired
    UserJDBCTemplate db;

    @Autowired
    com.huybach.resources.Service.repo.SessionJDBCTemplate sessionDb;

    public ResponseEntity<Response> loginHandle(@RequestBody User user, HttpServletRequest req, HttpServletResponse res) {
        try {
            User result = db.getUserByEmail(user.getEmail());
            if (result.getPassword().equals(user.getPassword())) {
                sendSessionId(res, result);
                return ResponseEntity.status(200).body(new Response(200, "successfull", result));
            } else {
                return ResponseEntity.status(401).body(new Response(401, "Password is invalid", null));
            }
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.status(500).body(new Response(500, "Email is not existed", null));
        }
    }
    
    public ResponseEntity<Response> registerHandle(@RequestBody User user,HttpServletResponse res) {
        try {
            db.createUser(user);
            sendSessionId(res, user);
            return ResponseEntity.status(200).body(new Response(200, "Create account successfully", null));
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(500).body(new Response(500, "The email is existed", null));
        }
    }
    
    public void sendSessionId (HttpServletResponse res, User user){
        String sessionId = sessionDb.create(user.getId());
            res.setHeader("set-cookie", "sessionId=" + sessionId + "; Path=/; Max-Age=31536000; HttpOnly;Secure;SameSite=None");
    }
}
