package com.example.cineverse.data.model.genre;

import com.example.cineverse.data.model.ApiResponse;

import java.util.List;

public class GenreApiResponse
        implements ApiResponse {

    private List<Genre> genres;

    public GenreApiResponse(List<Genre> genres) {
        this.genres = genres;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

}
