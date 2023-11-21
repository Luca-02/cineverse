package com.example.cineverse.repository;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.example.cineverse.interfaces.IAuth;
import com.example.cineverse.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * AbstractAuthRepository is a base class that provides authentication-related functionality using Firebase
 * authentication services. It encapsulates methods for handling user authentication, managing
 * user data, and checking network connectivity. This class is meant to be extended for specific
 * authentication methods such as email/password, Google Sign-In, etc.
 * This class maintains MutableLiveData objects for observing changes in user data and network
 * connectivity status.
 */
public abstract class AbstractAuthRepository
        implements IAuth {

    /**
     * Enum representing possible authentication errors and associated string resources for error messages.
     */
    public enum Error {
        SUCCESS(null),
        ERROR_INVALID_EMAIL(R.string.invalid_email),
        ERROR_INVALID_EMAIL_FORMAT(R.string.invalid_email_format),
        ERROR_NOT_FOUND_DISABLED(R.string.user_not_found_disabled),
        ERROR_ALREADY_EXISTS(R.string.user_already_exist),
        ERROR_WEAK_PASSWORD(R.string.weak_password),
        ERROR_WRONG_PASSWORD(R.string.wrong_password),
        ERROR_INVALID_CREDENTIAL(R.string.invalid_credentials),
        ERROR_GOOGLE_SIGNIN_FAILED(R.string.google_signin_failed),
        ERROR_AUTHENTICATION_FAILED(R.string.authentication_failed);

        private final Integer stringId;

        Error(Integer stringId) {
            this.stringId = stringId;
        }

        public boolean isSuccess() {
            return stringId == null;
        }

        public int getError() {
            return stringId;
        }
    }

    // Firebase authentication instance.
    protected static FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    protected final Application application;
    private final MutableLiveData<FirebaseUser> userLiveData;
    private final MutableLiveData<Boolean> networkErrorLiveData;

    /**
     * Constructs an AbstractAuthRepository object with the given Application context. Initializes
     * MutableLiveData objects for user data and network connectivity.
     *
     * @param application The Application context of the calling component.
     */
    public AbstractAuthRepository(Application application) {
        this.application = application;
        userLiveData = new MutableLiveData<>();
        networkErrorLiveData = new MutableLiveData<>();

        // Check if there is a currently authenticated user and update userLiveData if available.
        FirebaseUser currentUser = getCurrentUser();
        if (currentUser != null) {
            userLiveData.postValue(currentUser);
        }
    }

    public MutableLiveData<FirebaseUser> getUserLiveData() {
        return userLiveData;
    }

    public void setUserLiveData(FirebaseUser firebaseUser) {
        userLiveData.postValue(firebaseUser);
    }

    public MutableLiveData<Boolean> getNetworkErrorLiveData() {
        return networkErrorLiveData;
    }

    public void setNetworkErrorLiveData(boolean bool) {
        networkErrorLiveData.postValue(bool);
    }

    /**
     * Returns the currently authenticated FirebaseUser object.
     *
     * @return FirebaseUser representing the currently authenticated user, or null if not authenticated.
     */
    public static FirebaseUser getCurrentUser() {
        return firebaseAuth.getCurrentUser();
    }

    /**
     * Checks if the currently authenticated user's email is verified.
     *
     * @return true if the user's email is verified, false otherwise.
     */
    public static boolean isEmailVerified() {
        FirebaseUser user = getCurrentUser();
        return user != null && user.isEmailVerified();
    }

}
