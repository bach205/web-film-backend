/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.huybach.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huybach.resources.Model.User;
import com.huybach.resources.Service.repo.SessionJDBCTemplate;
import com.huybach.resources.Service.repo.UserJDBCTemplate;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author HOME PC
 */
public class AuthorizationFilter implements Filter {

    private UserJDBCTemplate userDb;

    private SessionJDBCTemplate sessionDb;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

            HttpServletResponse httpResponse = (HttpServletResponse) response;
            User user = new User();
            // Tạo object JSON        
            try{
                user = userDb.getUserById(sessionDb.getSession((String)request.getAttribute("sessionId")).getUserId());
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
            if (user.getRole() == 0) {
                Map<String, Object> responseObject = new HashMap<>();
            responseObject.put("status", "403");
            responseObject.put("message", "Access Denied");   
                // Chuyển object thành JSON
                ObjectMapper objectMapper = new ObjectMapper();
                String jsonResponse = objectMapper.writeValueAsString(responseObject);

                // Thiết lập response
                httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN); // 403
                httpResponse.setContentType("application/json");
                httpResponse.setCharacterEncoding("UTF-8");
                httpResponse.getWriter().write(jsonResponse);
                return;
            }
        
        chain.doFilter(request, response);
    }

}
