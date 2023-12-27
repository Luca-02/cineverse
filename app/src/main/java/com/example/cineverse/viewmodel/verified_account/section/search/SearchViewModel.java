package com.example.cineverse.viewmodel.verified_account.section.search;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.cineverse.data.model.api.Failure;
import com.example.cineverse.data.model.search.Keyword;
import com.example.cineverse.data.model.search.KeywordResponse;
import com.example.cineverse.data.model.search.QueryHistory;
import com.example.cineverse.data.source.search.KeywordRemoteResponseCallback;
import com.example.cineverse.repository.search.SearchRepository;
import com.example.cineverse.utils.constant.GlobalConstant;
import com.example.cineverse.viewmodel.verified_account.AbstractFailureResponseViewModel;

import java.util.List;

public class SearchViewModel
        extends AbstractFailureResponseViewModel
        implements KeywordRemoteResponseCallback {

    private final SearchRepository repository;
    private MutableLiveData<List<QueryHistory>> searchHistoryLiveData;
    private MutableLiveData<List<Keyword>> keywordLiveData;

    public SearchViewModel(@NonNull Application application) {
        super(application);
        repository = new SearchRepository(application.getApplicationContext(), this);
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
        List<QueryHistory> searchHistory = repository.getSearchHistory();
        searchHistoryLiveData.postValue(searchHistory);
    }

    public QueryHistory addToSearchHistory(String query) {
        return repository.addToSearchHistory(query);
    }

    public void removeFromSearchHistory(QueryHistory queryHistory) {
        repository.removeFromSearchHistory(queryHistory);
    }

    public void fetchKeyword(String query) {
        repository.fetchKeyword(query);
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
