package com.example.cineverse.repository.auth.service;

import android.content.Context;

import com.example.cineverse.repository.auth.AbstractAuthRepository;
import com.example.cineverse.service.firebase.FirebaseCallback;
import com.example.cineverse.service.firebase.UserFirebaseDatabaseService;
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
     */
    public void register(String username, String email, String password) {
        if (!UsernameValidator.getInstance().isValid(username)) {
            authCallback.onError(Error.ERROR_INVALID_USERNAME_FORMAT);
        } else if (!EmailValidator.getInstance().isValid(email)) {
            authCallback.onError(Error.ERROR_INVALID_EMAIL_FORMAT);
        } else {
            firebaseSource.isUsernameSaved(username, context,
                    new FirebaseCallback<Boolean>() {
                        @Override
                        public void onCallback(Boolean exist) {
                            if (exist == null) {
                                authCallback.onError(Error.ERROR_AUTHENTICATION_FAILED);
                            } else if (exist) {
                                authCallback.onError(Error.ERROR_USERNAME_ALREADY_EXISTS);
                            } else {
                                firebaseAuth.createUserWithEmailAndPassword(email, password)
                                        .addOnSuccessListener(authResult ->
                                                handleSuccess(authResult, username))
                                        .addOnFailureListener(e -> handleFailure(e));
                            }
                        }

                        @Override
                        public void onNetworkUnavailable() {
                            authCallback.onNetworkError();
                        }
                    });
        }
    }

    /**
     * Handles successful registration by saving user data in Firebase and updating the user live data.
     *
     * @param authResult The successful registration result containing user information.
     * @param username   User's username for registration.
     */
    private void handleSuccess(AuthResult authResult, String username) {
        FirebaseUser firebaseUser = authResult.getUser();
        if (firebaseUser != null) {
            userStorage.register(firebaseUser, username);
        } else {
            handleAuthenticationFailure();
        }
    }

    /**
     * Handles registration failure scenarios by identifying the type of exception.
     *
     * @param exception The exception occurred during the registration process.
     */
    private void handleFailure(Exception exception) {
        if (exception instanceof FirebaseAuthWeakPasswordException) {
            authCallback.onError(Error.ERROR_WEAK_PASSWORD);
        } else if (exception instanceof FirebaseAuthInvalidCredentialsException) {
            authCallback.onError(Error.ERROR_INVALID_EMAIL);
        } else if (exception instanceof FirebaseAuthUserCollisionException) {
            authCallback.onError(Error.ERROR_EMAIL_ALREADY_EXISTS);
        } else if (exception instanceof FirebaseNetworkException) {
            authCallback.onNetworkError();
        } else {
            authCallback.onError(Error.ERROR_INVALID_CREDENTIAL);
        }
    }

}
