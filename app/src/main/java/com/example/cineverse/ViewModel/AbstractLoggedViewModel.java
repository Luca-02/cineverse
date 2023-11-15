package com.example.cineverse.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.cineverse.Interface.ILogged;
import com.example.cineverse.Repository.AbstractLoggedRepository;

/**
 * The AbstractLoggedViewModel class extends AbstractAuthViewModel and serves as the base class for ViewModels
 * related to the logged-in user's functionality. It provides MutableLiveData instances for observing
 * the user's authentication status, network error states, and logout events. Subclasses of
 * LoggedViewModel are expected to handle user-specific logic and communicate changes in user
 * authentication status, network errors, and logout events through LiveData objects.
 */
public abstract class AbstractLoggedViewModel extends AbstractAuthViewModel
        implements ILogged {

    protected MutableLiveData<Boolean> loggedOutLiveData;

    /**
     * Constructs a AbstractLoggedViewModel object with the given Application context.
     *
     * @param application The Application context of the calling component.
     */
    public AbstractLoggedViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Boolean> getLoggedOutLiveData() {
        return loggedOutLiveData;
    }

    /**
     * Sets the LiveData instances for observing the current user's authentication status, network error states,
     * and logout events based on the provided repository.
     *
     * @param repository The repository providing LiveData instances for observation.
     */
    protected void setLiveData(AbstractLoggedRepository repository) {
        super.setLiveData(repository);
        loggedOutLiveData = repository.getLoggedOutLiveData();
    }

    /**
     * Abstract method to be implemented by subclasses. Handles the user logout functionality.
     */
    @Override
    public abstract void logOut();

}
