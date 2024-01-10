package com.example.cineverse.data.source.details.section;

import static com.example.cineverse.utils.constant.Api.CREDITS;
import static com.example.cineverse.utils.constant.Api.SIMILAR;
import static com.example.cineverse.utils.constant.Api.VIDEOS;

import android.content.Context;

import com.example.cineverse.data.model.details.section.MovieDetails;
import com.example.cineverse.data.source.details.AbstractContentDetailsRemoteDataSource;

import retrofit2.Call;

public class MovieDetailsRemoteDataSource
        extends AbstractContentDetailsRemoteDataSource<MovieDetails> {

    public MovieDetailsRemoteDataSource(Context context) {
        super(context);
    }

    public Call<MovieDetails> createApiCall(int movieId) {
        String appendToResponse = CREDITS + "," + VIDEOS + "," + SIMILAR;
        return detailsApiService.getMovieDetails(
                movieId, appendToResponse, getLanguage(), getBearerAccessTokenAuth());
    }

}
