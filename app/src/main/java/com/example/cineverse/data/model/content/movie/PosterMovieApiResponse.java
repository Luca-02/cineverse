package com.example.cineverse.data.model.content.movie;

import com.example.cineverse.data.model.content.AbstractPosterApiResponse;

import java.util.List;

public class PosterMovieApiResponse extends AbstractPosterApiResponse<PosterMovie> {

    public PosterMovieApiResponse(int page, int totalPages,
                                  int totalResults, List<PosterMovie> results) {
        super(page, totalPages, totalResults, results);
    }
}
