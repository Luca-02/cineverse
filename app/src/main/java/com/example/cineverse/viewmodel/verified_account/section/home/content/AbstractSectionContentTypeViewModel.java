package com.example.cineverse.viewmodel.verified_account.section.home.content;

import android.app.Application;

import com.example.cineverse.data.model.api.Failure;
import com.example.cineverse.data.model.content.AbstractContent;
import com.example.cineverse.data.model.content.AbstractContentResponse;
import com.example.cineverse.data.source.content.SectionContentRemoteResponseCallback;
import com.example.cineverse.repository.content.AbstractSectionContentRepository;

/**
 * The {@link AbstractSectionContentTypeViewModel} class extends {@link AbstractSectionContentViewModel} and is an abstract
 * base class for ViewModel classes representing content sections with a specific content type (e.g., movies or TV shows)
 * in the home screen.
 *
 * @param <T> The type of content that the ViewModel manages.
 */
public abstract class AbstractSectionContentTypeViewModel<T extends AbstractContent>
        extends AbstractSectionContentViewModel
        implements SectionContentRemoteResponseCallback<T> {

    protected AbstractSectionContentRepository<T> repository;

    /**
     * Constructs an {@link AbstractSectionContentTypeViewModel} object with the given {@link Application}
     * and a genre ID specific to the content type.
     *
     * @param application The {@link Application} of the calling component.
     */
    public AbstractSectionContentTypeViewModel(Application application) {
        super(application);
    }

    /**
     * Initiates the process of fetching content from the repository.
     */
    @Override
    public void fetch() {
        repository.fetch(getPage());
    }

    /**
     * Handles the response from the remote data source by updating the LiveData with the retrieved content.
     *
     * @param response The response containing the content data.
     */
    @Override
    public void onRemoteResponse(AbstractContentResponse<T> response) {
        if (response != null) {
            handleResponse(response.getResults(), getContentLiveData());
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
