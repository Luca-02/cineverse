package com.example.cineverse.service.api;

import static com.example.cineverse.utils.constant.Api.AUTHORIZATION_HEADER;
import static com.example.cineverse.utils.constant.Api.LANGUAGE_PARAMETER;
import static com.example.cineverse.utils.constant.Api.PAGE_PARAMETER;
import static com.example.cineverse.utils.constant.Api.QUERY_PARAMETER;
import static com.example.cineverse.utils.constant.Api.Search.SEARCH_KEYWORD_ENDPOINT;
import static com.example.cineverse.utils.constant.Api.Search.SEARCH_MULTI_ENDPOINT;

import com.example.cineverse.data.model.search.KeywordResponse;
import com.example.cineverse.data.model.search.SearchContentResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

/**
 * The {@link SearchApiService} interface provides methods for searching data from the external API.
 */
public interface SearchApiService {

    @GET(SEARCH_KEYWORD_ENDPOINT)
    Call<KeywordResponse> searchKeyword(
            @Query(QUERY_PARAMETER) String query,
            @Query(PAGE_PARAMETER) int page,
            @Header(AUTHORIZATION_HEADER) String bearerAccessTokenAuth
    );

    @GET(SEARCH_MULTI_ENDPOINT)
    Call<SearchContentResponse> searchMulti(
            @Query(LANGUAGE_PARAMETER) String language,
            @Query(QUERY_PARAMETER) String query,
            @Query(PAGE_PARAMETER) int page,
            @Header(AUTHORIZATION_HEADER) String bearerAccessTokenAuth
    );

}
