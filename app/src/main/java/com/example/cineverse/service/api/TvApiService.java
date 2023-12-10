package com.example.cineverse.service.api;

import static com.example.cineverse.utils.constant.Api.AUTHORIZATION_HEADER;
import static com.example.cineverse.utils.constant.Api.LANGUAGE_PARAMETER;
import static com.example.cineverse.utils.constant.Api.PAGE_PARAMETER;
import static com.example.cineverse.utils.constant.Api.REGION_PARAMETER;
import static com.example.cineverse.utils.constant.Api.Tv.AIRING_TODAY_TV_ENDPOINT;
import static com.example.cineverse.utils.constant.Api.Tv.ON_THE_AIR_TV_ENDPOINT;
import static com.example.cineverse.utils.constant.Api.Tv.POPULAR_TV_ENDPOINT;
import static com.example.cineverse.utils.constant.Api.Tv.TOP_RATED_TV_ENDPOINT;

import com.example.cineverse.data.model.content.section.ContentTvApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface TvApiService {

    @GET(AIRING_TODAY_TV_ENDPOINT)
    Call<ContentTvApiResponse> getAiringTodayTv(
            @Query(LANGUAGE_PARAMETER) String language,
            @Query(PAGE_PARAMETER) int page,
            @Query(REGION_PARAMETER) String region,
            @Header(AUTHORIZATION_HEADER) String bearerAccessTokenAuth
    );

    @GET(ON_THE_AIR_TV_ENDPOINT)
    Call<ContentTvApiResponse> getWeekAiringTv(
            @Query(LANGUAGE_PARAMETER) String language,
            @Query(PAGE_PARAMETER) int page,
            @Query(REGION_PARAMETER) String region,
            @Header(AUTHORIZATION_HEADER) String bearerAccessTokenAuth
    );

    @GET(POPULAR_TV_ENDPOINT)
    Call<ContentTvApiResponse> getPopularTv(
            @Query(LANGUAGE_PARAMETER) String language,
            @Query(PAGE_PARAMETER) int page,
            @Query(REGION_PARAMETER) String region,
            @Header(AUTHORIZATION_HEADER) String bearerAccessTokenAuth
    );

    @GET(TOP_RATED_TV_ENDPOINT)
    Call<ContentTvApiResponse> getTopRatedTv(
            @Query(LANGUAGE_PARAMETER) String language,
            @Query(PAGE_PARAMETER) int page,
            @Query(REGION_PARAMETER) String region,
            @Header(AUTHORIZATION_HEADER) String bearerAccessTokenAuth
    );

}
