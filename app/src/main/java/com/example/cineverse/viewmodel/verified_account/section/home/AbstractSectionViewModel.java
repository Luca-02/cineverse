package com.example.cineverse.viewmodel.verified_account.section.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.cineverse.data.model.api.Failure;

import org.jetbrains.annotations.NotNull;

/**
 * The {@link AbstractSectionViewModel} class is an abstract base class for ViewModel classes
 * representing various sections in the home screen of the application.
 */
public abstract class AbstractSectionViewModel
        extends AndroidViewModel {

    private MutableLiveData<Failure> failureLiveData;

    /**
     * Constructs an {@link AbstractSectionViewModel} object with the given {@link Application}.
     *
     * @param application The {@link Application} of the calling component.
     */
    public AbstractSectionViewModel(@NonNull Application application) {
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

    /**
     * Clears the content LiveData list.
     */
    public abstract void emptyContentLiveDataList();

    /**
     * Checks if the content LiveData list is empty.
     *
     * @return True if the content LiveData list is empty, false otherwise.
     */
    public abstract boolean isContentEmpty();

    /**
     * Initiates the data fetching process.
     */
    public abstract void fetch();

}
