package com.example.cineverse.repository.watchlist;

import android.content.Context;

import com.example.cineverse.data.model.User;
import com.example.cineverse.data.model.content.AbstractContent;
import com.example.cineverse.data.model.details.section.ContentDetailsApiResponse;
import com.example.cineverse.data.source.watchlist.WatchlistFirebaseCallback;
import com.example.cineverse.data.source.watchlist.WatchlistFirebaseSource;

public class WatchlistRepository
        implements WatchlistFirebaseCallback {

    private final User user;
    private final WatchlistFirebaseSource watchlistFirebaseSource;
    private final WatchlistFirebaseCallback firebaseCallback;

    public WatchlistRepository(Context context, User user, WatchlistFirebaseCallback firebaseCallback) {
        this.user = user;
        this.firebaseCallback = firebaseCallback;
        this.watchlistFirebaseSource = new WatchlistFirebaseSource(context, this);
    }

    public void getTimestampForContentInWatchlist(AbstractContent content) {
        if (user != null) {
            watchlistFirebaseSource.getTimestampForContentInWatchlist(user, content);
        }
    }

    public void addContentToWatchlist(AbstractContent content) {
        if (user != null) {
            watchlistFirebaseSource.addContentToWatchlist(user, content);
        }
    }

    public void removeContentToWatchlist(AbstractContent content) {
        if (user != null) {
            watchlistFirebaseSource.removeContentToWatchlist(user, content);
        }
    }

    @Override
    public void onTimestampForContentInWatchlist(Long timestamp) {
        firebaseCallback.onTimestampForContentInWatchlist(timestamp);
    }

    @Override
    public void onAddedContentToWatchlist(Long newTimestamp) {
        firebaseCallback.onAddedContentToWatchlist(newTimestamp);
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
