package com.example.cineverse.data.model.content.tv;

import com.example.cineverse.data.model.content.AbstractPoster;

public class PosterTv extends AbstractPoster {

    private String name;

    public PosterTv(int id, String posterPath) {
        super(id, posterPath);
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
