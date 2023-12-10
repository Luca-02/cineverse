package com.example.cineverse.viewmodel.logged.verified_account.section.home;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.cineverse.data.model.content.Failure;
import com.example.cineverse.data.model.content.AbstractContent;
import com.example.cineverse.data.source.content.poster.IPosterContentRemoteDataSource;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractSectionViewModel
        extends AndroidViewModel {

    private MutableLiveData<List<? extends AbstractContent>> contentLiveData;
    private MutableLiveData<Failure> failureLiveData;
    private int page;

    /**
     * Constructs an {@link AbstractSectionViewModel} object with the given {@link Application}.
     *
     * @param application The {@link Application} of the calling component.
     */
    public AbstractSectionViewModel(Application application) {
        super(application);
        page = 1;
    }

    public MutableLiveData<List<? extends AbstractContent>> getContentLiveData() {
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

    public int getPage() {
        return page;
    }

    public void increasePage() {
        page++;
    }

    public boolean isContentEmpty() {
        List<? extends AbstractContent> content = getContentLiveData().getValue();
        if (content != null) {
            return content.isEmpty();
        }
        return true;
    }

    public void fetchAndIncreasePage() {
        fetch();
        increasePage();
    }

    public abstract void fetch();

}
