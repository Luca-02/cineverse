package com.example.cineverse.data.source.watchlist;

import com.example.cineverse.service.NetworkCallback;

public interface WatchlistFirebaseCallback extends NetworkCallback {
    void onTimestampForContentInWatchlist(Long timestamp);
    void onAddedContentToWatchlist(Long newTimestamp);
    void onRemovedContentToWatchlist(boolean removed);
}
