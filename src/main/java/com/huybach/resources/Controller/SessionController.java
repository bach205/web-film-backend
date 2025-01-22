/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.huybach.resources.Controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author HOME PC
 */
@RestController
@RequestMapping("/api/session")
public class SessionController {
    @Autowired
    JdbcTemplate db;
    
    @PostMapping()
    public ResponseEntity<String> createSession(HttpServletRequest req){
        return ResponseEntity.status(200).body((String) req.getAttribute("add"));
    }
    @GetMapping()
    public String getPage(){
        return "hi";
    }
    
}
