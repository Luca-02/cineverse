package com.example.cineverse.data.source.details.section;

import static com.example.cineverse.utils.constant.Api.APPEND_TO_RESPONSE_DEFAULT_PARAMETER;

import android.content.Context;

import com.example.cineverse.data.model.content.AbstractContent;
import com.example.cineverse.data.model.details.section.MovieDetails;
import com.example.cineverse.data.source.details.AbstractContentDetailsRemoteDataSource;
import com.example.cineverse.data.source.details.ContentDetailsRemoteResponseCallback;

import retrofit2.Call;

public class MovieDetailsRemoteDataSource
        extends AbstractContentDetailsRemoteDataSource<MovieDetails> {

    public MovieDetailsRemoteDataSource(Context context) {
        super(context);
    }

    public Call<MovieDetails> createApiCall(int movieId) {
        return detailsApiService.getMovieDetails(
                movieId, APPEND_TO_RESPONSE_DEFAULT_PARAMETER, getLanguage(), getBearerAccessTokenAuth());
    }

}
