package com.example.cineverse.data.model.details;

import java.util.List;

public class ContentVideos {

    private List<Video> results;

    public ContentVideos(List<Video> results) {
        this.results = results;
    }

    public List<Video> getResults() {
        return results;
    }

    public void setResults(List<Video> results) {
        this.results = results;
    }

}
