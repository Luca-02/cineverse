package com.example.cineverse.data.source.content.poster.section;

import android.content.Context;

import com.example.cineverse.data.model.content.section.ContentMovie;
import com.example.cineverse.data.model.content.section.ContentMovieApiResponse;
import com.example.cineverse.data.source.content.poster.AbstractPosterMovieRemoteDataSource;
import com.example.cineverse.data.source.content.poster.IPosterContentRemoteDataSource;
import com.example.cineverse.data.source.content.poster.PosterContentResponseCallback;

import retrofit2.Call;

public class PopularMovieRemoteDataSource
        extends AbstractPosterMovieRemoteDataSource
        implements IPosterContentRemoteDataSource {

    public PopularMovieRemoteDataSource(Context context,
                                        PosterContentResponseCallback<ContentMovie> callback) {
        super(context, callback);
    }

    @Override
    public void fetch(int page) {
        Call<ContentMovieApiResponse> call =
                movieApiService.getPopularMovies(language, page, region, bearerAccessTokenAuth);
        handlePosterApiCal(call);
    }

}
