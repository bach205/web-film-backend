/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.huybach.resources.Model;

/**
 *
 * @author HOME PC
 */
public class MovieIdAndEpisode {
    private long movieId;
    private int episode;

    public MovieIdAndEpisode(long movieId, int episode) {
        this.movieId = movieId;
        this.episode = episode;
    }

    public MovieIdAndEpisode() {
    }

    public long getMovieId() {
        return movieId;
    }

    public void setMovieId(long movieId) {
        this.movieId = movieId;
    }

    public int getEpisode() {
        return episode;
    }

    public void setEpisode(int episode) {
        this.episode = episode;
    }
    
}
