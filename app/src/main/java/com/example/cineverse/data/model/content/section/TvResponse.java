package com.example.cineverse.data.model.content.section;

import com.example.cineverse.data.model.content.AbstractContentResponse;

import java.util.List;

public class TvResponse extends AbstractContentResponse<TvEntity> {

    public TvResponse(int page, List<TvEntity> results, int totalPages, int totalResults) {
        super(page, results, totalPages, totalResults);
    }

}