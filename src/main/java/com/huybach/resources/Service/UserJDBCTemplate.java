/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.huybach.resources.Service;

import com.huybach.resources.Model.User;
import com.huybach.resources.Service.Mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 *
 * @author HOME PC
 */
@Service
public class UserJDBCTemplate {
    @Autowired
    JdbcTemplate db;

    public void setDb(JdbcTemplate db) {
        this.db = db;
    }
    
    
    public void createUser(User user) throws DataIntegrityViolationException{
        String query = "insert into user values (?,?,?,?,?)";
        db.update(query,user.getEmail(),user.getPassword(),user.getFirstName(),user.getLastName(),user.getGender());
    }
    
    public User getUserByEmail(String email) throws EmptyResultDataAccessException{
        String query = "select * from users where email = ?";
        User result = (User) db.queryForObject(query, new Object[]{email},new UserMapper());
        return result;
    }
    
    public User getUserById(int id) throws EmptyResultDataAccessException{
        String query = "select * from users where id = ?";
        User result = (User) db.queryForObject(query, new Object[]{id},new UserMapper());
        return result;
    }
}
