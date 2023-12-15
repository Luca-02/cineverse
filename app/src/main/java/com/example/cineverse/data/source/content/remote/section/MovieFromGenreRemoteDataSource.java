package com.example.cineverse.data.source.content.remote.section;

import android.content.Context;

import com.example.cineverse.data.model.content.section.MovieResponse;
import com.example.cineverse.data.source.content.remote.AbstractSectionMovieRemoteDataSource;
import com.example.cineverse.data.source.content.remote.ISectionContentRemoteDataSource;

import retrofit2.Call;

public class MovieFromGenreRemoteDataSource
        extends AbstractSectionMovieRemoteDataSource
        implements ISectionContentRemoteDataSource {

    private final int genreId;

    public MovieFromGenreRemoteDataSource(Context context, int genreId) {
        super(context);
        this.genreId = genreId;
    }

    @Override
    public String getSection() {
        return null;
    }

    @Override
    public void fetch(int page) {
        Call<MovieResponse> call =
                movieApiService.getMovieFromGenres(language, page, genreId, bearerAccessTokenAuth);
        handlePosterApiCal(call);
    }

}
