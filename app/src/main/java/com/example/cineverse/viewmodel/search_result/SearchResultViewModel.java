package com.example.cineverse.viewmodel.search_result;

import android.app.Application;

import com.example.cineverse.data.model.api.Failure;
import com.example.cineverse.data.model.search.SearchContentResponse;
import com.example.cineverse.data.source.search.SearchContentRemoteResponseCallback;
import com.example.cineverse.repository.search.SearchResultRepository;
import com.example.cineverse.viewmodel.verified_account.section.home.content.AbstractSectionContentViewModel;

public class SearchResultViewModel
        extends AbstractSectionContentViewModel
        implements SearchContentRemoteResponseCallback {

    private final String query;
    private final SearchResultRepository repository;

    /**
     * Constructs an {@link SearchResultViewModel} object with the given {@link Application}.
     *
     * @param application The {@link Application} of the calling component.
     * @param query The query of research.
     */
    public SearchResultViewModel(Application application, String query) {
        super(application);
        this.query = query;
        repository = new SearchResultRepository(application.getApplicationContext(), this);
    }

    /**
     * Initiates the process of fetching content from the repository.
     */
    @Override
    public void fetch() {
        repository.fetch(query, getPage());
    }

    /**
     * Handles the response from the remote data source by updating the LiveData with the retrieved content.
     *
     * @param response The response containing the content data.
     */
    @Override
    public void onRemoteResponse(SearchContentResponse response) {
        if (response != null) {
            handleResponse(response.getParsedResults(), getContentLiveData());
        }
    }

    /**
     * Handles a failure in the content retrieval process by updating the LiveData with the failure information.
     *
     * @param failure The failure object containing details about the failure.
     */
    @Override
    public void onFailure(Failure failure) {
        if (failure != null) {
            handleFailure(failure);
        }
    }

}
