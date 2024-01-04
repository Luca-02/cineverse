package com.example.cineverse.repository.details;

import android.content.Context;

import com.example.cineverse.data.model.User;
import com.example.cineverse.data.model.api.Failure;
import com.example.cineverse.data.model.content.AbstractContent;
import com.example.cineverse.data.model.details.section.ContentDetailsApiResponse;
import com.example.cineverse.data.source.details.AbstractContentDetailsRemoteDataSource;
import com.example.cineverse.data.source.details.ContentDetailsRemoteResponseCallback;
import com.example.cineverse.data.source.details.IContentDetailsRemoteDataSource;
import com.example.cineverse.data.source.user.WatchlistFirebaseSource;
import com.example.cineverse.repository.auth.AbstractLoggedRepository;
import com.example.cineverse.service.firebase.FirebaseCallback;

public abstract class AbstractDetailsRepository<T extends ContentDetailsApiResponse>
        extends AbstractLoggedRepository
        implements IContentDetailsRemoteDataSource, ContentDetailsRemoteResponseCallback<T> {

    private final AbstractContentDetailsRemoteDataSource<T> remoteDataSource;
    private final WatchlistFirebaseSource watchlistFirebaseSource;
    private ContentDetailsRemoteResponseCallback<T> remoteResponseCallback;
    private WatchlistCallback watchlistCallback;

    public AbstractDetailsRepository(
            Context context,
            AbstractContentDetailsRemoteDataSource<T> remoteDataSource) {
        super(context);
        this.remoteDataSource = remoteDataSource;
        remoteDataSource.setRemoteResponseCallback(this);
        watchlistFirebaseSource = new WatchlistFirebaseSource(context);
    }

    public void setRemoteResponseCallback(ContentDetailsRemoteResponseCallback<T> remoteResponseCallback) {
        this.remoteResponseCallback = remoteResponseCallback;
    }

    public void setWatchlistCallback(WatchlistCallback watchlistCallback) {
        this.watchlistCallback = watchlistCallback;
    }

    @Override
    public void fetchDetails(int movieId) {
        remoteDataSource.fetchDetails(movieId);
    }

    public void addContentToWatchlist(AbstractContent content) {
        User user = getCurrentUser();
        if (user != null) {
            watchlistFirebaseSource.addContentToWatchlist(user, content, new FirebaseCallback<Boolean>() {
                @Override
                public void onCallback(Boolean added) {
                    watchlistCallback.onContentAddedToWatchlist(added);
                }

                @Override
                public void onNetworkUnavailable() {
                    watchlistCallback.onNetworkError();
                }
            });
        }
    }

    @Override
    public void onRemoteResponse(T response) {
        remoteResponseCallback.onRemoteResponse(response);
    }

    @Override
    public void onFailure(Failure failure) {
        remoteResponseCallback.onFailure(failure);
    }

    public interface WatchlistCallback extends NetworkCallback {
        void onContentAddedToWatchlist(boolean added);
    }

}
