package com.example.cineverse.data.model.content.poster;

import java.util.List;

public class PosterTvApiResponse extends AbstractPosterApiResponse<PosterTv> {

    public PosterTvApiResponse(int page, List<PosterTv> results, int totalPages, int totalResults) {
        super(page, results, totalPages, totalResults);
    }

}