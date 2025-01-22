/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.huybach.resources.Service;

import com.huybach.resources.Model.Session;
import com.huybach.resources.Service.Mapper.SessionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 *
 * @author HOME PC
 */
@Service
public class SessionJDBCTemplate {
    @Autowired
    private JdbcTemplate db;

    public SessionJDBCTemplate(JdbcTemplate jdbcTemplate) {
        this.db = jdbcTemplate;
    }
    
    public void create (Session session){
        String query = "insert into session values (?,?)";
        db.update(query,session.getSessionId(),session.getUserId());
    }
    
    public Session getSession(String sessionId) {
        String query = "select * from session where sessionId = ?";
        Session session = (Session) db.queryForObject(query, new Object[]{sessionId}, new SessionMapper() );
        return session;
    }
}
