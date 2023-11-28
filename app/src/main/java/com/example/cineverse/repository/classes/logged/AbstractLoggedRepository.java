package com.example.cineverse.repository.classes.logged;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.example.cineverse.repository.classes.AbstractAuthRepository;
import com.example.cineverse.repository.interfaces.ILogged;

/**
 * The {@code AbstractLoggedRepository} class serves as the base class for repositories related to logged-in users.
 * It extends {@link AbstractAuthRepository} and provides {@link MutableLiveData} instances for observing user
 * logout status. Subclasses of {@code AbstractLoggedRepository} are expected to handle user logout logic
 * and communicate changes in user logout status through LiveData objects.
 */
public abstract class AbstractLoggedRepository
        extends AbstractAuthRepository
        implements ILogged {

    private final MutableLiveData<Boolean> loggedOutLiveData;

    /**
     * Constructs an {@code AbstractLoggedRepository} object with the given Application context and initializes
     * MutableLiveData for observing user logout status.
     *
     * @param context The Application context of the calling component.
     */
    public AbstractLoggedRepository(Context context) {
        super(context);
        loggedOutLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<Boolean> getLoggedOutLiveData() {
        return loggedOutLiveData;
    }

    /**
     * Logs out the currently authenticated user by invoking Firebase's signOut() method and communicates
     * the logout status via {@code loggedOutLiveData}.
     */
    @Override
    public void logOut() {
        clearAllUser(context);
        loggedOutLiveData.postValue(true);
    }

}
