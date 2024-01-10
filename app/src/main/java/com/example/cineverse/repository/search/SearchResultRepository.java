package com.example.cineverse.repository.search;

import android.content.Context;

import com.example.cineverse.data.model.api.Failure;
import com.example.cineverse.data.model.search.SearchContentResponse;
import com.example.cineverse.data.source.search.SearchContentRemoteDataSource;
import com.example.cineverse.data.source.search.SearchContentRemoteResponseCallback;

public class SearchResultRepository
        implements SearchContentRemoteResponseCallback {

    private final SearchContentRemoteDataSource remoteDataSource;
    private final SearchContentRemoteResponseCallback remoteResponseCallback;

    public SearchResultRepository(Context context, SearchContentRemoteResponseCallback remoteResponseCallback) {
        this.remoteResponseCallback = remoteResponseCallback;
        remoteDataSource = new SearchContentRemoteDataSource(context, this);
    }

    public void fetch(String query, int page) {
        remoteDataSource.fetch(query, page);
    }

    @Override
    public void onRemoteResponse(SearchContentResponse response) {
        remoteResponseCallback.onRemoteResponse(response);
    }

    @Override
    public void onFailure(Failure failure) {
        remoteResponseCallback.onFailure(failure);
    }

}
