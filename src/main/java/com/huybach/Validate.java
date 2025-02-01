/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.huybach;

import com.huybach.resources.Model.Response;
import com.huybach.resources.Model.User;
import com.huybach.resources.Service.repo.SessionJDBCTemplate;
import com.huybach.resources.Service.repo.UserJDBCTemplate;
import jakarta.servlet.http.HttpServletRequest;
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

    public ResponseEntity<Response> deniedResponse() {
        return ResponseEntity.status(403).body(new Response(403, "what are you looking for"));
    }

}
