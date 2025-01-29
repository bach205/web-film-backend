/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.huybach.resources.Model;

/**
 *
 * @author HOME PC
 */
public class Movie {
    private long id;
    private String title;
    private String description;
    private String category;
    private int releaseDate;
    private String country;
    private String imageURL;
    private int totalEpisode;

    public Movie() {
    }

    public Movie(long id, String title, String description, String category, int releaseDate, String country, String imageURL,int totalEpisode) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.category = category;
        this.releaseDate = releaseDate;
        this.country = country;
        this.imageURL = imageURL;
        this.totalEpisode = totalEpisode;
    }

    public int getTotalEpisode() {
        return totalEpisode;
    }

    public void setTotalEpisode(int totalEpisode) {
        this.totalEpisode = totalEpisode;
    }

    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "Movie{" + "id=" + id + ", title=" + title + ", description=" + description + ", category=" + category + ", releaseDate=" + releaseDate + ", country=" + country + ", imageURL=" + imageURL + ", totalEpisode=" + totalEpisode + '}';
    }

    
    
    
    
}