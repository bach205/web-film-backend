/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.huybach.resources.Service.Mapper;

import com.huybach.resources.Model.Movie;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author HOME PC
 */
public class MovieMapper implements RowMapper{

    @Override
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        Movie movie = new Movie();
        movie.setId(rs.getLong("id"));
        movie.setCountry(rs.getString("country"));
        movie.setDescription(rs.getString("description"));
        movie.setCategory(rs.getString("category"));
        movie.setImageURL(rs.getString("imageURL"));
        movie.setReleaseDate(rs.getInt("releaseDate"));
        movie.setTitle(rs.getString("title"));
        return movie;
    }
    
}
