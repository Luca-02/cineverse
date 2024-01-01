package com.example.cineverse.data.source.search;

import android.content.Context;

import com.example.cineverse.data.model.search.SearchContentResponse;
import com.example.cineverse.data.source.api.TMDBRemoteDataSource;
import com.example.cineverse.service.api.SearchApiServices;
import com.example.cineverse.utils.ServiceLocator;

import retrofit2.Call;

public class SearchContentRemoteDataSource
        extends TMDBRemoteDataSource {

    private final SearchApiServices searchApiServices;
    private final SearchContentRemoteResponseCallback remoteResponseCallback;

    public SearchContentRemoteDataSource(Context context, SearchContentRemoteResponseCallback remoteResponseCallback) {
        super(context);
        this.remoteResponseCallback = remoteResponseCallback;
        searchApiServices = ServiceLocator.getInstance().getSearchApiServices();
    }

    public void fetch(String query, int page) {
        Call<SearchContentResponse> call = searchApiServices
                .searchMulti(getLanguage(), query, page, getBearerAccessTokenAuth());
        handleApiCall(call, remoteResponseCallback);
    }

}
