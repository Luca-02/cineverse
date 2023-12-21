package com.example.cineverse.repository.content;

import static com.example.cineverse.utils.constant.Api.STARTING_PAGE;

import android.content.Context;

import com.example.cineverse.data.model.api.Failure;
import com.example.cineverse.data.model.content.AbstractContent;
import com.example.cineverse.data.model.content.AbstractContentResponse;
import com.example.cineverse.data.source.content.SectionContentLocalDataSource;
import com.example.cineverse.data.source.content.AbstractSectionContentRemoteDataSource;
import com.example.cineverse.data.source.content.ISectionContentRemoteDataSource;
import com.example.cineverse.data.source.content.SectionContentLocalResponseCallback;
import com.example.cineverse.data.source.content.SectionContentRemoteResponseCallback;
import com.example.cineverse.utils.NetworkUtils;

/**
 * The {@link AbstractSectionContentRepository} class serves as the base repository for handling section-specific content.
 *
 * @param <T> The type parameter representing the {@link AbstractContent} subclass.
 */
public abstract class AbstractSectionContentRepository<T extends AbstractContent>
        implements ISectionContentRemoteDataSource,
        SectionContentRemoteResponseCallback<T>,
        SectionContentLocalResponseCallback<T> {

    protected final Context context;
    protected final AbstractSectionContentRemoteDataSource<T> remoteDataSource;
    protected final SectionContentLocalDataSource<T> localDataSource;
    protected final SectionContentRemoteResponseCallback<T> callback;

    /**
     * Constructor for AbstractSectionContentRepository.
     *
     * @param context         The application context.
     * @param remoteDataSource The remote data source for section-specific content.
     * @param contentType     The class type of the content.
     * @param callback        The callback for handling remote responses.
     */
    public AbstractSectionContentRepository(Context context,
                                            AbstractSectionContentRemoteDataSource<T> remoteDataSource,
                                            Class<T> contentType,
                                            SectionContentRemoteResponseCallback<T> callback) {
        this.context = context;
        this.remoteDataSource = remoteDataSource;
        this.localDataSource = new SectionContentLocalDataSource<>(context, contentType);
        this.callback = callback;
        remoteDataSource.setRemoteResponseCallback(this);
        localDataSource.setLocalResponseCallback(this);
    }

    /**
     * Fetches section-specific content based on the given page number.
     *
     * @param page The page number for fetching content.
     */
    @Override
    public void fetch(int page) {
        if (NetworkUtils.isNetworkAvailable(context)) {
            remoteDataSource.fetch(page);
        } else if (page == STARTING_PAGE) {
            localDataSource.fetch(remoteDataSource.getSection());
        }
    }

    /**
     * Handles local response callback for section-specific content.
     *
     * @param response The local response containing section-specific content.
     */
    @Override
    public void onLocalResponse(AbstractContentResponse<T> response) {
        callback.onRemoteResponse(response);
    }

    /**
     * Handles remote response callback for section-specific content.
     *
     * @param response The remote response containing section-specific content.
     */
    @Override
    public void onRemoteResponse(AbstractContentResponse<T> response) {
        if (response != null) {
            // Locally save content section if is the first page
            if (response.getPage() == STARTING_PAGE) {
                localDataSource.insertContent(response.getResults(), remoteDataSource.getSection());
            }
            callback.onRemoteResponse(response);
        }
    }

    /**
     * Handles failure callback for section-specific content.
     *
     * @param failure The object representing the failure response.
     */
    @Override
    public void onFailure(Failure failure) {
        callback.onFailure(failure);
    }

}
