package com.example.cineverse.repository.classes.auth_service;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.example.cineverse.R;
import com.example.cineverse.repository.classes.AbstractAuthRepository;
import com.example.cineverse.repository.interfaces.IAuthServices;

/**
 * The {@code AbstractAuthServiceRepository} class extends {@link AbstractAuthRepository} and serves as
 * a base class for authentication service repositories. It provides {@link MutableLiveData} for observing
 * authentication-related errors.
 */
public abstract class AbstractAuthServiceRepository
        extends AbstractAuthRepository
        implements IAuthServices {

    /**
     * Enum representing possible authentication errors and associated string resources for error messages.
     */
    public enum Error {
        SUCCESS(null),
        ERROR_INVALID_EMAIL(R.string.invalid_email),
        ERROR_INVALID_USERNAME_FORMAT(R.string.invalid_username_format),
        ERROR_INVALID_EMAIL_FORMAT(R.string.invalid_email_format),
        ERROR_NOT_FOUND_DISABLED(R.string.user_not_found_disabled),
        ERROR_USERNAME_ALREADY_EXISTS(R.string.username_already_exist),
        ERROR_EMAIL_ALREADY_EXISTS(R.string.email_already_exist),
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

    protected final MutableLiveData<Error> errorLiveData;

    /**
     * Constructs an {@code AbstractAuthServiceRepository} object with the given Application context and
     * initializes MutableLiveData for observing authentication-related errors.
     *
     * @param context The Application context of the calling component.
     */
    public AbstractAuthServiceRepository(Context context) {
        super(context);
        errorLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<Error> getErrorLiveData() {
        return errorLiveData;
    }

    /**
     * Handles authentication failure by signing out from Firebase and updating the error LiveData.
     */
    protected void handleAuthenticationFailure() {
        firebaseAuth.signOut();
        errorLiveData.postValue(Error.ERROR_AUTHENTICATION_FAILED);
    }

    /**
     * Handles authentication network failure by signing out from Firebase and updating the network error LiveData.
     */
    protected void handleAuthenticationNetworkFailure() {
        firebaseAuth.signOut();
        setNetworkErrorLiveData(true);
    }

}
