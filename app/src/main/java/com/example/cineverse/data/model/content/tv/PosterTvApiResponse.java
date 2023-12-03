package com.example.cineverse.data.model.content.tv;

import com.example.cineverse.data.model.content.AbstractPosterApiResponse;

import java.util.List;

public class PosterTvApiResponse extends AbstractPosterApiResponse<PosterTv> {

    public PosterTvApiResponse(int page, int totalPages,
                               int totalResults, List<PosterTv> results) {
        super(page, totalPages, totalResults, results);
    }
}