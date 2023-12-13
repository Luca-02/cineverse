package com.example.cineverse.repository.auth.service;

import android.content.Context;

import com.example.cineverse.data.model.User;
import com.example.cineverse.repository.auth.AbstractAuthRepository;
import com.example.cineverse.service.firebase.UserFirebaseDatabaseServices;
import com.example.cineverse.utils.UsernameValidator;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

import org.apache.commons.validator.routines.EmailValidator;

/**
 * The {@link RegisterRepository} class extends {@link AbstractAuthRepository} and provides functionality
 * for user registration. It validates user input, performs registration operations using Firebase authentication
 * services, and handles various registration-related error scenarios. Errors and registration status are
 * communicated through the provided {@link AuthCallback}.
 */
public class RegisterRepository
        extends AbstractAuthRepository {

    /**
     * Constructs a {@code RegisterRepository} object with the given application {@link Context}.
     *
     * @param context The application {@link Context} of the calling component.
     */
    public RegisterRepository(Context context) {
        super(context);
    }

    /**
     * Attempts user registration with the provided username, email, and password. Validates the username
     * and email format, initiates the registration operation, and communicates errors or success through
     * the provided {@link AuthCallback}.
     *
     * @param username  User's username for registration.
     * @param email     User's email address for registration.
     * @param password  User's password for registration.
     * @param callback  Callback to handle authentication errors and status.
     */
    public void register(String username, String email, String password, AuthCallback callback) {
        if (!UsernameValidator.getInstance().isValid(username)) {
            callback.onError(Error.ERROR_INVALID_USERNAME_FORMAT);
        } else if (!EmailValidator.getInstance().isValid(email)) {
            callback.onError(Error.ERROR_INVALID_EMAIL_FORMAT);
        } else {
            userStorage.getFirebaseSource().isUsernameSaved(username, context,
                    new UserFirebaseDatabaseServices.Callback<Boolean>() {
                        @Override
                        public void onCallback(Boolean exist) {
                            if (exist == null) {
                                callback.onError(Error.ERROR_AUTHENTICATION_FAILED);
                            } else if (exist) {
                                callback.onError(Error.ERROR_USERNAME_ALREADY_EXISTS);
                            } else {
                                firebaseAuth.createUserWithEmailAndPassword(email, password)
                                        .addOnSuccessListener(authResult ->
                                                handleSuccess(authResult, username, callback))
                                        .addOnFailureListener(e -> handleFailure(e, callback));
                            }
                        }

                        @Override
                        public void onNetworkUnavailable() {
                            callback.onNetworkError();
                        }
                    });
        }
    }

    /**
     * Handles successful registration by saving user data in Firebase and updating the user live data.
     *
     * @param authResult The successful registration result containing user information.
     * @param username   User's username for registration.
     * @param callback   Callback to handle authentication errors and status.
     */
    private void handleSuccess(AuthResult authResult, String username, AuthCallback callback) {
        FirebaseUser firebaseUser = authResult.getUser();
        if (firebaseUser != null) {
            userStorage.registerUser(firebaseUser, username,
                    new UserFirebaseDatabaseServices.Callback<User>() {
                        @Override
                        public void onCallback(User user) {
                            handleUserAuthentication(user, callback);
                        }

                        @Override
                        public void onNetworkUnavailable() {
                            handleAuthenticationNetworkFailure(callback);
                        }
                    });
        } else {
            handleAuthenticationFailure(callback);
        }
    }

    /**
     * Handles registration failure scenarios by identifying the type of exception.
     *
     * @param exception The exception occurred during the registration process.
     * @param callback  Callback to handle authentication errors and status.
     */
    private void handleFailure(Exception exception, ErrorAuthCallback callback) {
        if (exception instanceof FirebaseAuthWeakPasswordException) {
            callback.onError(Error.ERROR_WEAK_PASSWORD);
        } else if (exception instanceof FirebaseAuthInvalidCredentialsException) {
            callback.onError(Error.ERROR_INVALID_EMAIL);
        } else if (exception instanceof FirebaseAuthUserCollisionException) {
            callback.onError(Error.ERROR_EMAIL_ALREADY_EXISTS);
        } else if (exception instanceof FirebaseNetworkException) {
            callback.onNetworkError();
        } else {
            callback.onError(Error.ERROR_INVALID_CREDENTIAL);
        }
    }

}
