/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.huybach.resources.Service.repo;

import com.huybach.resources.Model.Session;
import com.huybach.resources.Service.Mapper.SessionMapper;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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

    public SessionJDBCTemplate() {
    }

    
    
    public SessionJDBCTemplate(JdbcTemplate jdbcTemplate) {
        this.db = jdbcTemplate;
    }
    
    public String create (long userId){
        String query = "insert into session values (?,?)";
        String sessionId = UUID.randomUUID().toString();
        db.update(query,sessionId,userId);
        return sessionId;
    }
    
    public Session getSession(String sessionId) throws EmptyResultDataAccessException {
        String query = "select * from session where sessionId = ?";
        Session session = (Session) db.queryForObject(query, new Object[]{sessionId}, new SessionMapper() );
        return session;
    }
}
