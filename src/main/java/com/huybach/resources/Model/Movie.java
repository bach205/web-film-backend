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
public class Movie {
    private long id;
    private String title;
    private String description;
    private String category;
    private int releaseDate;
    private String country;
    private String imageURL;
    private int totalView;
    private double rate;
    private int rateQuantity;
    private int totalEpisode;
    private String status;
    private List<String> genre;

    public Movie() {
    }

    public Movie(long id, String title, String description, String category, int releaseDate, String country, String imageURL, int totalView, double rate, int rateQuantity, int totalEpisode, String status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.category = category;
        this.releaseDate = releaseDate;
        this.country = country;
        this.imageURL = imageURL;
        this.totalView = totalView;
        this.rate = rate;
        this.rateQuantity = rateQuantity;
        this.totalEpisode = totalEpisode;
        this.status = status;
    }

    public Movie(String title, String description, String category, int releaseDate, String country, String imageURL, List<String> genre) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.releaseDate = releaseDate;
        this.country = country;
        this.imageURL = imageURL;
        this.genre = genre;
    }

    
    
    public Movie(long id, String title, String description, String category, int releaseDate, String country, String imageURL, int totalView, double rate, int rateQuantity, int totalEpisode, String status, List<String> genre) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.category = category;
        this.releaseDate = releaseDate;
        this.country = country;
        this.imageURL = imageURL;
        this.totalView = totalView;
        this.rate = rate;
        this.rateQuantity = rateQuantity;
        this.totalEpisode = totalEpisode;
        this.status = status;
        this.genre = genre;
    }
    

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    

    
    
    public int getTotalView() {
        return totalView;
    }

    public void setTotalView(int totalView) {
        this.totalView = totalView;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
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

   

    public int getRateQuantity() {
        return rateQuantity;
    }

    public void setRateQuantity(int rateQuantity) {
        this.rateQuantity = rateQuantity;
    }

    public List<String> getGenre() {
        return genre;
    }

    public void setGenre(List<String> genre) {
        this.genre = genre;
    }

    @Override
    public String toString() {
        return "Movie{" + "id=" + id + ", title=" + title + ", description=" + description + ", category=" + category + ", releaseDate=" + releaseDate + ", country=" + country + ", imageURL=" + imageURL + ", totalView=" + totalView + ", rate=" + rate + ", rateQuantity=" + rateQuantity + ", totalEpisode=" + totalEpisode + ", status=" + status + ", genre=" + genre + '}';
    }

    
    
    

    

    
    
    
    
}