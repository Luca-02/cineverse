package com.example.cineverse.data.model.details.section;

import com.example.cineverse.data.model.api.ApiResponse;
import com.example.cineverse.data.model.details.ContentVideos;
import com.example.cineverse.data.model.details.Credits;
import com.example.cineverse.data.model.genre.Genre;

import java.util.List;

public interface ContentDetailsApiResponse extends ApiResponse {

    List<Genre> getGenres();

    void setGenres(List<Genre> genres);

    String getStatus();

    void setStatus(String status);

    String getTagline();

    void setTagline(String tagline);

    Credits getCredits();

    void setCredits(Credits credits);

    ContentVideos getVideos();

    void setVideos(ContentVideos videos);

    Long getTimestamp();

    void setTimestamp(Long timestamp);

}
