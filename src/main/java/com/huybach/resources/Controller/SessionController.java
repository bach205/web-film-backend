/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.huybach.resources.Controller;

import com.huybach.resources.Model.Response;
import com.huybach.resources.Model.Session;
import com.huybach.resources.Model.User;
import com.huybach.resources.Service.SessionJDBCTemplate;
import com.huybach.resources.Service.UserJDBCTemplate;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author HOME PC
 */
@Controller
@RequestMapping("/authentication")
public class SessionController {

    @Autowired
    SessionJDBCTemplate db;

    @Autowired
    UserJDBCTemplate userDb;

    @PostMapping()
    @ResponseBody
    public ResponseEntity<Response> getUserData(HttpServletRequest req) {
        String sessionId = (String) req.getAttribute("sessionId");
        try {
            if (sessionId != null) {
                Session session = db.getSession(sessionId);
                User user = userDb.getUserById(session.getUserId());
                return ResponseEntity.status(200).body(new Response(200, "Authentication successfully", user));
            } else {
                return ResponseEntity.status(401).body(new Response(401, "Authentication fail", null));
            }
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.status(402).body(new Response(402, "what are you looking for", null));

        }
    }

    @GetMapping()
    public String getPage() {
        return "hello";
    }

}
