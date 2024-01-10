package com.example.cineverse.repository.auth;

import android.content.Context;

import com.example.cineverse.R;
import com.example.cineverse.data.model.User;
import com.example.cineverse.data.source.user.UserFirebaseSource;
import com.example.cineverse.data.source.user.UserStorageManagerSource;
import com.example.cineverse.repository.UserRepository;
import com.example.cineverse.service.NetworkCallback;
import com.example.cineverse.service.firebase.FirebaseCallback;

/**
 * The {@link AbstractAuthRepository} class extends {@link UserRepository} and serves as
 * a base class for authentication service repositories. It provides {@link ErrorAuthErrorCallback}
 * and {@link AuthErrorCallback} to communicate the authentication error and status to the caller.
 */
public abstract class AbstractAuthRepository
        extends UserRepository
        implements FirebaseCallback<User> {

    /**
     * {@link Error Error} enum representing possible authentication errors and associated string resources for error messages.
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

    protected final UserFirebaseSource firebaseSource;
    protected final UserStorageManagerSource userStorage;
    protected AuthErrorCallback authCallback;

    /**
     * Constructs an {@link AbstractAuthRepository} object with the given application.
     *
     * @param context The application {@link Context} of the calling component.
     */
    public AbstractAuthRepository(Context context) {
        super(context);
        firebaseSource = new UserFirebaseSource(context);
        userStorage = new UserStorageManagerSource(context, localSource, firebaseSource, this);
    }

    public void setAuthCallback(AuthErrorCallback authCallback) {
        this.authCallback = authCallback;
    }

    /**
     * Handles authentication failure by signing out from Firebase.
     */
    protected void handleAuthenticationFailure() {
        firebaseAuth.signOut();
        authCallback.onError(Error.ERROR_AUTHENTICATION_FAILED);
    }

    /**
     * Handles the authentication process for the user.
     *
     * @param user The authenticated user.
     */
    @Override
    public void onCallback(User user) {
        if (user != null && authCallback != null) {
            authCallback.onUserAuthentication(user);
        } else {
            handleAuthenticationFailure();
        }
    }

    /**
     * Handles authentication network failure by signing out from Firebase.
     */
    @Override
    public void onNetworkUnavailable() {
        if (authCallback != null) {
            firebaseAuth.signOut();
            authCallback.onNetworkUnavailable();
        }
    }

    /**
     * Callback interface for handling authentication errors.
     */
    public interface ErrorAuthErrorCallback extends NetworkCallback {
        /**
         * Invoked when an authentication {@link Error} occurs.
         *
         * @param error The authentication {@link Error}.
         */
        void onError(Error error);
    }

    /**
     * Callback interface for handling authentication success and errors.
     */
    public interface AuthErrorCallback extends ErrorAuthErrorCallback {
        /**
         * Invoked when user authentication is successful.
         *
         * @param user The authenticated {@link User}.
         */
        void onUserAuthentication(User user);
    }

}
