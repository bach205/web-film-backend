/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.huybach.resources.Service;

import com.huybach.resources.Model.Response;
import com.huybach.resources.Model.User;
import com.huybach.resources.Service.repo.UserJDBCTemplate;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

/**
 *
 * @author HOME PC
 */
@Service
public class UserService {

    @Autowired
    UserJDBCTemplate db;

    @Autowired
    com.huybach.resources.Service.repo.SessionJDBCTemplate sessionDb;

    public ResponseEntity<Response> loginHandle(@RequestBody User user, HttpServletRequest req, HttpServletResponse res) {
        try {
            User result = db.getUserByEmail(user.getEmail());
            if (result.getPassword().equals(user.getPassword())) {
                sendSessionId(res, result);
                return ResponseEntity.status(200).body(new Response(200, "successfull", result));
            } else {
                return ResponseEntity.status(401).body(new Response(401, "Password is invalid", null));
            }
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.status(500).body(new Response(500, "Email is not existed", null));
        } catch(Exception e){
            return ResponseEntity.status(500).body(new Response(500, e.getMessage(), null));
        }
    }
    
    public ResponseEntity<Response> registerHandle(@RequestBody User user,HttpServletResponse res) {
        try {
            db.createUser(user);
            sendSessionId(res, user);
            return ResponseEntity.status(200).body(new Response(200, "Create account successfully", user));
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(500).body(new Response(500, "The email is existed", null));
        }catch(Exception e){
            return ResponseEntity.status(500).body(new Response(500, e.getMessage(), null));
        }
    }
    
    //gui sessionid vao cookie trong response
    public void sendSessionId (HttpServletResponse res, User user){
        String sessionId = sessionDb.create(user.getId());
            res.setHeader("set-cookie", "sessionId=" + sessionId + "; Path=/; Max-Age=31536000; HttpOnly;Secure;SameSite=None");
    }
    
    public ResponseEntity<Response> resetPasswordHandle (User user){
        int result = db.updateUserPassword(user.getEmail(), user.getPassword());
        if(result>0){
            return ResponseEntity.status(200).body(new Response(200,"Your password is updated sucessfully",null));
        }else{
            return ResponseEntity.status(500).body(new Response(500,"Email is not existed",null));
        }
    }
    
    public ResponseEntity<Response> updateUserInformation(User user){      
        try{
            int result = db.updateUserInformationExceptPassword(user);
        if(result>0){
            return ResponseEntity.status(200).body(new Response(200,"Your information is updated sucessfully",null));
        }else{
            return ResponseEntity.status(500).body(new Response(500,"can not update",null));
        }
        }catch(DataIntegrityViolationException e){
            return ResponseEntity.status(500).body(new Response(500,"Email is existed",null));
        }catch(Exception e){
            return ResponseEntity.status(500).body(new Response(500,e.getMessage(),null));
        }
    }
    public ResponseEntity<Response> getAllUser (){
        try{
            List<User> result = db.getAllUser();
            return ResponseEntity.status(200).body(new Response(200,"load all user successfully",result));
        }catch(Exception e){
            return ResponseEntity.status(500).body(new Response(500,"load all user failed",null));
        }
    }
    
    public ResponseEntity<Response> deleteUserById(int userId){
        try{
            int result = db.deleteUserById(userId);
            if(result > 0 ){
                return ResponseEntity.status(200).body(new Response(200,"delete successfully"));
            }else{
                return ResponseEntity.status(500).body(new Response(200,"delete failed"));
            }
        }catch(Exception e){
            return ResponseEntity.status(200).body(new Response(200,e.getMessage()));
        }
    }
    
    public ResponseEntity<Response> updateUserByAdmin(User user){
        try{
            int result = db.updateUserByAdmin(user);
            if(result > 0 ){
                return ResponseEntity.status(200).body(new Response(200,"update successfully"));
            }else{
                return ResponseEntity.status(500).body(new Response(200,"update failed"));
            }
        }catch(Exception e){
            return ResponseEntity.status(200).body(new Response(200,e.getMessage()));
        }
        
    }
}
