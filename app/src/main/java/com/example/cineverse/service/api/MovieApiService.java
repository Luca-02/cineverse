package com.example.cineverse.service.api;

import static com.example.cineverse.utils.constant.Api.LANGUAGE_PARAMETER;
import static com.example.cineverse.utils.constant.Api.Movie.POPULAR_MOVIE_ENDPOINT;
import static com.example.cineverse.utils.constant.Api.Movie.TOP_RATED_MOVIE_ENDPOINT;
import static com.example.cineverse.utils.constant.Api.PAGE_PARAMETER;

import com.example.cineverse.data.model.content.PosterMovieApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface MovieApiService {

    @GET(POPULAR_MOVIE_ENDPOINT)
    Call<PosterMovieApiResponse> getPopularMovies(
            @Query(LANGUAGE_PARAMETER) String language,
            @Query(PAGE_PARAMETER) int page,
            @Header("Authorization") String bearerAccessTokenAuth
    );

    @GET(TOP_RATED_MOVIE_ENDPOINT)
    Call<PosterMovieApiResponse> getTopRatedMovies(
            @Query(LANGUAGE_PARAMETER) String language,
            @Query(PAGE_PARAMETER) int page,
            @Header("Authorization") String bearerAccessTokenAuth
    );

}
