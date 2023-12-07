package com.example.cineverse.viewmodel.logged.verified_account.section.home;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.cineverse.data.model.content.Failure;
import com.example.cineverse.data.model.content.poster.AbstractPoster;
import com.example.cineverse.data.model.content.poster.AbstractPosterApiResponse;
import com.example.cineverse.data.source.content.poster.PosterContentResponseCallback;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractSectionViewModel<T extends AbstractPoster>
        extends AndroidViewModel
        implements PosterContentResponseCallback<T> {

    private MutableLiveData<List<T>> contentLiveData;
    private MutableLiveData<Failure> failureLiveData;

    /**
     * Constructs an {@link AbstractSectionViewModel} object with the given {@link Application}.
     *
     * @param application The {@link Application} of the calling component.
     */
    public AbstractSectionViewModel(Application application) {
        super(application);
    }

    public MutableLiveData<List<T>> getContentLiveData() {
        if (contentLiveData == null) {
            contentLiveData = new MutableLiveData<>();
        }
        return contentLiveData;
    }

    public MutableLiveData<Failure> getFailureLiveData() {
        if (failureLiveData == null) {
            failureLiveData = new MutableLiveData<>();
        }
        return failureLiveData;
    }

    public void clearFailureLiveData() {
        failureLiveData = new MutableLiveData<>();
    }

    public void clearContentLiveDataList() {
        getContentLiveData().setValue(new ArrayList<>());
    }

    public boolean isContentEmpty() {
        List<T> content = getContentLiveData().getValue();
        if (content != null) {
            return content.isEmpty();
        }
        return true;
    }

    @Override
    public void onResponse(AbstractPosterApiResponse<T> response) {
        if (response != null) {
            List<T> currentData = getContentLiveData().getValue();
            List<T> resultData = response.getResults();
            if (currentData == null) {
                currentData = new ArrayList<>();
            } else {
                currentData.clear();
            }
            currentData.addAll(resultData);
            getContentLiveData().postValue(currentData);
        }
    }

    @Override
    public void onFailure(Failure failure) {
        getFailureLiveData().postValue(failure);
        clearFailureLiveData();
    }

    public abstract void fetch();

}
