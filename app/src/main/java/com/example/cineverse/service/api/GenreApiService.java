package com.example.cineverse.service.api;

import static com.example.cineverse.utils.constant.Api.AUTHORIZATION_HEADER;
import static com.example.cineverse.utils.constant.Api.LANGUAGE_PARAMETER;
import static com.example.cineverse.utils.constant.Api.Movie.GENRE_MOVIE_ENDPOINT;
import static com.example.cineverse.utils.constant.Api.Tv.GENRE_TV_ENDPOINT;

import com.example.cineverse.data.model.genre.GenreResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

/**
 * The {@link GenreApiService} interface provides methods for accessing genre-related data from the
 * external API.
 */
public interface GenreApiService {

    @GET(GENRE_MOVIE_ENDPOINT)
    Call<GenreResponse> getMovieGenres(
            @Query(LANGUAGE_PARAMETER) String language,
            @Header(AUTHORIZATION_HEADER) String bearerAccessTokenAuth
    );

    @GET(GENRE_TV_ENDPOINT)
    Call<GenreResponse> getTvGenres(
            @Query(LANGUAGE_PARAMETER) String language,
            @Header(AUTHORIZATION_HEADER) String bearerAccessTokenAuth
    );

}
