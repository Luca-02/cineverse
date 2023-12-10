package com.example.cineverse.data.model.content;

import com.google.gson.annotations.SerializedName;

public abstract class AbstractContent {

    private int id;
    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("backdrop_path")
    private String backdropPath;

    public AbstractContent(int id, String posterPath, String backdropPath) {
        this.id = id;
        this.posterPath = posterPath;
        this.backdropPath = backdropPath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public abstract String getName();

    public abstract void setName(String name);

    public abstract String getReleaseDate();

    public abstract void setReleaseDate(String releaseDate);

}
