package com.example.cineverse.data.model.content;

import com.google.gson.annotations.SerializedName;

public abstract class AbstractPoster {

    private int id;
    @SerializedName("poster_path")
    private String posterPath;

    public AbstractPoster(int id, String posterPath) {
        this.id = id;
        this.posterPath = posterPath;
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

    public abstract String getName();

    public abstract void setName(String name);

}
