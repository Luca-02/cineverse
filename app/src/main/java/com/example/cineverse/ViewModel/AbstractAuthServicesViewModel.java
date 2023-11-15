package com.example.cineverse.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.cineverse.Repository.AbstractAuthRepository;
import com.example.cineverse.Repository.AbstractAuthServiceRepository;

/**
 * The AbstractAuthServicesViewModel class extends AbstractAuthViewModel and serves as a base class
 * for view models related to authentication services. It provides MutableLiveData for observing
 * authentication-related errors.
 */
public abstract class AbstractAuthServicesViewModel extends AbstractAuthViewModel {

    private MutableLiveData<AbstractAuthRepository.Error> errorLiveData;

    /**
     * Constructs an AbstractAuthServicesViewModel object with the given Application context and initializes
     * MutableLiveData for observing authentication-related errors.
     *
     * @param application The Application context of the calling component.
     */
    public AbstractAuthServicesViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<AbstractAuthRepository.Error> getErrorLiveData() {
        return errorLiveData;
    }

    /**
     * Clears the MutableLiveData instance for authentication-related errors status.
     * This method is typically used when the network error state is consumed, and you don't want
     * to keep the last state in the LiveData.
     */
    public void clearErrorLiveData() {
        errorLiveData = new MutableLiveData<>();
    }

    /**
     * Sets the LiveData for observing authentication-related errors based on the provided repository.
     *
     * @param repository The repository providing LiveData for observing authentication-related errors.
     */
    protected void setLiveData(AbstractAuthServiceRepository repository) {
        super.setLiveData(repository);
        errorLiveData = repository.getErrorLiveData();
    }

}
