package com.example.cineverse.data.source.details;

import android.content.Context;

import com.example.cineverse.data.model.details.section.IContentDetails;
import com.example.cineverse.data.source.api.TMDBRemoteDataSource;
import com.example.cineverse.service.api.DetailsApiService;
import com.example.cineverse.utils.ServiceLocator;

import retrofit2.Call;

public abstract class AbstractContentDetailsRemoteDataSource<T extends IContentDetails>
        extends TMDBRemoteDataSource
        implements IContentDetailsRemoteDataSource {

    protected final DetailsApiService detailsApiService;
    private ContentDetailsRemoteResponseCallback<T> remoteResponseCallback;

    public AbstractContentDetailsRemoteDataSource(Context context) {
        super(context);
        detailsApiService = ServiceLocator.getInstance().getDetailsApiService();
    }

    public void setRemoteResponseCallback(ContentDetailsRemoteResponseCallback<T> remoteResponseCallback) {
        this.remoteResponseCallback = remoteResponseCallback;
    }

    @Override
    public void fetchDetails(int contentId) {
        Call<T> call = createApiCall(contentId);
        handleApiCall(call, remoteResponseCallback);
    }

    public abstract Call<T> createApiCall(int contentId);

}
