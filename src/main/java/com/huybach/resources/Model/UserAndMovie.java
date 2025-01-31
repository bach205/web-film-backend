/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.huybach.resources.Model;

/**
 *
 * @author HOME PC
 */
public class UserAndMovie {
    private long userId;
    private long movieId;

    public UserAndMovie() {
    }

    public UserAndMovie(long userId, long movieId) {
        this.userId = userId;
        this.movieId = movieId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getMovieId() {
        return movieId;
    }

    public void setMovieId(long movieId) {
        this.movieId = movieId;
    }
    
}
