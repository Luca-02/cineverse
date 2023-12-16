package com.example.cineverse.service.api.movie;

import static com.example.cineverse.utils.constant.Api.AUTHORIZATION_HEADER;
import static com.example.cineverse.utils.constant.Api.LANGUAGE_PARAMETER;
import static com.example.cineverse.utils.constant.Api.Movie.UPCOMING_MOVIE_ENDPOINT;
import static com.example.cineverse.utils.constant.Api.PAGE_PARAMETER;
import static com.example.cineverse.utils.constant.Api.REGION_PARAMETER;

import com.example.cineverse.data.model.content.section.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface UpcomingMovieService {

    @GET(UPCOMING_MOVIE_ENDPOINT)
    Call<MovieResponse> getContent(
            @Query(LANGUAGE_PARAMETER) String language,
            @Query(PAGE_PARAMETER) int page,
            @Query(REGION_PARAMETER) String region,
            @Header(AUTHORIZATION_HEADER) String bearerAccessTokenAuth
    );

}
