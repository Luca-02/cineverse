package com.example.cineverse.viewmodel.watchlist;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.cineverse.data.model.content.AbstractContent;
import com.example.cineverse.data.model.details.section.IContentDetails;
import com.example.cineverse.data.source.watchlist.WatchlistFirebaseCallback;
import com.example.cineverse.repository.watchlist.WatchlistRepository;
import com.example.cineverse.viewmodel.verified_account.VerifiedAccountViewModel;

import java.util.List;

public class WatchlistViewModel
        extends VerifiedAccountViewModel
        implements WatchlistFirebaseCallback {

    protected WatchlistRepository watchlistRepository;
    private MutableLiveData<Long> timestampForContentInWatchlistLiveData;
    private MutableLiveData<List<AbstractContent>> userContentWatchlistLiveData;
    private MutableLiveData<Long> addedContentToWatchlistLiveData;
    private MutableLiveData<Boolean> removedContentToWatchlistLiveData;
    private boolean firstTimeLoadTimestampInWatchlist;

    public WatchlistViewModel(@NonNull Application application) {
        super(application);
        watchlistRepository = new WatchlistRepository(
                application.getApplicationContext(), userRepository.getCurrentUser(), this);
        firstTimeLoadTimestampInWatchlist = true;
    }

    public MutableLiveData<Long> getTimestampForContentInWatchlistLiveData() {
        if (timestampForContentInWatchlistLiveData == null) {
            timestampForContentInWatchlistLiveData = new MutableLiveData<>();
        }
        return timestampForContentInWatchlistLiveData;
    }

    public MutableLiveData<List<AbstractContent>> getUserContentWatchlistLiveData() {
        if (userContentWatchlistLiveData == null) {
            userContentWatchlistLiveData = new MutableLiveData<>();
        }
        return userContentWatchlistLiveData;
    }

    public MutableLiveData<Long> getAddedContentToWatchlistLiveData() {
        if (addedContentToWatchlistLiveData == null) {
            addedContentToWatchlistLiveData = new MutableLiveData<>();
        }
        return addedContentToWatchlistLiveData;
    }

    public MutableLiveData<Boolean> getRemovedContentToWatchlistLiveData() {
        if (removedContentToWatchlistLiveData == null) {
            removedContentToWatchlistLiveData = new MutableLiveData<>();
        }
        return removedContentToWatchlistLiveData;
    }

    public boolean isFirstTimeLoadTimestampInWatchlist() {
        return firstTimeLoadTimestampInWatchlist;
    }

    public void setFirstTimeLoadTimestampInWatchlist(boolean firstTimeLoadTimestampInWatchlist) {
        this.firstTimeLoadTimestampInWatchlist = firstTimeLoadTimestampInWatchlist;
    }

    public void getTimestampForContentInWatchlist(AbstractContent content) {
        watchlistRepository.getTimestampForContentInWatchlist(content);
    }

    public void getUserContentWatchlist(String contentType) {
        watchlistRepository.getUserContentWatchlist(contentType);
    }

    public void addContentToWatchlist(AbstractContent content) {
        watchlistRepository.addContentToWatchlist(content);
    }

    public void removeContentToWatchlist(AbstractContent content) {
        watchlistRepository.removeContentToWatchlist(content);
    }

    @Override
    public void onTimestampForContentInWatchlist(Long timestamp) {
        getTimestampForContentInWatchlistLiveData().postValue(timestamp);
    }

    @Override
    public void onUserContentWatchlist(List<AbstractContent> watchlistList, String contentType) {
        if (watchlistList != null) {
            getUserContentWatchlistLiveData().postValue(watchlistList);
        }
    }

    @Override
    public void onAddedContentToWatchlist(Long newTimestamp) {
        getAddedContentToWatchlistLiveData().postValue(newTimestamp);
    }

    @Override
    public void onRemovedContentToWatchlist(boolean removed) {
        getRemovedContentToWatchlistLiveData().postValue(removed);
    }

}
