/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.huybach.resources.Model;

import java.util.List;

/**
 *
 * @author HOME PC
 */
public class Episode {
    private long movieId;
    private String title;
    private String description;
    private String category;
    private int releaseDate;
    private String country;
    private String imageURL;
    private int totalEpisode;
    private long view;
    private String videoURL;
    List<String> genre;

    public Episode(long movieId, String title, String description, String category, int releaseDate, String country, String imageURL, int totalEpisode, long view,String videoURL, List<String> genre) {
        this.movieId = movieId;
        this.title = title;
        this.description = description;
        this.category = category;
        this.releaseDate = releaseDate;
        this.country = country;
        this.imageURL = imageURL;
        this.totalEpisode = totalEpisode;
        this.view = view;
        this.genre = genre;
        this.videoURL = videoURL;
    }

    public Episode() {
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    
    public long getView() {
        return view;
    }

    public void setView(long view) {
        this.view = view;
    }

    public List<String> getGenre() {
        return genre;
    }

    public void setGenre(List<String> genre) {
        this.genre = genre;
    }

    

    public long getMovieId() {
        return movieId;
    }

    public void setMovieId(long movieId) {
        this.movieId = movieId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(int releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public int getTotalEpisode() {
        return totalEpisode;
    }

    public void setTotalEpisode(int totalEpisode) {
        this.totalEpisode = totalEpisode;
    }

    @Override
    public String toString() {
        return "Episode{" + "movieId=" + movieId + ", title=" + title + ", description=" + description + ", category=" + category + ", releaseDate=" + releaseDate + ", country=" + country + ", imageURL=" + imageURL + ", totalEpisode=" + totalEpisode + '}';
    }
    
    
}
