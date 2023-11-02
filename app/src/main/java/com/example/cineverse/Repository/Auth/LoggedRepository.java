package com.example.cineverse.Repository.Auth;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.example.cineverse.Repository.AuthRepository;
import com.google.firebase.auth.FirebaseAuth;

/**
 * The LoggedRepository class extends AuthRepository and provides functionality for user logout.
 * It allows users to log out of the application, effectively ending their session and clearing
 * the current authentication state. The class communicates the logout status via MutableLiveData
 * for observation and handling user logout events.
 */
public class LoggedRepository extends AuthRepository {

    private final MutableLiveData<Boolean> loggedOutLiveData;

    /**
     * Constructs a LoggedRepository object with the given Application context and initializes
     * MutableLiveData for observing user logout status.
     *
     * @param application The Application context of the calling component.
     */
    public LoggedRepository(Application application) {
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
    public void logOut() {
        FirebaseAuth.getInstance().signOut();
        loggedOutLiveData.postValue(true);
    }

}
