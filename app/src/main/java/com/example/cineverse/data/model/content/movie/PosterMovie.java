package com.example.cineverse.data.model.content.movie;

import com.example.cineverse.data.model.content.AbstractPoster;
import com.google.gson.annotations.SerializedName;

public class PosterMovie extends AbstractPoster {

    @SerializedName("title")
    private String name;

    public PosterMovie(int id, String posterPath, String name) {
        super(id, posterPath);
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
