package com.example.cineverse.service.api;

import static com.example.cineverse.utils.constant.Api.APPEND_TO_RESPONSE_PARAMETER;
import static com.example.cineverse.utils.constant.Api.AUTHORIZATION_HEADER;
import static com.example.cineverse.utils.constant.Api.LANGUAGE_PARAMETER;
import static com.example.cineverse.utils.constant.Api.Movie.MOVIE_DETAILS_ENDPOINT;
import static com.example.cineverse.utils.constant.Api.Movie.MOVIE_ID;
import static com.example.cineverse.utils.constant.Api.Tv.TV_DETAILS_ENDPOINT;
import static com.example.cineverse.utils.constant.Api.Tv.TV_ID;

import com.example.cineverse.data.model.details.section.MovieDetails;
import com.example.cineverse.data.model.details.section.TvDetails;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DetailsApiService {

    @GET(MOVIE_DETAILS_ENDPOINT)
    Call<MovieDetails> getMovieDetails(
            @Path(MOVIE_ID) int movieId,
            @Query(APPEND_TO_RESPONSE_PARAMETER) String appendToResponse,
            @Query(LANGUAGE_PARAMETER) String language,
            @Header(AUTHORIZATION_HEADER) String bearerAccessTokenAuth
    );

    @GET(TV_DETAILS_ENDPOINT)
    Call<TvDetails> getTvDetails(
            @Path(TV_ID) int tvId,
            @Query(APPEND_TO_RESPONSE_PARAMETER) String appendToResponse,
            @Query(LANGUAGE_PARAMETER) String language,
            @Header(AUTHORIZATION_HEADER) String bearerAccessTokenAuth
    );

}
