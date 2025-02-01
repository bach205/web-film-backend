/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.huybach;

import com.huybach.filter.AuthorizationFilter;
import com.huybach.filter.SessionCheckFilter;
import jakarta.servlet.FilterRegistration;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import org.springframework.stereotype.Component;
import org.springframework.web.WebApplicationInitializer;

/**
 *
 * @author HOME PC
 */
public class ServletInitializer implements WebApplicationInitializer {

    /**
     *
     * @param servletContext
     * @throws ServletException
     */
    @Override
    
    public void onStartup(ServletContext servletContext) throws ServletException {
//        FilterRegistration.Dynamic authorizationFilter = servletContext.addFilter("AuthorizationFilter", new AuthorizationFilter());
        FilterRegistration.Dynamic sessionCheckFilter = servletContext.addFilter("SessionCheckFilter", new SessionCheckFilter());
//        authorizationFilter.addMappingForUrlPatterns(null, false, "/user/authorization/*","/api/movies/authorization/*");
        sessionCheckFilter.addMappingForUrlPatterns(null, false, "/*");
    }
}