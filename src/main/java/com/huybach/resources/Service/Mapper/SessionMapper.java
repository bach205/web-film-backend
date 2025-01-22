/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.huybach.resources.Service.Mapper;

import com.huybach.resources.Model.Session;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author HOME PC
 */
public class SessionMapper implements RowMapper{

    @Override
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        Session session = new Session();
        session.setSessionId(rs.getString("sessionId"));
        session.setUserId(rs.getInt("userId"));
        return session;
    }
    
}