package com.example.cineverse.repository.watchlist;

import com.example.cineverse.data.model.api.Failure;
import com.example.cineverse.data.model.content.AbstractContent;
import com.example.cineverse.data.model.details.section.IContentDetails;
import com.example.cineverse.data.source.details.AbstractContentDetailsRemoteDataSource;
import com.example.cineverse.data.source.details.ContentDetailsRemoteResponseCallback;
import com.example.cineverse.data.source.watchlist.WatchlistFirebaseCallback;

import java.util.ArrayList;
import java.util.List;

public class UserWatchlistCreator<T extends IContentDetails>
        implements ContentDetailsRemoteResponseCallback<T> {

    private final List<AbstractContent> startList;
    private final String contentType;
    private final AbstractContentDetailsRemoteDataSource<T> remoteDataSource;
    private final WatchlistFirebaseCallback firebaseCallback;
    private final List<AbstractContent> resultList;
    private int index;

    public UserWatchlistCreator(
            List<AbstractContent> startList,
            String contentType,
            AbstractContentDetailsRemoteDataSource<T> remoteDataSource,
            WatchlistFirebaseCallback firebaseCallback) {
        this.startList = startList;
        this.contentType = contentType;
        this.remoteDataSource = remoteDataSource;
        this.firebaseCallback = firebaseCallback;
        remoteDataSource.setRemoteResponseCallback(this);
        resultList = new ArrayList<>();
        index = 0;
    }

    public void create() {
        if (startList != null && startList.size() > 0) {
            remoteDataSource.fetchDetails(startList.get(index).getId());
        } else {
            firebaseCallback.onUserContentWatchlist(startList, contentType);
        }
    }

    @Override
    public void onRemoteResponse(T response) {
        response.setWatchlistTimestamp(startList.get(index).getWatchlistTimestamp());
        resultList.add((AbstractContent) response);
        index++;
        if (index < startList.size()) {
            remoteDataSource.fetchDetails(startList.get(index).getId());
        } else {
            firebaseCallback.onUserContentWatchlist(resultList, contentType);
        }
    }

    @Override
    public void onFailure(Failure failure) {
        firebaseCallback.onUserContentWatchlist(null, contentType);
    }

}
