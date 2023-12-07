package com.example.cineverse.data.model.content.poster;

public class PosterTv extends AbstractPoster {

    private String name;

    public PosterTv(int id, String posterPath, String backdropPath, String name) {
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
