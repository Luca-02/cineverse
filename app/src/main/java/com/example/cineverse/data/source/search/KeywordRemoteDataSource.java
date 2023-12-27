package com.example.cineverse.data.source.search;

import static com.example.cineverse.utils.constant.Api.STARTING_PAGE;

import android.content.Context;

import com.example.cineverse.data.model.search.KeywordResponse;
import com.example.cineverse.data.source.api.TMDBRemoteDataSource;
import com.example.cineverse.service.api.SearchApiServices;
import com.example.cineverse.utils.ServiceLocator;

import retrofit2.Call;

public class KeywordRemoteDataSource
        extends TMDBRemoteDataSource {

    private final SearchApiServices searchApiServices;
    private final KeywordRemoteResponseCallback remoteResponseCallback;

    public KeywordRemoteDataSource(Context context, KeywordRemoteResponseCallback remoteResponseCallback) {
        super(context);
        this.remoteResponseCallback = remoteResponseCallback;
        searchApiServices = ServiceLocator.getInstance().getSearchApiServices();
    }

    public void fetch(String query) {
        Call<KeywordResponse> call = searchApiServices
                .searchKeyword(query, STARTING_PAGE, getBearerAccessTokenAuth());
        handleKeywordApiCall(call);
    }

    protected void handleKeywordApiCall(Call<KeywordResponse> call) {
        if (remoteResponseCallback != null) {
            handleApiCall(call, remoteResponseCallback);
        }
    }

}
