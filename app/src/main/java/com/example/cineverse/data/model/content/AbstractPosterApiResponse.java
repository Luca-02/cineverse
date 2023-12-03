package com.example.cineverse.data.model.content;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public abstract class AbstractPosterApiResponse<T extends AbstractPoster> {

    private int page;
    private List<T> results;
    @SerializedName("total_pages")
    private int totalPages;
    @SerializedName("total_results")
    private int totalResults;

    public AbstractPosterApiResponse(int page, int totalPages, int totalResults, List<T> results) {
        this.page = page;
        this.totalPages = totalPages;
        this.totalResults = totalResults;
        this.results = results;
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

    public boolean isResponseOk() {
        return results != null;
    }

}
