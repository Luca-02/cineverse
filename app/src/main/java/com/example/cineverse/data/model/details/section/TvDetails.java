package com.example.cineverse.data.model.details.section;

import com.example.cineverse.data.model.content.section.Tv;
import com.example.cineverse.data.model.details.ContentVideos;
import com.example.cineverse.data.model.details.Credits;
import com.example.cineverse.data.model.details.Season;
import com.example.cineverse.data.model.genre.Genre;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TvDetails extends Tv implements IContentDetails {

    private List<Genre> genres;
    private String status;
    private String tagline;
    private List<Season> seasons;
    @SerializedName("aggregate_credits")
    private Credits credits;
    private ContentVideos videos;

    public TvDetails(int id, String name, String overview, String releaseDate,
                     String posterPath, String backdropPath, String originalLanguage,
                     List<Genre> genres, String status, String tagline,
                     List<Season> seasons, Credits credits, ContentVideos videos) {
        super(id, name, overview, releaseDate, posterPath, backdropPath, originalLanguage);
        this.genres = genres;
        this.status = status;
        this.tagline = tagline;
        this.seasons = seasons;
        this.credits = credits;
        this.videos = videos;
    }

    @Override
    public List<Genre> getGenres() {
        return genres;
    }

    @Override
    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    @Override
    public String getStatus() {
        return status;
    }

    @Override
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String getTagline() {
        return tagline;
    }

    @Override
    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public List<Season> getSeasons() {
        return seasons;
    }

    public void setSeasons(List<Season> seasons) {
        this.seasons = seasons;
    }

    @Override
    public Credits getCredits() {
        return credits;
    }

    @Override
    public void setCredits(Credits credits) {
        this.credits = credits;
    }

    @Override
    public ContentVideos getVideos() {
        return videos;
    }

    @Override
    public void setVideos(ContentVideos videos) {
        this.videos = videos;
    }

}
