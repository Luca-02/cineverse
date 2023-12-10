package com.example.cineverse.data.model.content.section;

import com.example.cineverse.data.model.content.AbstractContent;
import com.google.gson.annotations.SerializedName;

public class ContentTv extends AbstractContent {

    private String name;
    @SerializedName("first_air_date")
    private String releaseDate;

    public ContentTv(int id, String posterPath, String backdropPath, String name) {
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

    @Override
    public String getReleaseDate() {
        return releaseDate;
    }

    @Override
    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

}
