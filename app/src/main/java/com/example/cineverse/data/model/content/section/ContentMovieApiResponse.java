package com.example.cineverse.data.model.content.section;


import com.example.cineverse.data.model.content.AbstractContentApiResponse;

import java.util.List;

public class ContentMovieApiResponse extends AbstractContentApiResponse<ContentMovie> {

    public ContentMovieApiResponse(int page, List<ContentMovie> results, int totalPages, int totalResults) {
        super(page, results, totalPages, totalResults);
    }

}
