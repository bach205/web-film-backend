/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.huybach.resources.Service.repo;

import com.huybach.resources.Model.Episode;
import com.huybach.resources.Model.Movie;
import com.huybach.resources.Service.Mapper.EpisodeMapper;
import com.huybach.resources.Service.Mapper.EpisodeWithoutGenreViewMapper;
import com.huybach.resources.Service.Mapper.EpisodeWithoutViewGenreVideoURLMapper;
import com.huybach.resources.Service.Mapper.MovieMapper;
import com.huybach.resources.Service.Mapper.SearchMapper;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
    
    public long getMovieIdByTitle(String title){
        String query = "select id from movies where title = ?";
        return db.query(query,new Object[]{title},(rs)->{
            if(rs.next()){
                return rs.getLong("id");
            }
            return (long) -1;
        });
    }
    
    public List<Movie> getTrendingMovies() {
        String query = "select * from movies where id in (select top 12 movieId from (select movieId,sum([view]) as [view]  "
                + "from episodes group by movieId) as countView order by [view] desc)";
        return db.query(query, new MovieMapper());
    }

    public List<Movie> getLatestMoviesByCategory(String category) {
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

    public Episode getMovieAttributeWithTotalEpisode(String movieTitle, long episode) {
        String subQuery = "select a.id,a.title,a.[description],a.category,a.releaseDate,a.country,a.imageURL,count(b.movieId) as totalEpisode from movies a "
                + "join episodes b on a.id = b.movieId "
                + "where a.title = ? "
                + "group by a.id,a.title,a.[description],a.category,a.releaseDate,a.country,a.imageURL ";
        String query = "select sub.*,b.videoURL from (" + subQuery + ") as sub join episodes b on b.movieId = sub.id "
                + " where episode = ?";
        return (Episode) db.queryForObject(query, new Object[]{movieTitle, episode}, new EpisodeWithoutGenreViewMapper());
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
                + "where b.name in (" + genre + ") "
                + "group by a.movieId "
                + "order by count(b.name) desc)";
        return db.query(query, new MovieMapper());
    }

    public List<Episode> searchMovie(String extraQuery) {
        String query = "select sub.*,sum([view]) as [view] from (select a.* from movies a "
                + " join movies_genres b on a.id = b.movieId "
                + " join genres c on b.genreId = c.id "
                + "" + extraQuery + " ) as sub "
                + " join episodes b on b.movieId = sub.id "
                + " group by sub.id,sub.title,sub.description,sub.category,sub.releaseDate,sub.country,sub.imageURL";
        return db.query(query, new SearchMapper());
    }

    public int addInWatchLater(long userId, long movieId) {
        String query = "insert into watchLater values (?,?)";
        return db.update(query, new Object[]{userId, movieId});
    }

    //lay episode nay la thieu totalView voi list genre
    public List<Episode> getWatchLaterList(long userId) {
        String subQuery = "select a.* from movies a "
                + " join watchLater b on b.movieId  = a.id "
                + " where b.userId = ?";
        String query = "select sub.* ,count(b.movieId) as totalEpisode,sum(b.[view]) as [view] from(" + subQuery + ")  as sub "
                + " join episodes b on sub.id = b.movieId"
                + " group by sub.id,sub.title,sub.description,sub.category,sub.releaseDate,sub.country,sub.imageURL";
        return db.query(query, new Object[]{userId}, new EpisodeMapper());
    }

    public int deleteFromWatchLaterList(long userId, long movieId) {
        String query = "delete from watchLater where userId = ? and movieId =?";
        return db.update(query, new Object[]{userId, movieId});
    }

    public List<Episode> getTop5MostAndMinView() {
        String query = " with  "
                + "TopMostView as ( "
                + "	select TOP 5 a.*,sum(b.[view]) as [view],avg(b.[view]) as totalEpisode from movies a  "
                + "join episodes b on a.id = b.movieId "
                + "group by a.id,a.title,a.[description],a.category,a.releaseDate,a.country,a.imageURL "
                + "order by sum(b.[view]) desc "
                + "), "
                + "TopLeastView as ( "
                + "	select TOP 5 a.*,sum(b.[view]) as [view],avg(b.[view]) as totalEpisode from movies a  "
                + "join episodes b on a.id = b.movieId "
                + "group by a.id,a.title,a.[description],a.category,a.releaseDate,a.country,a.imageURL "
                + "order by sum(b.[view]) asc "
                + ") "
                + " "
                + "select * from TopMostView "
                + "union all "
                + "select * from TopLeastView";
        return db.query(query, new EpisodeMapper());
    }

    public List<Long> getTotalMovieAndTotalView() {
        String query = "select count(id) as totalMovie, sum([view]) as totalView from (select a.*,sum(b.[view]) as [view],avg(b.[view]) as avgView from movies a   "
                + "join episodes b on a.id = b.movieId  "
                + "group by a.id,a.title,a.[description],a.category,a.releaseDate,a.country,a.imageURL) as sub";
        List<Long> result = new ArrayList<>();
        return db.query(query, (rs) -> {
            if (rs.next()) {
                result.add(rs.getLong("totalMovie"));
                result.add(rs.getLong("totalView"));
                return result;
            }
            return result;
        });
    }
    
    public int addMovie(String title,String description,String category,int releaseDate,String country,String imageURL){
        String query = "insert into movies values (?,?,?,?,?,?)";
        return db.update(query,new Object[]{title,description,category,releaseDate,country,imageURL});
    }
    
    public int addNewEpisode(long movieId,int episode,String videoURL){
        String query = "insert into episodes (movieId,episode,videoURL) values (?,?,?)";
        return db.update(query,new Object[]{movieId,episode,videoURL});
    }
    
    //(movieId , ? ),
    public int addMovieGenre(List<Integer> genreList,long movieId){
        
        String subQuery ="";
        for(int genre : genreList){
            subQuery+="("+movieId+","+genre+"),";
        }
        subQuery = subQuery.substring(0,subQuery.length()-1);
        String query = "insert into movies_genres (movieId,genreId) values " + subQuery;
        return db.update(query);
    }
    
    public List<Integer> getGenreId(List<String> genreList){
        String subQuery = " (";
        for(String word : genreList){
            subQuery+="N'"+word+"',";
        }
        subQuery = subQuery.substring(0,subQuery.length()-1);
        subQuery+=") ";
        System.out.println(subQuery);
        String query = "select id from genres where name in "+subQuery;
        List<Integer> result = db.query(query,(rs)->{
            List<Integer> a = new ArrayList<>();
            while(rs.next()){
                a.add(rs.getInt("id"));
            }
            return a;
        });
        return result;
    }
}
