package com.example.cineverse.data.model.details.section;

import com.example.cineverse.data.model.api.ApiResponse;
import com.example.cineverse.data.model.details.ContentVideos;
import com.example.cineverse.data.model.details.Credits;
import com.example.cineverse.data.model.genre.Genre;

import java.util.List;

public interface IContentDetails extends ApiResponse {

    int getId();

    void setId(int id);

    String getOverview();

    void setOverview(String overview);

    String getPosterPath();

    void setPosterPath(String posterPath);

    String getBackdropPath();

    void setBackdropPath(String backdropPath);

    String getOriginalLanguage();

    void setOriginalLanguage(String originalLanguage);

    Long getWatchlistTimestamp();

    void setWatchlistTimestamp(Long timestamp);

    String getName();

    void setName(String name);

    String getReleaseDate();

    void setReleaseDate(String releaseDate);

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

}
