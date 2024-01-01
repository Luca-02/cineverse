package com.example.cineverse.data.model.search;

import com.example.cineverse.data.model.api.ApiResponse;
import com.example.cineverse.data.model.content.AbstractContent;
import com.example.cineverse.data.model.content.section.Movie;
import com.example.cineverse.data.model.content.section.Tv;
import com.example.cineverse.utils.constant.Api;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class SearchContentResponse
        implements ApiResponse {

    private int page;
    private List<Object> results;
    @SerializedName("total_pages")
    private int totalPages;
    @SerializedName("total_results")
    private int totalResults;

    public SearchContentResponse(int page, List<Object> results, int totalPages, int totalResults) {
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

    public List<Object> getResults() {
        return results;
    }

    public void setResults(List<Object> results) {
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

    public List<AbstractContent> getParsedResults() {
        List<AbstractContent> contentList = new ArrayList<>();
        if (results != null) {
            for (Object result : results) {
                AbstractContent content = mapToContent(result);
                if (content != null) {
                    contentList.add(content);
                }
            }
        }
        return contentList;
    }

    private AbstractContent mapToContent(Object result) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String json = objectMapper.writeValueAsString(result);
            if (json.contains("\"media_type\":\"" + Api.Movie.MOVIE + "\"")) {
                return new Gson().fromJson(json, Movie.class);
            } else if (json.contains("\"media_type\":\"" + Api.Tv.TV + "\"")) {
                return new Gson().fromJson(json, Tv.class);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

}
