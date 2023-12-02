package com.example.cineverse.viewmodel.logged;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.cineverse.repository.logged.AbstractLoggedRepository;
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
        implements AbstractLoggedRepository.LoggedCallback {

    private MutableLiveData<Boolean> loggedOutLiveData;

    /**
     * Constructs an {@link AbstractLoggedViewModel} object with the given {@link Application}.
     *
     * @param application The {@link Application} of the calling component.
     * @param userRepository  The {@link AbstractLoggedRepository} associated with the ViewModel.
     */
    public AbstractLoggedViewModel(@NonNull Application application, T userRepository) {
        super(application, userRepository);
    }

    public MutableLiveData<Boolean> getLoggedOutLiveData() {
        if (loggedOutLiveData == null) {
            loggedOutLiveData = new MutableLiveData<>();
        }
        return loggedOutLiveData;
    }

    /**
     * Initiates the user logout process.
     */
    public void logOut() {
        userRepository.logOut(this);
    }

    /**
     * Overrides the {@link AbstractLoggedRepository.LoggedCallback#onLogOut()} method
     * to handle the logout event and update the logged-out LiveData.
     */
    @Override
    public void onLogOut() {
        getLoggedOutLiveData().postValue(true);
    }

}
