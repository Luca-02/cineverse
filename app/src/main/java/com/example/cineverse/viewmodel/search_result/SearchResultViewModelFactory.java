package com.example.cineverse.viewmodel.search_result;

import android.app.Application;

import com.example.cineverse.viewmodel.GlobalViewModelFactory;

public class SearchResultViewModelFactory
        extends GlobalViewModelFactory<SearchResultViewModel> {

    /**
     * Constructs a {@link SearchResultViewModelFactory} with the specified ViewModel instance.
     *
     * @param application The {@link Application} of the calling component.
     * @param query The query of research.
     */
    public SearchResultViewModelFactory(Application application, String query) {
        super(new SearchResultViewModel(application, query));
    }

}
