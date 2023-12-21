package com.example.cineverse.data.model.genre;

import com.example.cineverse.data.model.api.ApiResponse;

import java.util.List;

/**
 * The {@link GenreResponse} class represents the response containing a list of genres.
 */
public class GenreResponse
        implements ApiResponse {

    private List<Genre> genres;

    public GenreResponse(List<Genre> genres) {
        this.genres = genres;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

}
