package com.example.cineverse.viewmodel.details;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.cineverse.data.model.api.Failure;
import com.example.cineverse.data.model.content.AbstractContent;
import com.example.cineverse.data.model.details.section.ContentDetailsApiResponse;
import com.example.cineverse.data.source.details.ContentDetailsRemoteResponseCallback;
import com.example.cineverse.data.source.details.IContentDetailsRemoteDataSource;
import com.example.cineverse.repository.details.AbstractDetailsRepository;
import com.example.cineverse.viewmodel.AbstractLoggedViewModel;

public abstract class AbstractContentDetailsViewModel<T extends ContentDetailsApiResponse>
        extends AbstractLoggedViewModel<AbstractDetailsRepository<T>>
        implements IContentDetailsRemoteDataSource,
        ContentDetailsRemoteResponseCallback<T>,
        AbstractDetailsRepository.WatchlistCallback {

    private MutableLiveData<T> contentDetailsLiveData;
    private MutableLiveData<Boolean> addedToWatchlistLiveData;
    private MutableLiveData<Failure> failureLiveData;

    public AbstractContentDetailsViewModel(@NonNull Application application, AbstractDetailsRepository<T> detailsRepository) {
        super(application, detailsRepository);
        userRepository.setRemoteResponseCallback(this);
        userRepository.setWatchlistCallback(this);
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

    public MutableLiveData<Boolean> getAddedToWatchlistLiveData() {
        if (addedToWatchlistLiveData == null) {
            addedToWatchlistLiveData = new MutableLiveData<>();
        }
        return addedToWatchlistLiveData;
    }

    @Override
    public void fetchDetails(int contentId) {
        userRepository.fetchDetails(contentId);
    }

    public void addContentToWatchlist(AbstractContent content) {
        userRepository.addContentToWatchlist(content);
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

    @Override
    public void onContentAddedToWatchlist(boolean added) {
        getAddedToWatchlistLiveData().postValue(added);
    }

}
