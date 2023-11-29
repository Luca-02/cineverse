package com.example.cineverse.repository.classes.auth;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.example.cineverse.R;
import com.example.cineverse.data.model.user.User;
import com.example.cineverse.repository.classes.UserRepository;
import com.example.cineverse.repository.interfaces.auth.IAuth;

/**
 * The {@link AbstractAuthRepository} class extends {@link UserRepository} and serves as
 * a base class for authentication service repositories. It provides {@link MutableLiveData} for observing
 * authentication-related errors.
 */
public abstract class AbstractAuthRepository
        extends UserRepository
        implements IAuth {

    protected final MutableLiveData<Error> errorLiveData;

    /**
     * Constructs an {@link AbstractAuthRepository} object with the given application {@link Context} and
     * initializes {@link MutableLiveData} for observing authentication-related errors.
     *
     * @param context The application {@link Context} of the calling component.
     */
    public AbstractAuthRepository(Context context) {
        super(context);
        errorLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<Error> getErrorLiveData() {
        return errorLiveData;
    }

    /**
     * Handles the user authentication.
     */
    protected void handleUserAuthentication(User user) {
        if (user != null) {
            setUserLiveData(user);
        } else {
            handleAuthenticationFailure();
        }
    }

    /**
     * Handles authentication failure by signing out from Firebase and updating the error {@link MutableLiveData}.
     */
    protected void handleAuthenticationFailure() {
        firebaseAuth.signOut();
        errorLiveData.postValue(Error.ERROR_AUTHENTICATION_FAILED);
    }

    /**
     * Handles authentication network failure by signing out from Firebase and updating the network error {@link MutableLiveData}.
     */
    protected void handleAuthenticationNetworkFailure() {
        firebaseAuth.signOut();
        setNetworkErrorLiveData(true);
    }

}
