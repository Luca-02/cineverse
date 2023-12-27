package com.example.cineverse.repository.search;

import android.content.Context;
import android.util.Log;

import com.example.cineverse.data.model.api.Failure;
import com.example.cineverse.data.model.search.KeywordResponse;
import com.example.cineverse.data.model.search.QueryHistory;
import com.example.cineverse.data.source.search.KeywordRemoteDataSource;
import com.example.cineverse.data.source.search.KeywordRemoteResponseCallback;
import com.example.cineverse.data.source.search.SearchHistoryLocalDataSource;
import com.example.cineverse.utils.constant.GlobalConstant;
import com.google.firebase.database.core.utilities.Tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class SearchRepository
        implements KeywordRemoteResponseCallback {

    private final SearchHistoryLocalDataSource historyLocalDataSource;
    private final KeywordRemoteDataSource keywordRemoteDataSource;
    private final KeywordRemoteResponseCallback keywordRemoteResponseCallback;

    public SearchRepository(Context context, KeywordRemoteResponseCallback remoteResponseCallback) {
        this.keywordRemoteResponseCallback = remoteResponseCallback;
        historyLocalDataSource = new SearchHistoryLocalDataSource(context);
        keywordRemoteDataSource = new KeywordRemoteDataSource(context, this);
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
        keywordRemoteDataSource.fetch(query);
    }

    @Override
    public void onRemoteResponse(KeywordResponse response) {
        keywordRemoteResponseCallback.onRemoteResponse(response);
    }

    @Override
    public void onFailure(Failure failure) {
        keywordRemoteResponseCallback.onFailure(failure);
    }

}
