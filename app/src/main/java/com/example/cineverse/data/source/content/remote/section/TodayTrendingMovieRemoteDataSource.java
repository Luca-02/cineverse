package com.example.cineverse.data.source.content.remote.section;

import android.content.Context;

import com.example.cineverse.data.model.content.section.MovieResponse;
import com.example.cineverse.data.source.content.remote.AbstractSectionMovieRemoteDataSource;
import com.example.cineverse.data.source.content.remote.ISectionContentRemoteDataSource;

import retrofit2.Call;

public class TodayTrendingMovieRemoteDataSource
        extends AbstractSectionMovieRemoteDataSource
        implements ISectionContentRemoteDataSource {

    public TodayTrendingMovieRemoteDataSource(Context context) {
        super(context);
    }

    @Override
    public void fetch(int page) {
        Call<MovieResponse> call =
                movieApiService.getTodayTrendingMovies(language, page, bearerAccessTokenAuth);
        handlePosterApiCal(call);
    }

}
