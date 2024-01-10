package com.example.cineverse.viewmodel.verified_account.section.search;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.cineverse.data.model.api.Failure;
import com.example.cineverse.data.model.search.Keyword;
import com.example.cineverse.data.model.search.KeywordResponse;
import com.example.cineverse.data.model.search.QueryHistory;
import com.example.cineverse.data.model.search.SearchContentResponse;
import com.example.cineverse.data.source.search.SearchContentRemoteResponseCallback;
import com.example.cineverse.data.source.search.SearchKeywordRemoteResponseCallback;
import com.example.cineverse.repository.search.SearchRepository;
import com.example.cineverse.viewmodel.verified_account.AbstractFailureResponseViewModel;

import java.util.List;

public class SearchViewModel
        extends AbstractFailureResponseViewModel
        implements SearchKeywordRemoteResponseCallback {

    private final SearchRepository searchRepository;
    private MutableLiveData<List<QueryHistory>> searchHistoryLiveData;
    private MutableLiveData<List<Keyword>> keywordLiveData;

    public SearchViewModel(@NonNull Application application) {
        super(application);
        searchRepository = new SearchRepository(application.getApplicationContext(), this);
    }

    public MutableLiveData<List<QueryHistory>> getSearchHistoryLiveData() {
        if (searchHistoryLiveData == null) {
            searchHistoryLiveData = new MutableLiveData<>();
        }
        return searchHistoryLiveData;
    }

    public MutableLiveData<List<Keyword>> getKeywordLiveData() {
        if (keywordLiveData == null) {
            keywordLiveData = new MutableLiveData<>();
        }
        return keywordLiveData;
    }


    public void loadSearchHistory() {
        List<QueryHistory> searchHistory = searchRepository.getSearchHistory();
        searchHistoryLiveData.postValue(searchHistory);
    }

    public QueryHistory addToSearchHistory(String query) {
        return searchRepository.addToSearchHistory(query);
    }

    public void removeFromSearchHistory(QueryHistory queryHistory) {
        searchRepository.removeFromSearchHistory(queryHistory);
    }

    public void fetchKeyword(String query) {
        searchRepository.fetchKeyword(query);
    }

    @Override
    public void onRemoteResponse(KeywordResponse response) {
        if (response != null) {
            handleResponse(response.getResults(), getKeywordLiveData());
        }
    }

    @Override
    public void onFailure(Failure failure) {
        if (failure != null) {
            handleFailure(failure);
        }
    }

}
