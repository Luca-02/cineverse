package com.example.cineverse.service.api;

import static com.example.cineverse.utils.constant.Api.AUTHORIZATION_HEADER;
import static com.example.cineverse.utils.constant.Api.LANGUAGE_PARAMETER;
import static com.example.cineverse.utils.constant.Api.Movie.GENRE_MOVIE_ENDPOINT;
import static com.example.cineverse.utils.constant.Api.Tv.GENRE_TV_ENDPOINT;

import com.example.cineverse.data.model.genre.GenreApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface GenreApiService {

    @GET(GENRE_MOVIE_ENDPOINT)
    Call<GenreApiResponse> getMovieGenres(
            @Query(LANGUAGE_PARAMETER) String language,
            @Header(AUTHORIZATION_HEADER) String bearerAccessTokenAuth
    );

    @GET(GENRE_TV_ENDPOINT)
    Call<GenreApiResponse> getTvGenres(
            @Query(LANGUAGE_PARAMETER) String language,
            @Header(AUTHORIZATION_HEADER) String bearerAccessTokenAuth
    );

}
