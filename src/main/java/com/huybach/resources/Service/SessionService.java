/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.huybach.resources.Service;

import com.huybach.resources.Model.Response;
import com.huybach.resources.Model.Session;
import com.huybach.resources.Model.User;
import com.huybach.resources.Service.repo.UserJDBCTemplate;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 *
 * @author HOME PC
 */
@Service
public class SessionService {

    @Autowired
    com.huybach.resources.Service.repo.SessionJDBCTemplate db;

    @Autowired
    UserJDBCTemplate userDb;

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

        } catch (Exception e) {
            return ResponseEntity.status(500).body(new Response(500, e.getMessage(), null));
        }
    }

    public ResponseEntity<Response> resetSessionCookie(HttpServletResponse res) {
        res.setHeader("set-cookie", "sessionId =;HttpOnly;Secure;SameSite=None;Path =/; max-age = 0");
        return ResponseEntity.status(200).build();
    }
}
