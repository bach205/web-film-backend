/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.huybach.resources.Service.repo;

import com.huybach.resources.Model.Episode;
import com.huybach.resources.Model.Movie;
import com.huybach.resources.Service.Mapper.EpisodeMapper;
import com.huybach.resources.Service.Mapper.MovieMapper;
import com.huybach.resources.Service.Mapper.SearchMapper;
import java.sql.Timestamp;
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

    public List<Movie> getTrendingMovies() {
        String query = "select * from movies where id in (select top 12 movieId from (select movieId,sum([view]) as [view] \n"
                + "from episodes group by movieId) as countView order by [view] desc)";
        return db.query(query, new MovieMapper());
    }

    public List<Movie> getLatestMoviesByGenre(String category) {
        String query = "select a.* from movies a join (select movieId,max(createAt) as createAt from episodes group by movieId) as b on a.id = b.movieId where a.category = N'" + category + "' order by b.createAt desc";
        return db.query(query, new MovieMapper());
    }

    public List<String> getMovieGenre(String movieTitle) {
        String query = "select c.name from movies a "
                + "join movies_genres b on a.id = b.movieId "
                + "join genres c on b.genreId = c.id "
                + "where a.title = ? ";
        List<String> result = db.query(query, new Object[]{movieTitle}, (rs) -> {
            List<String> list = new ArrayList<>();
            while (rs.next()) {
                list.add(rs.getString("name"));
            }
            return list;
        });
        return result;
    }

    public long getEpisodeView(String movieTitle, long episode) {
        String query = "select b.[view] from movies a join episodes b on a.id = b.movieId  where a.title = ? and b.episode = ?";
        return db.query(query, new Object[]{movieTitle, episode}, (rs) -> {
            //bat buoc phai rs.next()
            if (rs.next()) {
                return rs.getLong("view");
            } else {
                return (long) -1;
            }
        });
    }

    public Episode getMovieAttributeWithTotalEpisode(String movieTitle) {
        String query = "select a.id,a.title,a.[description],a.category,a.releaseDate,a.country,a.imageURL,count(b.movieId) as totalEpisode from movies a "
                + "join episodes b on a.id = b.movieId "
                + "where a.title = ? "
                + "group by a.id,a.title,a.[description],a.category,a.releaseDate,a.country,a.imageURL ";
        return (Episode) db.queryForObject(query, new String[]{movieTitle}, new EpisodeMapper());
    }

    public Timestamp getEpisodeVersionUpdateView(long movieId, int episode) throws Exception {
        String query = "select version from episodes where movieId = ? and episode = ?";
        return db.query(query, new Object[]{movieId, episode}, (rs) -> {
            if (rs.next()) {
                return rs.getTimestamp("version");
            } else {
                return null;
            }
        });
    }

    //convert vi trong sql version type dateTime luu den ca milis,trong khi java chi luu den s ma thoi
    public int updateEpisodeView(long movieId, int episode, Timestamp version) {
        String query = "update episodes set [view] = [view] +1, version = CURRENT_TIMESTAMP where CONVERT(VARCHAR, version, 120) = CONVERT(VARCHAR, ?, 120) and movieId = ? and episode = ?";
        return db.update(query, new Object[]{version, movieId, episode});
    }

    public List<Movie> getMovieByRelativeGenre(String genre) {
        String query = "select * from movies where id in (select Top 12 a.movieId from movies_genres a "
                + "join genres b on a.genreId = b.id "
                + "where b.name in ("+ genre +") "
                + "group by a.movieId "
                + "order by count(b.name) desc)";
        return db.query(query,new MovieMapper());
    }
    
    public List<Episode> searchMovie(String extraQuery){
        String query = "select sub.*,sum([view]) as [view] from (select a.* from movies a "
                + " join movies_genres b on a.id = b.movieId "
                + " join genres c on b.genreId = c.id "
                + ""+extraQuery+" ) as sub "
                + " join episodes b on b.movieId = sub.id "
                + " group by sub.id,sub.title,sub.description,sub.category,sub.releaseDate,sub.country,sub.imageURL";
        return db.query(query,new SearchMapper());
    }
}
