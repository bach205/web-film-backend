/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.huybach;

import com.huybach.resources.Model.Movie;
import com.huybach.resources.Service.repo.MovieJDBCTemplate;
import java.util.List;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

/**
 *
 * @author HOME PC
 */
public class Test {

    public static void main(String agrs[]) {
        AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
//        MovieJDBCTemplate db = new MovieJDBCTemplate();
//        List<Movie> a = db.getTrendingMovies();
//        System.out.println(a.get(0).toString());
    }
}
