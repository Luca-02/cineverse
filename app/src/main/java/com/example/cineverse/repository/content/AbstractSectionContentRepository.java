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

public abstract class AbstractSectionContentRepository<T extends AbstractContent>
        implements ISectionContentRemoteDataSource,
        SectionContentRemoteResponseCallback<T>,
        SectionContentLocalResponseCallback<T> {

    protected final Context context;
    protected final AbstractSectionContentRemoteDataSource<T> remoteDataSource;
    protected final SectionContentLocalDataSource<T> localDataSource;
    protected final SectionContentRemoteResponseCallback<T> callback;

    public AbstractSectionContentRepository(Context context,
                                            AbstractSectionContentRemoteDataSource<T> remoteDataSource,
                                            Class<T> contentType,
                                            SectionContentRemoteResponseCallback<T> callback) {
        this.context = context;
        this.remoteDataSource = remoteDataSource;
        this.localDataSource = new SectionContentLocalDataSource<>(context, contentType);
        this.callback = callback;
        remoteDataSource.setCallback(this);
        localDataSource.setCallback(this);
    }

    @Override
    public void fetch(int page) {
        if (NetworkUtils.isNetworkAvailable(context)) {
            remoteDataSource.fetch(page);
        } else if (page == STARTING_PAGE) {
            localDataSource.fetch(remoteDataSource.getSection());
        }
    }

    @Override
    public void onLocalResponse(AbstractContentResponse<T> response) {
        callback.onRemoteResponse(response);
    }

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

    @Override
    public void onFailure(Failure failure) {
        callback.onFailure(failure);
    }

}
