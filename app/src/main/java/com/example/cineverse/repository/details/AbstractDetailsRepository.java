package com.example.cineverse.repository.details;

import com.example.cineverse.data.model.api.Failure;
import com.example.cineverse.data.model.details.section.IContentDetails;
import com.example.cineverse.data.source.details.AbstractContentDetailsRemoteDataSource;
import com.example.cineverse.data.source.details.ContentDetailsRemoteResponseCallback;
import com.example.cineverse.data.source.details.IContentDetailsRemoteDataSource;

public abstract class AbstractDetailsRepository<T extends IContentDetails>
        implements IContentDetailsRemoteDataSource, ContentDetailsRemoteResponseCallback<T> {

    private final AbstractContentDetailsRemoteDataSource<T> remoteDataSource;
    private ContentDetailsRemoteResponseCallback<T> remoteResponseCallback;

    public AbstractDetailsRepository(AbstractContentDetailsRemoteDataSource<T> remoteDataSource) {
        this.remoteDataSource = remoteDataSource;
        remoteDataSource.setRemoteResponseCallback(this);
    }

    public void setRemoteResponseCallback(ContentDetailsRemoteResponseCallback<T> remoteResponseCallback) {
        this.remoteResponseCallback = remoteResponseCallback;
    }

    @Override
    public void fetchDetails(int movieId) {
        remoteDataSource.fetchDetails(movieId);
    }

    @Override
    public void onRemoteResponse(T response) {
        remoteResponseCallback.onRemoteResponse(response);
    }

    @Override
    public void onFailure(Failure failure) {
        remoteResponseCallback.onFailure(failure);
    }

}
