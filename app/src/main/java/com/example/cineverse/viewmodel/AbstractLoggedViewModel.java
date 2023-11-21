package com.example.cineverse.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.cineverse.interfaces.ILogged;
import com.example.cineverse.repository.AbstractLoggedRepository;

/**
 * The AbstractLoggedViewModel class extends AbstractAuthViewModel and serves as the base class for ViewModels
 * related to the logged-in user's functionality. It provides MutableLiveData instances for observing
 * the user's authentication status, network error states, and logout events. Subclasses of
 * LoggedViewModel are expected to handle user-specific logic and communicate changes in user
 * authentication status, network errors, and logout events through LiveData objects.
 *
 * @param <T> The type of repository associated with the ViewModel.
 */
public abstract class AbstractLoggedViewModel<T extends AbstractLoggedRepository>
        extends AbstractAuthViewModel<T>
        implements ILogged {

    protected MutableLiveData<Boolean> loggedOutLiveData;

    /**
     * Constructs an AbstractLoggedViewModel object with the given Application context.
     *
     * @param application The Application context of the calling component.
     * @param repository  The repository associated with the ViewModel.
     */
    public AbstractLoggedViewModel(@NonNull Application application, T repository) {
        super(application, repository);
        loggedOutLiveData = repository.getLoggedOutLiveData();
    }

    public MutableLiveData<Boolean> getLoggedOutLiveData() {
        return loggedOutLiveData;
    }

    /**
     * Abstract method to be implemented by subclasses. Handles the user logout functionality.
     */
    @Override
    public abstract void logOut();

}
