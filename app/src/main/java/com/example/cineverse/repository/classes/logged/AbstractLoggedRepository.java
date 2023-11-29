package com.example.cineverse.repository.classes.logged;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.example.cineverse.repository.classes.UserRepository;
import com.example.cineverse.repository.interfaces.logged.ILogged;

/**
 * The {@link AbstractLoggedRepository} class serves as the base class for repositories related to logged-in users.
 * It extends {@link UserRepository} and provides {@link MutableLiveData} instances for observing user
 * logout status. Subclasses of {@link AbstractLoggedRepository} are expected to handle user logout logic
 * and communicate changes in user logout status through LiveData objects.
 */
public abstract class AbstractLoggedRepository
        extends UserRepository
        implements ILogged {

    private final MutableLiveData<Boolean> loggedOutLiveData;

    /**
     * Constructs an {@link AbstractLoggedRepository} object with the given application {@link Context} and initializes
     * {@link MutableLiveData} for observing user logout status.
     *
     * @param context The application {@link Context} of the calling component.
     */
    public AbstractLoggedRepository(Context context) {
        super(context);
        loggedOutLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<Boolean> getLoggedOutLiveData() {
        return loggedOutLiveData;
    }

    /**
     * Logs out the currently authenticated user by invoking {@link UserRepository#clearAllUser clearAllUser} method and communicates
     * the logout status via {@link #loggedOutLiveData}.
     */
    @Override
    public void logOut() {
        clearAllUser(context);
        loggedOutLiveData.postValue(true);
    }

}
