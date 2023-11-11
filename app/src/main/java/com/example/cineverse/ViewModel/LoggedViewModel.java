package com.example.cineverse.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.cineverse.Repository.Auth.LoggedRepository;
import com.example.cineverse.ViewModel.AbstractAuthViewModel;

/**
 * The LoggedViewModel class extends AbstractAuthViewModel and serves as the base class for ViewModels
 * related to the logged-in user's functionality. It provides MutableLiveData instances for observing
 * the user's authentication status, network error states, and logout events. Subclasses of
 * LoggedViewModel are expected to handle user-specific logic and communicate changes in user
 * authentication status, network errors, and logout events through LiveData objects.
 */
public abstract class LoggedViewModel extends AbstractAuthViewModel {

    protected MutableLiveData<Boolean> loggedOutLiveData;

    /**
     * Constructs a LoggedViewModel object with the given Application context.
     *
     * @param application The Application context of the calling component.
     */
    public LoggedViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Boolean> getLoggedOutLiveData() {
        return loggedOutLiveData;
    }

    protected void setLoggedOutLiveData(LoggedRepository repository) {
        loggedOutLiveData = repository.getLoggedOutLiveData();
    };

    /**
     * Abstract method to be implemented by subclasses. Handles the user logout functionality.
     */
    public abstract void logOut();

}
