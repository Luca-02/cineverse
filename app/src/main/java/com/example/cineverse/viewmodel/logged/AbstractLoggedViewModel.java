package com.example.cineverse.viewmodel.logged;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.cineverse.repository.interfaces.logged.ILogged;
import com.example.cineverse.repository.classes.logged.AbstractLoggedRepository;
import com.example.cineverse.viewmodel.AbstractUserViewModel;

/**
 * The {@link AbstractLoggedViewModel} class extends {@link AbstractUserViewModel} and serves as the base class for ViewModels
 * related to the logged-in user's functionality. It provides MutableLiveData instances for observing
 * the user's authentication status, network error states, and logout events. Subclasses of
 * {@link AbstractLoggedViewModel} are expected to handle user-specific logic and communicate changes in user
 * authentication status, network errors, and logout events through LiveData objects.
 *
 * @param <T> The type of {@link AbstractLoggedRepository} associated with the ViewModel.
 */
public abstract class AbstractLoggedViewModel<T extends AbstractLoggedRepository>
        extends AbstractUserViewModel<T>
        implements ILogged {

    protected MutableLiveData<Boolean> loggedOutLiveData;

    /**
     * Constructs an {@link AbstractLoggedViewModel} object with the given {@link Application}.
     *
     * @param application The {@link Application} of the calling component.
     * @param repository  The {@link AbstractLoggedRepository} associated with the ViewModel.
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
