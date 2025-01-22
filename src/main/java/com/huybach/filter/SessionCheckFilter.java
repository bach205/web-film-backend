/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.huybach.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 *
 * @author HOME PC
 */
public class SessionCheckFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;

            // Lấy danh sách cookie
//            Cookie[] cookies = httpRequest.getCookies();
//
//            if (cookies != null) {
//                for (Cookie cookie : cookies) {
//                    // Kiểm tra cookie theo tên
//                    if ("sessionId".equals(cookie.getName())) {
//                        System.out.println("Session ID: " + cookie.getValue());
//                    }
//                }
//            } else {
//                System.out.println("No cookies found!");
//            }
                request.setAttribute("add", "filter");
        }

        // Tiếp tục chain
        chain.doFilter(request, response);
    }
    
}
