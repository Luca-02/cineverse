package com.example.cineverse.repository.search;

import android.content.Context;

import com.example.cineverse.data.model.api.Failure;
import com.example.cineverse.data.model.search.KeywordResponse;
import com.example.cineverse.data.model.search.QueryHistory;
import com.example.cineverse.data.source.search.SearchKeywordRemoteDataSource;
import com.example.cineverse.data.source.search.SearchKeywordRemoteResponseCallback;
import com.example.cineverse.data.source.search.SearchHistoryLocalDataSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SearchRepository
        implements SearchKeywordRemoteResponseCallback {

    private final SearchHistoryLocalDataSource historyLocalDataSource;
    private final SearchKeywordRemoteDataSource searchKeywordRemoteDataSource;
    private final SearchKeywordRemoteResponseCallback searchKeywordRemoteResponseCallback;

    public SearchRepository(Context context, SearchKeywordRemoteResponseCallback remoteResponseCallback) {
        this.searchKeywordRemoteResponseCallback = remoteResponseCallback;
        historyLocalDataSource = new SearchHistoryLocalDataSource(context);
        searchKeywordRemoteDataSource = new SearchKeywordRemoteDataSource(context, this);
    }

    public List<QueryHistory> getSearchHistory() {
        Set<QueryHistory> searchHistorySet = historyLocalDataSource.getSearchHistory();
        return new ArrayList<>(searchHistorySet);
    }

    public QueryHistory addToSearchHistory(String query) {
        if (!query.trim().isEmpty()) {
            return historyLocalDataSource.addQuery(query);
        }
        return null;
    }

    public void removeFromSearchHistory(QueryHistory queryHistory) {
        historyLocalDataSource.removeQuery(queryHistory);
    }

    public void fetchKeyword(String query) {
        searchKeywordRemoteDataSource.fetch(query);
    }

    @Override
    public void onRemoteResponse(KeywordResponse response) {
        searchKeywordRemoteResponseCallback.onRemoteResponse(response);
    }

    @Override
    public void onFailure(Failure failure) {
        searchKeywordRemoteResponseCallback.onFailure(failure);
    }

}
