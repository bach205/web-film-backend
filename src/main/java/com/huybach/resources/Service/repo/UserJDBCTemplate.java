/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.huybach.resources.Service.repo;

import com.huybach.resources.Model.User;
import com.huybach.resources.Service.Mapper.UserMapper;
import java.util.List;
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
        String query = "insert into users values (?,?,?,?,?,?,?)";
        db.update(query,user.getEmail(),user.getPassword(),user.getFirstName(),user.getLastName(),user.getGender(),user.getAddress(),user.getRole());
    }
    
    public User getUserByEmail(String email) throws EmptyResultDataAccessException{
        String query = "select * from users where email = ?";
        User result = (User) db.queryForObject(query, new Object[]{email},new UserMapper());
        return result;
    }
    
    public User getUserById(long id) throws EmptyResultDataAccessException{
        String query = "select * from users where id = ?";
        User result = (User) db.queryForObject(query, new Object[]{id},new UserMapper());
        return result;
    }
    
    public int updateUserPassword(String email,String password){
        String query = "update users set password = ? where email = ?";
        return db.update(query,password,email);
    }
    
    public boolean isUserExistedByEmail(String email){
        try{
            User user = getUserByEmail(email);
            return true;
        }catch(EmptyResultDataAccessException e){
            return false;
        }
    }
    //khong update (email,password,role)
    public int updateUserInformationExceptPassword (User user){
        String query = "update users set firstName = ?, lastName = ?, gender = ?, address= ? where id = ?";
        return db.update(query,user.getFirstName(),user.getLastName(),user.getGender(),user.getAddress(),user.getId());     
    }
    
    public List<User> getAllUser(){
        String query = "select * from users";
        return db.query(query,new UserMapper());       
    }
        
    public int deleteUserById(long userId) {
        String query = "delete from users where id = ?";
        return db.update(query,userId);
    }
    //khong update (email,password)
    public int updateUserByAdmin(User user){
        String query = "update users set firstName = ?, lastName=?,gender =?,address = ?,role=? where id =?";
        return db.update(query,user.getFirstName(),user.getLastName(),user.getGender(),user.getAddress(),user.getRole(),user.getId());
    }
}
