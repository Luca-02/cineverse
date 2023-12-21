package com.example.cineverse.data.model.search;

import com.example.cineverse.data.model.api.ApiResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class KeywordResponse
        implements ApiResponse {

    private int page;
    private List<Keyword> results;
    @SerializedName("total_pages")
    private int totalPages;
    @SerializedName("total_results")
    private int totalResults;

    public KeywordResponse(int page, List<Keyword> results, int totalPages, int totalResults) {
        this.page = page;
        this.results = results;
        this.totalPages = totalPages;
        this.totalResults = totalResults;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<Keyword> getResults() {
        return results;
    }

    public void setResults(List<Keyword> results) {
        this.results = results;
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

}
