package com.example.cineverse.data.model.content.poster;


import java.util.List;

public class PosterMovieApiResponse extends AbstractPosterApiResponse<PosterMovie> {

    public PosterMovieApiResponse(int page, List<PosterMovie> results, int totalPages, int totalResults) {
        super(page, results, totalPages, totalResults);
    }

}
