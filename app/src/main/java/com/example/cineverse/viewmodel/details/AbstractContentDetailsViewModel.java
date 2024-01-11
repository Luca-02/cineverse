package com.example.cineverse.viewmodel.details;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.cineverse.data.model.api.Failure;
import com.example.cineverse.data.model.details.section.IContentDetails;
import com.example.cineverse.data.source.details.ContentDetailsRemoteResponseCallback;
import com.example.cineverse.data.source.details.IContentDetailsRemoteDataSource;
import com.example.cineverse.data.source.watchlist.WatchlistFirebaseCallback;
import com.example.cineverse.repository.details.AbstractDetailsRepository;
import com.example.cineverse.viewmodel.watchlist.WatchlistViewModel;

public abstract class AbstractContentDetailsViewModel<T extends IContentDetails>
        extends WatchlistViewModel
        implements IContentDetailsRemoteDataSource,
        ContentDetailsRemoteResponseCallback<T>,
        WatchlistFirebaseCallback {

    protected AbstractDetailsRepository<T> detailsRepository;
    private MutableLiveData<T> contentDetailsLiveData;
    private MutableLiveData<Failure> failureLiveData;

    public AbstractContentDetailsViewModel(
            @NonNull Application application,
            AbstractDetailsRepository<T> detailsRepository) {
        super(application);
        this.detailsRepository = detailsRepository;
        detailsRepository.setRemoteResponseCallback(this);
    }

    public MutableLiveData<T> getContentDetailsLiveData() {
        if (contentDetailsLiveData == null) {
            contentDetailsLiveData = new MutableLiveData<>();
        }
        return contentDetailsLiveData;
    }

    public MutableLiveData<Failure> getFailureLiveData() {
        if (failureLiveData == null) {
            failureLiveData = new MutableLiveData<>();
        }
        return failureLiveData;
    }

    @Override
    public void fetchDetails(int contentId) {
        detailsRepository.fetchDetails(contentId);
    }

    @Override
    public void onRemoteResponse(T response) {
        if (response != null) {
            getContentDetailsLiveData().postValue(response);
        }
    }

    @Override
    public void onFailure(Failure failure) {
        if (failure != null) {
            getFailureLiveData().postValue(failure);
        }
    }

}

