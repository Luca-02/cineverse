package com.example.cineverse.data.source.content.remote.section;

import android.content.Context;

import com.example.cineverse.data.model.content.section.MovieResponse;
import com.example.cineverse.data.source.content.remote.AbstractSectionMovieRemoteDataSource;

import retrofit2.Call;

public class UpcomingMovieRemoteDataSource
        extends AbstractSectionMovieRemoteDataSource {

    public UpcomingMovieRemoteDataSource(Context context) {
        super(context);
    }

    @Override
    public void fetch(int page) {
        Call<MovieResponse> call =
                movieApiService.getUpcomingMovies(language, page, region, bearerAccessTokenAuth);
        handlePosterApiCal(call);
    }

}
