/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.huybach.resources.Service.repo;

import com.huybach.resources.Model.Movie;
import com.huybach.resources.Service.Mapper.MovieMapper;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 *
 * @author HOME PC
 */
@Service
public class MovieJDBCTemplate {
    @Autowired
    JdbcTemplate db;
    
    public List<Movie> getTrendingMovies(){
        String query = "select * from movies where id in (select top 5 movieId from (select movieId,sum([view]) as [view] \n" +
    "from episodes group by movieId) as countView order by [view] desc)";
        return db.query(query,new MovieMapper());
    }
    
    public List<Movie> getLatestMoviesByGenre(String genre){
        String query ="select a.* from movies a join episodes b on a.id = b.movieId where a.genre = N'"+genre+"' order by b.createAt desc";
        return db.query(query,new MovieMapper());
    }
}
