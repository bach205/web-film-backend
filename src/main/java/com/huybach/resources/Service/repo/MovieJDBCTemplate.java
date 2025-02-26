/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.huybach.resources.Service.repo;

import com.huybach.resources.Model.Episode;
import com.huybach.resources.Model.Movie;
import com.huybach.resources.Service.Mapper.EpisodeMapper;
import com.huybach.resources.Service.Mapper.MovieMapper;
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
    
    public static String moviesAtribute() {
        return "title,description,category,releaseDate,country,imageURL";
    }
    
    public static String getEpisodeQuery() {
        return "with episodes_cte as( "
                + "select a.movieId,count(a.id) as totalEpisode from episodes a "
                + "left join episodes b on a.movieId = b.movieId and a.episode = b.episode "
                + "group by a.movieId "
                + ") "
                + "select a.id,a.title,a.description,a.category,a.releaseDate,a.country, "
                + "a.imageURL,d.videoURL,d.episode,count(b.viewId) as totalView,isnull(avg(c.rate),0) "
                + "as rate, count(c.rate) as rateQuantity,e.totalEpisode,d.createAt,d.[status],f.[view] "
                + "from movies a  "
                + "left join view_tracking b on a.id = b.movieId  "
                + "left join ratings c on a.id = c.movieId "
                + "left join episodes d on a.id = d.movieId "
                + "left join episodes_cte e on a.id = e.movieId "
                + "left join (select a.movieId,a.episode,count(b.viewId) as [view] from episodes a   "
                + "left join view_tracking b on a.movieId = b.movieId and a.episode = b.episode  "
                + "group by a.movieId,a.episode) f on f.movieId = a.id and f.episode = d.episode "
                + "group by a.id,a.title,a.description,a.category,a.releaseDate,a.country,a.imageURL "
                + ",d.videoURL,d.episode,d.createAt,d.status,e.totalEpisode,f.[view] ";
    }
    
    public static String getDefaultMovieAttributeQuery(String quantityRow, String CTETable) {
        return "with filtered as ( "
                + "select * from view_tracking a "
                + ")"
                + ", "
                + "episodes_cte as( "
                + "select a.movieId,count(a.id) as totalEpisode from episodes a "
                + "group by a.movieId "
                + ") "
                + ", " + CTETable + " "
                + "select " + quantityRow + " a.*,count(b.viewId) as totalView,isnull(avg(c.rate),0) as rate, count(c.rate) as rateQuantity,d.totalEpisode from movies a "
                + "left join filtered b on a.id = b.movieId "
                + "left join ratings c on a.id = c.movieId "
                + "left join episodes_cte d on a.id = d.movieId "
                + "group by a.id,a.title,a.description,a.category,a.releaseDate,a.country,a.imageURL ,a.status,d.totalEpisode ";
    }
    
    private static String getDefaultMovieAttributeQuery(int dateDiff, String quantityRow) {
        return "with filtered as ( "
                + "select * from view_tracking a where DateDiff(day,a.createAt,getDate()) <= " + dateDiff + " "
                + ") "
                + ", "
                + "episodes_cte as( "
                + "select a.movieId,count(a.id) as totalEpisode from episodes a "
                + "group by a.movieId "
                + ") "
                + "select " + quantityRow + " a.*,count(b.viewId) as totalView,isnull(avg(c.rate),0) as rate, count(c.rate) as rateQuantity,d.totalEpisode from movies a "
                + "left join filtered b on a.id = b.movieId "
                + "left join ratings c on a.id = c.movieId "
                + "left join episodes_cte d on a.id = d.movieId "
                + "group by a.id,a.title,a.description,a.category,a.releaseDate,a.country,a.imageURL  ,a.status,d.totalEpisode ";
    }
    
    private static String getDefaultMovieAttributeQuery(String quantityRow) {
        return "with filtered as ( "
                + "select * from view_tracking a"
                + ") "
                + ", "
                + "episodes_cte as( "
                + "select a.movieId,count(a.id) as totalEpisode from episodes a "
                + "group by a.movieId "
                + ") "
                + "select " + quantityRow + " a.*,count(b.viewId) as totalView,isnull(avg(c.rate),0) as rate, count(c.rate) as rateQuantity,d.totalEpisode from movies a "
                + "left join filtered b on a.id = b.movieId "
                + "left join ratings c on a.id = c.movieId "
                + "left join episodes_cte d on a.id = d.movieId "
                + "group by a.id,a.title,a.description,a.category,a.releaseDate,a.country,a.imageURL  ,a.status,d.totalEpisode ";
    }
    
    private static String getDefaultMovieAttributeQuery(int dateDiff) {
        return "with filtered as ( "
                + "select * from view_tracking a where DateDiff(day,a.createAt,getDate()) <= " + dateDiff + " "
                + ") "
                + ", "
                + "episodes_cte as( "
                + "select a.movieId,count(a.id) as totalEpisode from episodes a "
                + "group by a.movieId "
                + ") "
                + "select Top  a.*,count(b.viewId) as totalView,isnull(avg(c.rate),0) as rate, count(c.rate) as rateQuantity,count(d.id) as totalEpisode from movies a "
                + "left join filtered b on a.id = b.movieId "
                + "left join ratings c on a.id = c.movieId "
                + "left join episodes d on a.id = d.movieId "
                + "group by a.id,a.title,a.description,a.category,a.releaseDate,a.country,a.imageURL  ,a.status ";
    }
    
    public static String getDefaultMovieAttributeQuery() {
        return "with filtered as ( "
                + "select * from view_tracking a "
                + ") "
                + ", "
                + "episodes_cte as( "
                + "select a.movieId,count(a.id) as totalEpisode from episodes a "
                + "group by a.movieId "
                + ") "
                + "select a.*,count(b.viewId) as totalView,isnull(avg(c.rate),0) as rate, count(c.rate) as rateQuantity,d.totalEpisode from movies a "
                + "left join filtered b on a.id = b.movieId "
                + "left join ratings c on a.id = c.movieId "
                + "left join episodes_cte d on a.id = d.movieId "
                + "group by a.id,a.title,a.description,a.category,a.releaseDate,a.country,a.imageURL  ,a.status,d.totalEpisode ";
    }
    
    public long getMovieIdByTitle(String title) {
        String query = "select id from movies where title = ?";
        return db.query(query, new Object[]{title}, (rs) -> {
            if (rs.next()) {
                return rs.getLong("id");
            }
            return (long) -1;
        });
    }

    //fix 1.0
    public List<Movie> getTrendingMovies() {
        String query = getDefaultMovieAttributeQuery(100, "Top 12");
        return db.query(query, new MovieMapper());
    }

    //fix 1.0
    public List<Movie> getLatestMoviesByCategory(String category) {
        String query = getDefaultMovieAttributeQuery(100, "Top 12")
                + "having a.category = ?";
        return db.query(query, new Object[]{category}, new MovieMapper());
    }

    // 1.0
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

    //fix 1.0
    public int updateEpisodeView(long movieId, int episode) {
        String query = "insert into view_tracking(movieId,episode) values (?,?)";
        return db.update(query, new Object[]{movieId, episode});
    }

    //fix 1.0
    public List<Movie> getMovieByRelativeGenre(String genre) {
        String query = getDefaultMovieAttributeQuery("Top 12") + " having a.id in (select Top 12 a.movieId from movies_genres a "
                + "join genres b on a.genreId = b.id "
                + "where b.name in (" + genre + ") "
                + "group by a.movieId "
                + "order by count(a.movieId) )";
        return db.query(query, new MovieMapper());
    }

    //fix 1.0
    public List<Episode> searchMovie(String extraQuery, String sortQuery) {
        String query
                = "with filtered as (  "
                + "select * from view_tracking a "
                + "),  "
                + "episodes_cte as( "
                + "select a.movieId,count(a.id) as totalEpisode from episodes a "
                + "group by a.movieId "
                + "), "
                + "movies_cte as (  "
                + "select a.* from movies a join movies_genres b on a.id = b.movieId join genres c on b.genreId = c.id  " + extraQuery
                + ")  "
                + "select a.*,count(b.viewId) as totalView,isnull(avg(c.rate),0) as rate, count(c.rate) as rateQuantity,d.totalEpisode from movies_cte a   "
                + "left join filtered b on a.id = b.movieId   "
                + "left join ratings c on a.id = c.movieId  "
                + "left join episodes_cte d on a.id = d.movieId  "
                + "group by a.id,a.title,a.description,a.category,a.releaseDate,a.country,a.imageURL,a.status,d.totalEpisode   "
                + sortQuery;
        //+ "order by smt asc||desc"
        System.out.println(query);
        return db.query(query, new MovieMapper());
    }

    //1.0 (khong co gi can fix)
    public int addInWatchLater(long userId, long movieId) {
        String query = "insert into watchLater values (?,?)";
        return db.update(query, new Object[]{userId, movieId});
    }

    //fix 1.0 
    //lay episode nay la thieu totalView voi list genre
    public List<Episode> getWatchLaterList(long userId) {
        String CTETable = "select a.* from movies a "
                + " join watchLater b on b.movieId  = a.id "
                + " where b.userId = ?";
        String query
                = "with filtered as (  "
                + "select * from view_tracking a "
                + "),  "
                + "episodes_cte as( "
                + "select a.movieId,count(a.id) as totalEpisode from episodes a "
                + "group by a.movieId "
                + "), "
                + "movies_cte as (  "
                + CTETable
                + ")  "
                + "select a.*,count(b.viewId) as totalView,isnull(avg(c.rate),0) as rate, count(c.rate) as rateQuantity,d.totalEpisode from movies_cte a   "
                + "left join filtered b on a.id = b.movieId   "
                + "left join ratings c on a.id = c.movieId  "
                + "left join episodes_cte d on a.id = d.movieId  "
                + "group by a.id,a.title,a.description,a.category,a.releaseDate,a.country,a.imageURL,a.status,d.totalEpisode   ";
        
        return db.query(query, new Object[]{userId}, new MovieMapper());
    }
    
    public int deleteFromWatchLaterList(long userId, long movieId) {
        String query = "delete from watchLater where userId = ? and movieId =?";
        return db.update(query, new Object[]{userId, movieId});
    }

    //fix 1.0
    public List<Episode> getMostViewByDuration(String criterion) {
        int duration = 7;
        if (criterion.equalsIgnoreCase("week")) {
            duration = 7;
        } else if (criterion.equalsIgnoreCase("month")) {
            duration = 31;
        } else if (criterion.equalsIgnoreCase("year")) {
            duration = 365;
        }
        System.out.println(duration);
        String query = getDefaultMovieAttributeQuery(duration, "Top 5")
                + " order by count(viewId) desc";
        return db.query(query, new MovieMapper());
    }
    
    public int addMovie(String title, String description, String category, int releaseDate, String country, String imageURL) {
        String query = "insert into movies values (?,?,?,?,?,?)";
        return db.update(query, new Object[]{title, description, category, releaseDate, country, imageURL});
    }
    
    public int addNewEpisode(long movieId, int episode, String videoURL) {
        String query = "insert into episodes (movieId,episode,videoURL) values (?,?,?)";
        return db.update(query, new Object[]{movieId, episode, videoURL});
    }

    //1.0
    public int addMovieGenre(List<Integer> genreList, long movieId) {
        
        String subQuery = "";
        for (int genre : genreList) {
            subQuery += "(" + movieId + "," + genre + "),";
        }
        subQuery = subQuery.substring(0, subQuery.length() - 1);
        String query = "insert into movies_genres (movieId,genreId) values " + subQuery;
        return db.update(query);
    }

    //1.0
    public int addMovieManagementGenre(List<Integer> genreList, long movieId) {
        
        String subQuery = "";
        for (int genre : genreList) {
            subQuery += "(" + movieId + "," + genre + "),";
        }
        subQuery = subQuery.substring(0, subQuery.length() - 1);
        String query = "insert into movies_genres (movieId,genreId) values " + subQuery;
        return db.update(query);
    }
    
    public List<Integer> getGenreId(List<String> genreList) {
        String subQuery = " (";
        for (String word : genreList) {
            subQuery += "N'" + word + "',";
        }
        subQuery = subQuery.substring(0, subQuery.length() - 1);
        subQuery += ") ";
        String query = "select id from genres where name in " + subQuery;
        List<Integer> result = db.query(query, (rs) -> {
            List<Integer> a = new ArrayList<>();
            while (rs.next()) {
                a.add(rs.getInt("id"));
            }
            return a;
        });
        return result;
    }
    
    public int deleteUserIdFromWatchLater(long userId) {
        String query = "delete from watchLater where userId = ?";
        return db.update(query, new Object[]{userId});
    }

    //fix 1.0
    public List<Movie> getAllMovieSortByCriterion(String criterion, String direction) {
        String query = getDefaultMovieAttributeQuery();
        if (criterion != null && !criterion.isBlank()) {
            criterion = "a." + criterion;
            query += " order by " + criterion + " " + direction;
        }
        return db.query(query, new MovieMapper());
    }

    //1.0
    public Episode getEpisode(String movieTitle, long episode) {
        String query = getEpisodeQuery() + " having a.title = ? and d.episode = ?";
        return (Episode) db.queryForObject(query, new Object[]{movieTitle, episode}, new EpisodeMapper());
    }

    //1.0
    public Movie getMovieWithoutGenre(long movieId) {
        String query = getDefaultMovieAttributeQuery() + " having a.id = ?";
        return (Movie) db.queryForObject(query, new Object[]{movieId}, new MovieMapper());
    }

    //1.0
    public int adminCreateMovie(Movie movie) {
        String query = "insert into movies (" + moviesAtribute() + ") values(?,?,?,?,?,?)";
        return db.update(query, new Object[]{movie.getTitle(), movie.getDescription(), movie.getCategory(), movie.getReleaseDate(), movie.getCountry(), movie.getImageURL()});
        
    }

    //1.0
    public int adminUpdateMovie(Movie movie) {
        String query = "update movies set title = ?, description = ?, category = ?, releaseDate = ?, country = ?, imageURL= ?, status = ? where id = ?";
        return db.update(query, new Object[]{movie.getTitle(), movie.getDescription(), movie.getCategory(), movie.getReleaseDate(), movie.getCountry(), movie.getImageURL(), "allowed", movie.getId()});
    }

    //fix 1.0
    public int adminDeleteMovieById(long movieId) {
        String query = "delete from movies_genres where movieId = ?";
        int a = db.update(query, new Object[]{movieId});
        query = "delete from episodes where movieId = ?";
        a += db.update(query, new Object[]{movieId});
        query = "delete from movies where id = ?";
        a += db.update(query, new Object[]{movieId});
        query = "delete from watchLater where movieId = ?";
        a += db.update(query, new Object[]{movieId});
        query = "delete from view_tracking where movieId = ?";
        a += db.update(query, new Object[]{movieId});
        query = "delete from ratings where movieId = ?";
        a += db.update(query, new Object[]{movieId});
        query = "delete from comments where movieId = ?";
        a += db.update(query, new Object[]{movieId});
        return a;
    }

    //1.0
    public int managerCreateMovie(Movie movie, String status) {
        String query = "insert into movies_management (" + moviesAtribute() + ",[status],[type]) values (?,?,?,?,?,?,?,?) ";
        int a = db.update(query, new Object[]{movie.getTitle(), movie.getDescription(), movie.getCategory(), movie.getReleaseDate(), movie.getCountry(), movie.getImageURL(), status, "create"});
        a += insertIntoMovieManagementGenre(movie);
        return a;
    }

    //1.0
    public int managerDeleteMovie(Movie movie, String status) {
        String query = "insert into movies_management (" + moviesAtribute() + ",[status],[type]) values (?,?,?,?,?,?,?,?) ";
        int a = db.update(query, new Object[]{movie.getTitle(), movie.getDescription(), movie.getCategory(), movie.getReleaseDate(), movie.getCountry(), movie.getImageURL(), status, "delete"});
        List<Integer> listGenre = getGenreId(movie.getGenre());
        a += addMovieManagementGenre(listGenre, movie.getId());
        return a;
    }
    //1.0

    public int managerUpdateMovie(Movie movie, String status) {
        String query = "insert into movies_management (" + moviesAtribute() + ",[status],[type]) values (?,?,?,?,?,?,?,?) ";
        int a = db.update(query, new Object[]{movie.getTitle(), movie.getDescription(), movie.getCategory(), movie.getReleaseDate(), movie.getCountry(), movie.getImageURL(), status, "update"});
        a += insertIntoMovieManagementGenre(movie);
        return a;
    }
    
    public int insertIntoMovieManagementGenre(Movie movie) {
        List<Integer> genreList = getGenreId(movie.getGenre());
        long movieId = getMovieManagementIdByTitle(movie.getTitle());
        String subQuery = "";
        for (int genre : genreList) {
            subQuery += "(" + movieId + "," + genre + "),";
        }
        subQuery = subQuery.substring(0, subQuery.length() - 1);
        String query = "insert into movies_management_genre (movieManagementId,genreId) values " + subQuery;
        return db.update(query);
    }
    
    public long getMovieManagementIdByTitle(String title) {
        String query = "Select id from movies_management where title = ?";
        return db.query(query, new Object[]{title}, (rs) -> {
            return rs.next() ? rs.getLong("id") : 0;
        });
    }

    // 1.0
    public List<String> getMovieManagementGenre(String movieTitle) {
        String query = "select c.name from movies a "
                + "join movies_management_genre b on a.id = b.movieManagementId "
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
    
    public int addEpisode(long movieId, long episode, String videoURL) {
        String query = "insert into episodes (movieId,episode,videoURL) values (?,?,?)";
        return db.update(query, new Object[]{movieId, episode, videoURL});
    }
    
    public List<Integer> getEachCategoryStatics(String category) {
        String query = "SELECT "
                + "COALESCE(a.category, N'" + category + "') AS category,  "
                + "COUNT(b.viewId) AS totalView,  "
                + "c.hour AS hour "
                + "FROM hour_statics c  "
                + "LEFT JOIN view_tracking b ON c.hour = DATEPART(HOUR, b.createAt) "
                + "LEFT JOIN movies a ON a.id = b.movieId "
                + "GROUP BY c.hour, a.category "
                + "having a.category = ? or a.category is null "
                + "ORDER BY c.hour";
        return db.query(query, new Object[]{category}, (rs) -> {
            List<Integer> result = new ArrayList<>();
            for(int i = 0; i<24;i++){
                result.add(0);
            }
            while (rs.next()) {
                int a = rs.getInt("totalView");
                int hour= rs.getInt("hour");
                result.set(hour,a);
            }
            return result;
        });
    }

    public List<Integer> getAllCategoryStatics() {
        String query = "SELECT "
                + "COUNT(b.viewId) AS totalView,  "
                + "c.hour AS hour "
                + "FROM hour_statics c  "
                + "LEFT JOIN view_tracking b ON c.hour = DATEPART(HOUR, b.createAt) "
                + "LEFT JOIN movies a ON a.id = b.movieId "
                + "GROUP BY c.hour "
                + "ORDER BY c.hour ";
        return db.query(query, (rs) -> {
            List<Integer> result = new ArrayList<>();
            while (rs.next()) {
                int a = rs.getInt("totalView");
                result.add(a);
            }
            return result;
        });
    }
    
    public List<Integer> getTotalView(){
        String query = "select a.category, count(b.viewId) as totalView from movies a "
                + "join view_tracking b on a.id = b.movieId "
                + "group by a.category "
                + "order by a.category";
        return db.query(query, (rs)->{
            List<Integer> result = new ArrayList<>();
            while(rs.next()){
                result.add(rs.getInt("totalView"));
            }
            return result;
        });
    }
    
}
