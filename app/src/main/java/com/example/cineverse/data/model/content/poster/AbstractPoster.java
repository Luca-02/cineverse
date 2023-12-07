package com.example.cineverse.data.model.content.poster;

import com.google.gson.annotations.SerializedName;

public abstract class AbstractPoster {

    private int id;
    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("backdrop_path")
    private String backdropPath;

    public AbstractPoster(int id, String posterPath, String backdropPath) {
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

}
