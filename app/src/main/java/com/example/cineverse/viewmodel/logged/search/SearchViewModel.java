package com.example.cineverse.viewmodel.logged.search;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.cineverse.repository.logged.search.SearchRepository;
import java.util.List;

public class SearchViewModel extends AndroidViewModel {

    private final SearchRepository repository;
    private MutableLiveData<List<String>> searchHistoryLiveData;

    public SearchViewModel(@NonNull Application application) {
        super(application);
        repository = new SearchRepository(application.getApplicationContext());
    }

    public LiveData<List<String>> getSearchHistoryLiveData() {
        if (searchHistoryLiveData == null) {
            searchHistoryLiveData = new MutableLiveData<>();
        }
        return searchHistoryLiveData;
    }

    public void loadSearchHistory() {
        List<String> searchHistory = repository.getSearchHistory();
        Log.d("TAG", "loadvm"+searchHistory.toString());
        searchHistoryLiveData.postValue(searchHistory);
    }

    public void addToSearchHistory(String query) {
        if (!query.trim().isEmpty()){
            repository.addToSearchHistory(query);
            loadSearchHistory();
        }
    }

    public void removeFromSearchHistory(int position) {
        repository.removeFromSearchHistory(position);
        loadSearchHistory();
    }
}
