package com.example.cineverse.repository.watchlist;

import android.content.Context;

import com.example.cineverse.data.model.User;
import com.example.cineverse.data.model.content.AbstractContent;
import com.example.cineverse.data.model.details.section.IContentDetails;
import com.example.cineverse.data.model.details.section.MovieDetails;
import com.example.cineverse.data.model.details.section.TvDetails;
import com.example.cineverse.data.source.details.section.MovieDetailsRemoteDataSource;
import com.example.cineverse.data.source.details.section.TvDetailsRemoteDataSource;
import com.example.cineverse.data.source.watchlist.WatchlistFirebaseCallback;
import com.example.cineverse.data.source.watchlist.WatchlistFirebaseSource;
import com.example.cineverse.utils.mapper.ContentTypeMappingManager;

import java.util.List;
import java.util.Objects;

public class WatchlistRepository
        implements WatchlistFirebaseCallback {

    private final Context context;
    private final User currentuser;
    private final WatchlistFirebaseSource watchlistFirebaseSource;
    private final WatchlistFirebaseCallback firebaseCallback;
    private final WatchlistNotificationHandler watchlistNotificationHandler;

    public WatchlistRepository(Context context, User currentuser, WatchlistFirebaseCallback firebaseCallback) {
        this.context = context;
        this.currentuser = currentuser;
        this.firebaseCallback = firebaseCallback;
        this.watchlistFirebaseSource = new WatchlistFirebaseSource(context, this);
        watchlistNotificationHandler = new WatchlistNotificationHandler(context);
    }

    public void getTimestampForContentInWatchlist(AbstractContent content) {
        if (currentuser != null) {
            watchlistFirebaseSource.getTimestampForContentInWatchlist(currentuser, content);
        }
    }

    public void getUserContentWatchlist(String contentType, Integer sizeLimit) {
        if (currentuser != null) {
            watchlistFirebaseSource.getUserContentWatchlist(currentuser, contentType, sizeLimit);
        }
    }

    public void addContentToWatchlist(AbstractContent content) {
        if (currentuser != null) {
            watchlistFirebaseSource.addContentToWatchlist(currentuser, content);
        }
    }

    public void removeContentToWatchlist(AbstractContent content) {
        if (currentuser != null) {
            watchlistFirebaseSource.removeContentToWatchlist(currentuser, content);
        }
    }

    @Override
    public void onTimestampForContentInWatchlist(Long timestamp) {
        firebaseCallback.onTimestampForContentInWatchlist(timestamp);
    }

    @Override
    public void onUserContentWatchlist(List<AbstractContent> watchlistList, String contentType) {
        if (Objects.equals(contentType, ContentTypeMappingManager.ContentType.MOVIE.getType())) {
            UserWatchlistCreator<MovieDetails> userWatchlistCreator =
                    new UserWatchlistCreator<>(watchlistList, contentType, new MovieDetailsRemoteDataSource(context), firebaseCallback);
            userWatchlistCreator.create();
        } else if (Objects.equals(contentType, ContentTypeMappingManager.ContentType.TV.getType())) {
            UserWatchlistCreator<TvDetails> userWatchlistCreator =
                    new UserWatchlistCreator<>(watchlistList, contentType, new TvDetailsRemoteDataSource(context), firebaseCallback);
            userWatchlistCreator.create();
        } else {
            firebaseCallback.onUserContentWatchlist(null, contentType);
        }
    }

    @Override
    public void onAddedContentToWatchlist(AbstractContent content, Long newTimestamp) {
        watchlistNotificationHandler.showNotification(content);
        firebaseCallback.onAddedContentToWatchlist(content, newTimestamp);
    }

    @Override
    public void onRemovedContentToWatchlist(boolean removed) {
        firebaseCallback.onRemovedContentToWatchlist(removed);
    }

    @Override
    public void onNetworkUnavailable() {
        firebaseCallback.onNetworkUnavailable();
    }

}
