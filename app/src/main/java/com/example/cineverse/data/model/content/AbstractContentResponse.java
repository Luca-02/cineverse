package com.example.cineverse.data.model.content;

import static com.example.cineverse.utils.constant.Api.STARTING_PAGE;

import com.example.cineverse.data.model.api.ApiResponse;
import com.example.cineverse.data.model.content.section.Movie;
import com.example.cineverse.data.model.content.section.MovieResponse;
import com.example.cineverse.data.model.content.section.Tv;
import com.example.cineverse.data.model.content.section.TvResponse;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@link AbstractContentResponse} class is an abstract class representing the response
 * structure for content entities such as movies and TV shows.
 *
 * @param <T> The type of content entities (e.g., Movie, Tv) that the response contains.
 */
public abstract class AbstractContentResponse<T extends AbstractContent>
        implements ApiResponse {

    private int page;
    private List<T> results;
    @SerializedName("total_pages")
    private int totalPages;
    @SerializedName("total_results")
    private int totalResults;

    public AbstractContentResponse(int page, List<T> results, int totalPages, int totalResults) {
        this.page = page;
        this.results = results;
        this.totalPages = totalPages;
        this.totalResults = totalResults;
    }

    /**
     * Constructs an {@link AbstractContentResponse} instance with the provided list of results.
     *
     * @param results The list of content entities in the response.
     */
    public AbstractContentResponse(List<T> results) {
        this(STARTING_PAGE, results, STARTING_PAGE, results.size());
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }

    /**
     * Creates a response object based on the provided list of content entities and the content class type.
     *
     * @param contentEntityDbList The list of content entities retrieved from the database.
     * @param contentClass        The class type of the content entities (e.g., {@link Movie}, {@link Tv}).
     * @param <T>                 The type of content entities.
     * @return The corresponding response object (e.g., {@link MovieResponse}, {@link TvResponse}).
     */
    public static <T extends AbstractContent> Object createResponse(
            List<ContentEntityDb> contentEntityDbList, Class<T> contentClass) {
        List<T> convertedList = convertToContentList(contentEntityDbList, contentClass);

        if (Movie.class.isAssignableFrom(contentClass)) {
            List<Movie> convertedResult = (List<Movie>) convertedList;
            return new MovieResponse(convertedResult);
        } else if (Tv.class.isAssignableFrom(contentClass)) {
            List<Tv> convertedResult = (List<Tv>) convertedList;
            return new TvResponse(convertedResult);
        }
        return null;
    }

    /**
     * Converts the list of content entities retrieved from the database to the desired type.
     *
     * @param contentEntityDbList The list of content entities retrieved from the database.
     * @param contentClass        The class type of the content entities (e.g., {@link Movie}, {@link Tv}).
     * @param <T>                 The type of content entities.
     * @return The list of content entities of the desired type.
     */
    private static <T extends AbstractContent> List<T> convertToContentList(
            List<ContentEntityDb> contentEntityDbList, Class<T> contentClass) {
        List<T> result = new ArrayList<>();
        for (ContentEntityDb entityDb : contentEntityDbList) {
            result.add(ContentEntityDb.convertFromContentEntityDb(entityDb, contentClass));
        }
        return result;
    }

}
