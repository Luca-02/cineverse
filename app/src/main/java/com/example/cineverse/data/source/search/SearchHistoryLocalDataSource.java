package com.example.cineverse.data.source.search;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.cineverse.data.model.search.QueryHistory;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.Set;
import java.util.TreeSet;

public class SearchHistoryLocalDataSource {

    /**
     * The name of the shared preferences file.
     */
    public static final String PREF_NAME = "SearchHistoryPrefs";

    /**
     * The key used to store and retrieve query history information in the shared preferences.
     */
    public static final String QUERY_HISTORY_KEY = "searchHistory";

    /**
     * The type of the shared preferences elements in {@link #PREF_NAME}.
     */
    public static final Type QUERY_HISTORY_TYPE = new TypeToken<TreeSet<QueryHistory>>() {}.getType();

    private final Context context;

    public SearchHistoryLocalDataSource(Context context) {
        this.context = context;
    }

    public Set<QueryHistory> getSearchHistory() {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String json = prefs.getString(QUERY_HISTORY_KEY, null);

        if (json != null) {
            return new Gson().fromJson(json, QUERY_HISTORY_TYPE);
        } else {
            return new TreeSet<>();
        }
    }

    public void setSearchHistory(Set<QueryHistory> searchHistory) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Set<QueryHistory> sortedSet = new TreeSet<>(searchHistory);
        sortedSet.addAll(searchHistory);
        String json = new Gson().toJson(sortedSet);
        editor.putString(QUERY_HISTORY_KEY, json);
        editor.apply();
    }

    public QueryHistory addQuery(String query) {
        Set<QueryHistory> searchHistory = getSearchHistory();
        int position = 0;
        boolean alreadySaved = false;
        for (QueryHistory queryHistory : searchHistory) {
            if (queryHistory.getPosition() > position) {
                position = queryHistory.getPosition();
            }
            if (queryHistory.getQuery().equals(query)) {
                alreadySaved = true;
            }
        }
        if (!alreadySaved) {
            QueryHistory queryHistory = new QueryHistory(position + 1, query);
            if (searchHistory.add(queryHistory)) {
                setSearchHistory(searchHistory);
                return queryHistory;
            }
        }
        return null;
    }

    public void removeQuery(QueryHistory queryHistory) {
        Set<QueryHistory> searchHistory = getSearchHistory();
        searchHistory.remove(queryHistory);
        setSearchHistory(searchHistory);
    }

    public static void clearHistory(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(QUERY_HISTORY_KEY);
        editor.apply();
    }

}
