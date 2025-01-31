/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.huybach.resources.Controller;

import com.huybach.resources.Model.Response;
import com.huybach.resources.Service.SessionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author HOME PC
 */
@Controller
@RequestMapping("/authentication")
public class SessionController {

    SessionService sessionService;

    @Autowired
    public void setSessionService(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @GetMapping()
    public ResponseEntity<Response> getUserData(HttpServletRequest req) {
        return sessionService.getUserData(req);
    }

    @PostMapping(value = "/reset-session-cookie")
    public ResponseEntity<Response> resetSessionCookie(HttpServletResponse res) {
        return sessionService.resetSessionCookie(res);
    }

    @GetMapping(value ="/test")
    public String getPage() {
        return "hello";
    }

}
