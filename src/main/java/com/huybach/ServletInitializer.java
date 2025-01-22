/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.huybach;

import com.huybach.filter.SessionCheckFilter;
import jakarta.servlet.FilterRegistration;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
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
        FilterRegistration.Dynamic sessionCheckFilter = servletContext.addFilter("SessionCheckFilter", new SessionCheckFilter());
        sessionCheckFilter.addMappingForUrlPatterns(null, false, "*");
    }
}