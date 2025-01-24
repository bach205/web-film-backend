/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.huybach.resources.Controller;

import com.huybach.resources.Model.Response;
import com.huybach.resources.Model.User;
import com.huybach.resources.Service.SessionJDBCTemplate;
import com.huybach.resources.Service.UserJDBCTemplate;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserJDBCTemplate db;

    @Autowired
    SessionJDBCTemplate sessionDb;
    
    @PostMapping(value = "/validate")
    public ResponseEntity<Response> loginHandle(@RequestBody User user, HttpServletRequest req, HttpServletResponse res) throws IOException {
        try {
            User result = db.getUserByEmail(user.getEmail());
            if (result.getPassword().equals(user.getPassword())) {
                String sessionId = sessionDb.create(result.getId());
                res.addHeader("set-cookie", "sessionId=" + sessionId +"; Path=/; Max-Age=31536000; HttpOnly;Secure;SameSite=None");
                return ResponseEntity.status(200).body(new Response(200,"successfull",result));
            } else {
                return ResponseEntity.status(401).body(new Response(401,"wrong password",null));
            }
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.status(401).body(new Response(401,"account do not existed",null));
        }
    }
}
