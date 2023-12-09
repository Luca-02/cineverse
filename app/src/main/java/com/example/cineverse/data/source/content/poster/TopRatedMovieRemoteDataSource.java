package com.example.cineverse.data.source.content.poster;

import android.content.Context;

import com.example.cineverse.data.model.content.poster.PosterMovie;
import com.example.cineverse.data.model.content.poster.PosterMovieApiResponse;

import retrofit2.Call;

public class TopRatedMovieRemoteDataSource
        extends AbstractPosterMovieRemoteDataSource
        implements IPosterContentRemoteDataSource {

    public TopRatedMovieRemoteDataSource(Context context,
                                         PosterContentResponseCallback<PosterMovie> callback) {
        super(context, callback);
    }

    @Override
    public void fetch(int page) {
        Call<PosterMovieApiResponse> call =
                movieApiService.getTopRatedMovies(language, page, region, bearerAccessTokenAuth);
        handlePosterApiCal(call);
    }

}
