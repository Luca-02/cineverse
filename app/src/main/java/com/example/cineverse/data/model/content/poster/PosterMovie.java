package com.example.cineverse.data.model.content.poster;

import com.google.gson.annotations.SerializedName;

public class PosterMovie extends AbstractPoster {

    @SerializedName("title")
    private String name;

    public PosterMovie(int id, String posterPath, String backdropPath, String name) {
        super(id, posterPath, backdropPath);
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

}
