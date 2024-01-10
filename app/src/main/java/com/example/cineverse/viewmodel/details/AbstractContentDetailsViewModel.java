package com.example.cineverse.viewmodel.details;

        import android.app.Application;
        import android.util.Log;

        import androidx.annotation.NonNull;
        import androidx.lifecycle.MutableLiveData;

        import com.example.cineverse.data.model.api.Failure;
        import com.example.cineverse.data.model.content.AbstractContent;
        import com.example.cineverse.data.model.details.section.ContentDetailsApiResponse;
        import com.example.cineverse.data.source.details.ContentDetailsRemoteResponseCallback;
        import com.example.cineverse.data.source.details.IContentDetailsRemoteDataSource;
        import com.example.cineverse.data.source.watchlist.WatchlistFirebaseCallback;
        import com.example.cineverse.repository.details.AbstractDetailsRepository;
        import com.example.cineverse.repository.watchlist.WatchlistRepository;
        import com.example.cineverse.utils.constant.GlobalConstant;
        import com.example.cineverse.viewmodel.verified_account.VerifiedAccountViewModel;

public abstract class AbstractContentDetailsViewModel<T extends ContentDetailsApiResponse>
        extends VerifiedAccountViewModel
        implements IContentDetailsRemoteDataSource,
        ContentDetailsRemoteResponseCallback<T>,
        WatchlistFirebaseCallback {

    protected AbstractDetailsRepository<T> detailsRepository;
    protected WatchlistRepository watchlistRepository;
    private MutableLiveData<T> contentDetailsLiveData;
    private MutableLiveData<Long> timestampInWatchlistLiveData;
    private MutableLiveData<Long> addedToWatchlistLiveData;
    private MutableLiveData<Boolean> removedToWatchlistLiveData;
    private MutableLiveData<Failure> failureLiveData;
    private boolean firstTimeLoadTimestampInWatchlist;

    public AbstractContentDetailsViewModel(@NonNull Application application) {
        super(application);
        watchlistRepository = new WatchlistRepository(
                application.getApplicationContext(), userRepository.getCurrentUser(), this);
        firstTimeLoadTimestampInWatchlist = true;
    }

    public MutableLiveData<T> getContentDetailsLiveData() {
        if (contentDetailsLiveData == null) {
            contentDetailsLiveData = new MutableLiveData<>();
        }
        return contentDetailsLiveData;
    }

    public MutableLiveData<Long> getTimestampInWatchlistLiveData() {
        if (timestampInWatchlistLiveData == null) {
            timestampInWatchlistLiveData = new MutableLiveData<>();
        }
        return timestampInWatchlistLiveData;
    }

    public MutableLiveData<Failure> getFailureLiveData() {
        if (failureLiveData == null) {
            failureLiveData = new MutableLiveData<>();
        }
        return failureLiveData;
    }

    public MutableLiveData<Long> getAddedToWatchlistLiveData() {
        if (addedToWatchlistLiveData == null) {
            addedToWatchlistLiveData = new MutableLiveData<>();
        }
        return addedToWatchlistLiveData;
    }

    public MutableLiveData<Boolean> getRemovedToWatchlistLiveData() {
        if (removedToWatchlistLiveData == null) {
            removedToWatchlistLiveData = new MutableLiveData<>();
        }
        return removedToWatchlistLiveData;
    }

    public boolean isFirstTimeLoadTimestampInWatchlist() {
        return firstTimeLoadTimestampInWatchlist;
    }

    public void setFirstTimeLoadTimestampInWatchlist(boolean firstTimeLoadTimestampInWatchlist) {
        this.firstTimeLoadTimestampInWatchlist = firstTimeLoadTimestampInWatchlist;
    }

    @Override
    public void fetchDetails(int contentId) {
        detailsRepository.fetchDetails(contentId);
    }

    public void getTimestampForContentInWatchlist(AbstractContent content) {
        watchlistRepository.getTimestampForContentInWatchlist(content);
    }

    public void addContentToWatchlist(AbstractContent content) {
        watchlistRepository.addContentToWatchlist(content);
    }

    public void removeContentToWatchlist(AbstractContent content) {
        watchlistRepository.removeContentToWatchlist(content);
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
    public void onTimestampForContentInWatchlist(Long timestamp) {
        getTimestampInWatchlistLiveData().postValue(timestamp);
    }

    @Override
    public void onAddedContentToWatchlist(Long newTimestamp) {
        getAddedToWatchlistLiveData().postValue(newTimestamp);
    }

    @Override
    public void onRemovedContentToWatchlist(boolean removed) {
        getRemovedToWatchlistLiveData().postValue(removed);
    }

}

