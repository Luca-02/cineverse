package com.example.cineverse.data.source.watchlist;

import com.example.cineverse.data.model.content.AbstractContent;
import com.example.cineverse.data.model.details.section.IContentDetails;
import com.example.cineverse.service.NetworkCallback;

import java.util.AbstractList;
import java.util.List;

public interface WatchlistFirebaseCallback extends NetworkCallback {
    void onTimestampForContentInWatchlist(Long timestamp);
    void onUserContentWatchlist(List<AbstractContent> watchlistList, String contentType);
    void onAddedContentToWatchlist(AbstractContent content, Long newTimestamp);
    void onRemovedContentToWatchlist(boolean removed);
}
