package com.example.cineverse.repository.logged.search;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SearchRepository {

    private static final String PREF_NAME = "SearchHistoryPrefs";
    private Context context;

    public SearchRepository(Context context) {
        this.context = context;
    }

    public List<String> getSearchHistory() {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        Set<String> stringSet = prefs.getStringSet("searchHistory", new HashSet<>());
        Log.d("TAG", "repo "+stringSet.toString());
        return new ArrayList<>(stringSet);
    }

    public void saveSearchHistory(List<String> searchHistory) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Set<String> stringSet = new HashSet<>(searchHistory);
        editor.putStringSet("searchHistory", stringSet);
        editor.apply();
    }

    public void addToSearchHistory(String query) {
        List<String> searchHistory = getSearchHistory();
        searchHistory.add(query);
        saveSearchHistory(searchHistory);
    }

    public void removeFromSearchHistory(int position) {
        List<String> searchHistory = getSearchHistory();
        searchHistory.remove(position);
        saveSearchHistory(searchHistory);
    }
}
