package com.example.cineverse.data.model.content.section;


import com.example.cineverse.data.model.content.AbstractContentResponse;

import java.util.List;

public class MovieResponse extends AbstractContentResponse<MovieEntity> {

    public MovieResponse(int page, List<MovieEntity> results, int totalPages, int totalResults) {
        super(page, results, totalPages, totalResults);
    }

}
