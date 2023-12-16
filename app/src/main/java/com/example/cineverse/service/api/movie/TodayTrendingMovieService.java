package com.example.cineverse.service.api.movie;

import static com.example.cineverse.utils.constant.Api.AUTHORIZATION_HEADER;
import static com.example.cineverse.utils.constant.Api.LANGUAGE_PARAMETER;
import static com.example.cineverse.utils.constant.Api.Movie.TODAY_TRENDING_MOVIE_ENDPOINT;
import static com.example.cineverse.utils.constant.Api.PAGE_PARAMETER;

import com.example.cineverse.data.model.content.section.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface TodayTrendingMovieService {

    @GET(TODAY_TRENDING_MOVIE_ENDPOINT)
    Call<MovieResponse> getContent(
            @Query(LANGUAGE_PARAMETER) String language,
            @Query(PAGE_PARAMETER) int page,
            @Header(AUTHORIZATION_HEADER) String bearerAccessTokenAuth
    );

}
