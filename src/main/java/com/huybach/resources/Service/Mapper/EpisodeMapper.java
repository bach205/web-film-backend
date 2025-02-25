/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.huybach.resources.Service.Mapper;

import com.huybach.resources.Model.Episode;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author HOME PC
 */
public class EpisodeMapper implements RowMapper{

    @Override
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        Episode episode = new Episode();
        episode.setMovieId(rs.getLong("id"));
        episode.setCountry(rs.getString("country"));
        episode.setDescription(rs.getString("description"));
        episode.setCategory(rs.getString("category"));
        episode.setImageURL(rs.getString("imageURL"));
        episode.setView(rs.getLong("view"));
        episode.setReleaseDate(rs.getInt("releaseDate"));
        episode.setTitle(rs.getString("title"));
        episode.setTotalEpisode(rs.getInt("totalEpisode"));
        episode.setTotalView(rs.getInt("totalView"));
        episode.setRate(rs.getDouble("rate"));
        episode.setRateQuantity(rs.getInt("rateQuantity"));
        episode.setEpisode(rs.getLong("episode"));
        episode.setStatus(rs.getString("status"));
        episode.setVideoURL(rs.getString("videoURL"));
        return episode;
    }
    
}
