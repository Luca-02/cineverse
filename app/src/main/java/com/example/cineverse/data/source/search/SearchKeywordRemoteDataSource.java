package com.example.cineverse.data.source.search;

import static com.example.cineverse.utils.constant.Api.STARTING_PAGE;

import android.content.Context;

import com.example.cineverse.data.model.search.KeywordResponse;
import com.example.cineverse.data.source.api.TMDBRemoteDataSource;
import com.example.cineverse.service.api.SearchApiServices;
import com.example.cineverse.utils.ServiceLocator;

import retrofit2.Call;

/**
 * The {@link SearchKeywordRemoteDataSource} class is a class for searching keywords using The Movie Database (TMDB) API.
 * This class handles the communication with the TMDB search endpoint to retrieve keyword search results.
 * It extends {@link TMDBRemoteDataSource}.
 */
public class SearchKeywordRemoteDataSource
        extends TMDBRemoteDataSource {

    private final SearchApiServices searchApiServices;
    private final SearchKeywordRemoteResponseCallback remoteResponseCallback;

    public SearchKeywordRemoteDataSource(Context context, SearchKeywordRemoteResponseCallback remoteResponseCallback) {
        super(context);
        this.remoteResponseCallback = remoteResponseCallback;
        searchApiServices = ServiceLocator.getInstance().getSearchApiServices();
    }

    /**
     * Fetches keyword search results from the TMDB API.
     *
     * @param query The search query string.
     * @see SearchApiServices#searchKeyword(String, int, String)
     */
    public void fetch(String query) {
        Call<KeywordResponse> call = searchApiServices
                .searchKeyword(query, STARTING_PAGE, getBearerAccessTokenAuth());
        handleApiCall(call, remoteResponseCallback);
    }

}
