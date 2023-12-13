package com.example.cineverse.viewmodel.verified_account.section.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.cineverse.data.model.Failure;

import org.jetbrains.annotations.NotNull;

public abstract class AbstractSectionViewModel
        extends AndroidViewModel {

    private MutableLiveData<Failure> failureLiveData;

    public AbstractSectionViewModel(@NonNull Application application) {
        super(application);
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

    public <T> void handleResponse(@NotNull T resultData, @NotNull MutableLiveData<T> contentLiveData) {
        contentLiveData.postValue(resultData);
    }

    public void handleFailure(@NotNull Failure failure) {
        getFailureLiveData().postValue(failure);
        clearFailureLiveData();
    }

    public abstract void emptyContentLiveDataList();
    public abstract boolean isContentEmpty();
    public abstract void fetch();

}
