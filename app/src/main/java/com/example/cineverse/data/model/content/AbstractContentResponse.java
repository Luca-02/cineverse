package com.example.cineverse.data.model.content;

import static com.example.cineverse.utils.constant.Api.STARTING_PAGE;

import com.example.cineverse.data.model.ApiResponse;
import com.example.cineverse.data.model.content.section.ContentEntityDb;
import com.example.cineverse.data.model.content.section.MovieEntity;
import com.example.cineverse.data.model.content.section.MovieResponse;
import com.example.cineverse.data.model.content.section.TvEntity;
import com.example.cineverse.data.model.content.section.TvResponse;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

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

    public static <T extends AbstractContent> Object createResponse(
            List<ContentEntityDb> contentEntityDbList, Class<T> contentClass) {
        List<T> convertedList = convertToContentList(contentEntityDbList, contentClass);

        if (MovieEntity.class.isAssignableFrom(contentClass)) {
            List<MovieEntity> convertedResult = (List<MovieEntity>) convertedList;
            return new MovieResponse(STARTING_PAGE, convertedResult, STARTING_PAGE, convertedList.size());
        } else if (TvEntity.class.isAssignableFrom(contentClass)) {
            List<TvEntity> convertedResult = (List<TvEntity>) convertedList;
            return new TvResponse(STARTING_PAGE, convertedResult, STARTING_PAGE, convertedList.size());
        }
        // Handle other content types if needed
        return null;
    }

    private static <T extends AbstractContent> List<T> convertToContentList(
            List<ContentEntityDb> contentEntityDbList, Class<T> contentClass) {
        List<T> result = new ArrayList<>();
        for (ContentEntityDb entityDb : contentEntityDbList) {
            result.add(ContentEntityDb.convertFromDbContent(entityDb, contentClass));
        }
        return result;
    }

}
