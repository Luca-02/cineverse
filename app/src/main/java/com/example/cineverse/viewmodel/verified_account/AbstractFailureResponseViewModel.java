package com.example.cineverse.viewmodel.verified_account;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.cineverse.data.model.api.Failure;

import org.jetbrains.annotations.NotNull;

public abstract class AbstractFailureResponseViewModel
        extends AndroidViewModel {

    private MutableLiveData<Failure> failureLiveData;

    /**
     * Constructs an {@link AbstractFailureResponseViewModel} object with the given {@link Application}.
     *
     * @param application The {@link Application} of the calling component.
     */
    public AbstractFailureResponseViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Failure> getFailureLiveData() {
        if (failureLiveData == null) {
            failureLiveData = new MutableLiveData<>();
        }
        return failureLiveData;
    }

    /**
     * Handles the successful response by updating the content LiveData.
     *
     * @param resultData      The result data to be posted to the content LiveData.
     * @param contentLiveData The LiveData to be updated with the result data.
     */
    public <T> void handleResponse(@NotNull T resultData, @NotNull MutableLiveData<T> contentLiveData) {
        contentLiveData.postValue(resultData);
    }

    /**
     * Handles a failure by posting it to the failure LiveData.
     *
     * @param failure The failure object to be posted to the failure LiveData.
     */
    public void handleFailure(@NotNull Failure failure) {
        getFailureLiveData().postValue(failure);
    }

}
