package com.example.cineverse.Repository;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.example.cineverse.Interface.ILogged;
import com.google.firebase.auth.FirebaseAuth;

/**
 * The AbstractLoggedRepository class serves as the base class for repositories related to logged-in users.
 * It extends AbstractAuthRepository and provides MutableLiveData instances for observing user logout status.
 * Subclasses of AbstractLoggedRepository are expected to handle user logout logic and communicate changes
 * in user logout status through LiveData objects.
 */
public abstract class AbstractLoggedRepository extends AbstractAuthRepository
        implements ILogged {

    private final MutableLiveData<Boolean> loggedOutLiveData;

    /**
     * Constructs an AbstractLoggedRepository object with the given Application context and initializes
     * MutableLiveData for observing user logout status.
     *
     * @param application The Application context of the calling component.
     */
    public AbstractLoggedRepository(Application application) {
        super(application);
        loggedOutLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<Boolean> getLoggedOutLiveData() {
        return loggedOutLiveData;
    }

    /**
     * Logs out the currently authenticated user by invoking Firebase signOut() method and
     * communicates the logout status via loggedOutLiveData.
     */
    @Override
    public void logOut() {
        FirebaseAuth.getInstance().signOut();
        loggedOutLiveData.postValue(true);
    }

}
