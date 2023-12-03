package com.example.cineverse.service.api;

import static com.example.cineverse.utils.constant.Api.LANGUAGE_PARAMETER;
import static com.example.cineverse.utils.constant.Api.PAGE_PARAMETER;
import static com.example.cineverse.utils.constant.Api.Tv.ON_THE_AIR_TV_ENDPOINT;
import static com.example.cineverse.utils.constant.Api.Tv.POPULAR_TV_ENDPOINT;
import static com.example.cineverse.utils.constant.Api.Tv.TOP_RATED_TV_ENDPOINT;

import com.example.cineverse.data.model.content.tv.PosterTvApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface TvApiService {

    @GET(POPULAR_TV_ENDPOINT)
    Call<PosterTvApiResponse> getPopularTv(
            @Query(LANGUAGE_PARAMETER) String language,
            @Query(PAGE_PARAMETER) int page,
            @Header("Authorization") String bearerAccessTokenAuth
    );

    @GET(TOP_RATED_TV_ENDPOINT)
    Call<PosterTvApiResponse> getTopRatedTv(
            @Query(LANGUAGE_PARAMETER) String language,
            @Query(PAGE_PARAMETER) int page,
            @Header("Authorization") String bearerAccessTokenAuth
    );

    @GET(ON_THE_AIR_TV_ENDPOINT)
    Call<PosterTvApiResponse> getWeekAiringTv(
            @Query(LANGUAGE_PARAMETER) String language,
            @Query(PAGE_PARAMETER) int page,
            @Header("Authorization") String bearerAccessTokenAuth
    );


}
