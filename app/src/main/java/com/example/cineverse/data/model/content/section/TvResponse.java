package com.example.cineverse.data.model.content.section;

import com.example.cineverse.data.model.content.AbstractContentResponse;

import java.util.List;

/**
 * The {@link TvResponse} class represents a response containing a list of TV show content.
 * It extends the {@link AbstractContentResponse} class with a generic type of {@link Tv}.
 */
public class TvResponse extends AbstractContentResponse<Tv> {

    public TvResponse(int page, List<Tv> results, int totalPages, int totalResults) {
        super(page, results, totalPages, totalResults);
    }

    /**
     * Constructs a {@link TvResponse} instance with the converted list of TV shows.
     *
     * @param convertedResult The list of {@link Tv} shows converted from database entities.
     */
    public TvResponse(List<Tv> convertedResult) {
        super(convertedResult);
    }

}