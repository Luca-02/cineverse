package com.example.cineverse.service.api;

import static com.example.cineverse.utils.constant.Api.AUTHORIZATION_HEADER;
import static com.example.cineverse.utils.constant.Api.LANGUAGE_PARAMETER;
import static com.example.cineverse.utils.constant.Api.Movie.DISCOVER_MOVIE_ENDPOINT;
import static com.example.cineverse.utils.constant.Api.Movie.NOW_PLAYING_MOVIE_ENDPOINT;
import static com.example.cineverse.utils.constant.Api.Movie.POPULAR_MOVIE_ENDPOINT;
import static com.example.cineverse.utils.constant.Api.Movie.TODAY_TRENDING_MOVIE_ENDPOINT;
import static com.example.cineverse.utils.constant.Api.Movie.TOP_RATED_MOVIE_ENDPOINT;
import static com.example.cineverse.utils.constant.Api.Movie.UPCOMING_MOVIE_ENDPOINT;
import static com.example.cineverse.utils.constant.Api.PAGE_PARAMETER;
import static com.example.cineverse.utils.constant.Api.REGION_PARAMETER;
import static com.example.cineverse.utils.constant.Api.WITH_GENRES_PARAMETER;

import com.example.cineverse.data.model.content.section.ContentMovieApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface MovieApiService {

    @GET(NOW_PLAYING_MOVIE_ENDPOINT)
    Call<ContentMovieApiResponse> getNowPlayingMovies(
            @Query(LANGUAGE_PARAMETER) String language,
            @Query(PAGE_PARAMETER) int page,
            @Query(REGION_PARAMETER) String region,
            @Header(AUTHORIZATION_HEADER) String bearerAccessTokenAuth
    );

    @GET(POPULAR_MOVIE_ENDPOINT)
    Call<ContentMovieApiResponse> getPopularMovies(
            @Query(LANGUAGE_PARAMETER) String language,
            @Query(PAGE_PARAMETER) int page,
            @Query(REGION_PARAMETER) String region,
            @Header(AUTHORIZATION_HEADER) String bearerAccessTokenAuth
    );

    @GET(TOP_RATED_MOVIE_ENDPOINT)
    Call<ContentMovieApiResponse> getTopRatedMovies(
            @Query(LANGUAGE_PARAMETER) String language,
            @Query(PAGE_PARAMETER) int page,
            @Query(REGION_PARAMETER) String region,
            @Header(AUTHORIZATION_HEADER) String bearerAccessTokenAuth
    );

    @GET(UPCOMING_MOVIE_ENDPOINT)
    Call<ContentMovieApiResponse> getUpcomingMovies(
            @Query(LANGUAGE_PARAMETER) String language,
            @Query(PAGE_PARAMETER) int page,
            @Query(REGION_PARAMETER) String region,
            @Header(AUTHORIZATION_HEADER) String bearerAccessTokenAuth
    );

    @GET(TODAY_TRENDING_MOVIE_ENDPOINT)
    Call<ContentMovieApiResponse> getTodayTrendingMovies(
            @Query(LANGUAGE_PARAMETER) String language,
            @Query(PAGE_PARAMETER) int page,
            @Header(AUTHORIZATION_HEADER) String bearerAccessTokenAuth
    );

    @GET(DISCOVER_MOVIE_ENDPOINT)
    Call<ContentMovieApiResponse> getMovieFromGenres(
            @Query(LANGUAGE_PARAMETER) String language,
            @Query(PAGE_PARAMETER) int page,
            @Query(WITH_GENRES_PARAMETER) int withGenres,
            @Header(AUTHORIZATION_HEADER) String bearerAccessTokenAuth
    );

}
