package com.example.cineverse.data.model.content.section;

import com.example.cineverse.data.model.content.AbstractContentApiResponse;

import java.util.List;

public class ContentTvApiResponse extends AbstractContentApiResponse<ContentTv> {

    public ContentTvApiResponse(int page, List<ContentTv> results, int totalPages, int totalResults) {
        super(page, results, totalPages, totalResults);
    }

}