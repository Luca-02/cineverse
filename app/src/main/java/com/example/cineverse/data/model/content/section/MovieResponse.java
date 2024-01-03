package com.example.cineverse.data.model.content.section;


import com.example.cineverse.data.model.content.AbstractContentResponse;

import java.util.List;

/**
 * The {@link MovieResponse} class represents a response containing a list of movie content.
 * It extends the {@link AbstractContentResponse} class with a generic type of {@link Movie}.
 */
public class MovieResponse extends AbstractContentResponse<Movie> {

    public MovieResponse(int page, List<Movie> results, int totalPages, int totalResults) {
        super(page, results, totalPages, totalResults);
    }

    /**
     * Constructs a {@link MovieResponse} instance with the converted list of movies.
     *
     * @param convertedResult The list of {@link Movie} converted from database entities.
     */
    public MovieResponse(List<Movie> convertedResult) {
        super(convertedResult);
    }

}
