package com.example.cineverse.repository.auth;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.example.cineverse.repository.UserRepository;

/**
 * The {@link AbstractLoggedRepository} class serves as the base class for repositories related to logged-in users.
 * It extends {@link UserRepository}. Subclasses of {@link AbstractLoggedRepository} are expected to handle user
 * logout logic. It provides {@link LoggedCallback} to communicate the log-out status to the caller.
 */
public abstract class AbstractLoggedRepository
        extends UserRepository {

    private LoggedCallback loggedCallback;

    /**
     * Constructs an {@link AbstractLoggedRepository} object with the given application {@link Context} and initializes
     * {@link MutableLiveData} for observing user logout status.
     *
     * @param context The application {@link Context} of the calling component.
     */
    public AbstractLoggedRepository(Context context) {
        super(context);
    }

    public void setLoggedCallback(LoggedCallback loggedCallback) {
        this.loggedCallback = loggedCallback;
    }

    /**
     * Logs out the currently authenticated user by invoking {@link UserRepository#clearAllUser clearAllUser}
     * method and communicates the logout status through {@link #loggedCallback}.
     */
    public void logOut() {
        clearAllUser();
        loggedCallback.onLogOut();
    }

    /**
     * Callback interface for handling the result of the user logout operation.
     */
    public interface LoggedCallback {
        /**
         * Invoked when the user logout operation is completed.
         */
        void onLogOut();
    }

}
