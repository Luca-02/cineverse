package com.example.cineverse.viewmodel.watchlist;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.cineverse.data.model.content.AbstractContent;
import com.example.cineverse.data.source.watchlist.WatchlistFirebaseCallback;
import com.example.cineverse.repository.watchlist.WatchlistRepository;
import com.example.cineverse.utils.mapper.ContentTypeMappingManager;
import com.example.cineverse.viewmodel.verified_account.VerifiedAccountViewModel;

import java.util.List;
import java.util.Objects;

public class WatchlistViewModel
        extends VerifiedAccountViewModel
        implements WatchlistFirebaseCallback {

    protected WatchlistRepository watchlistRepository;
    private MutableLiveData<Long> timestampForContentInWatchlistLiveData;
    private MutableLiveData<List<AbstractContent>> userMovieWatchlistLiveData;
    private MutableLiveData<List<AbstractContent>> userTvWatchlistLiveData;
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

    public MutableLiveData<List<AbstractContent>> getUserMovieWatchlistLiveData() {
        if (userMovieWatchlistLiveData == null) {
            userMovieWatchlistLiveData = new MutableLiveData<>();
        }
        return userMovieWatchlistLiveData;
    }

    public MutableLiveData<List<AbstractContent>> getUserTvWatchlistLiveData() {
        if (userTvWatchlistLiveData == null) {
            userTvWatchlistLiveData = new MutableLiveData<>();
        }
        return userTvWatchlistLiveData;
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
        getUserContentWatchlist(contentType, null);
    }

    public void getUserContentWatchlist(String contentType, Integer sizeLimit) {
        watchlistRepository.getUserContentWatchlist(contentType, sizeLimit);
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
        if (watchlistList != null && contentType != null) {
            if (Objects.equals(contentType, ContentTypeMappingManager.ContentType.MOVIE.getType())) {
                getUserMovieWatchlistLiveData().postValue(watchlistList);
            } else if (Objects.equals(contentType, ContentTypeMappingManager.ContentType.TV.getType())) {
                getUserTvWatchlistLiveData().postValue(watchlistList);
            }
        }
    }

    @Override
    public void onAddedContentToWatchlist(AbstractContent content, Long newTimestamp) {
        getAddedContentToWatchlistLiveData().postValue(newTimestamp);
    }

    @Override
    public void onRemovedContentToWatchlist(boolean removed) {
        getRemovedContentToWatchlistLiveData().postValue(removed);
    }

}
