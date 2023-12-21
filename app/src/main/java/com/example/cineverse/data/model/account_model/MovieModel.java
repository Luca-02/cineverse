package com.example.cineverse.data.model.account_model;

import com.google.gson.annotations.SerializedName;

public class MovieModel{
    private int id;
    private String title;
    @SerializedName("poster_path")
    private String imgUrl;

    @SerializedName("vote_average")
    private double scoreMovie;

    public MovieModel(int id, String title, String imgUrl, Double scoreMovie) {
        this.id = id;
        this.title = title;
        this.imgUrl = imgUrl;
        this.scoreMovie = scoreMovie;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public double getScoreMovie() {
        return scoreMovie;
    }
    public void setScoreMovie(double scoreMovie) {
        this.scoreMovie = scoreMovie;
    }
}
