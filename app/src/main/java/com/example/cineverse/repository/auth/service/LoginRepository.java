package com.example.cineverse.repository.auth.service;

import android.content.Context;

import com.example.cineverse.repository.auth.AbstractAuthRepository;
import com.example.cineverse.data.source.user.UserCallback;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

import org.apache.commons.validator.routines.EmailValidator;

/**
 * The {@link LoginRepository} class extends {@link AbstractAuthRepository} and provides authentication functionality
 * for user login. It validates the user input, performs login operations using Firebase
 * authentication services, and handles success and failure scenarios. Errors and authentication
 * status are communicated through the provided {@link AuthErrorCallback}.
 */
public class LoginRepository
        extends AbstractAuthRepository {

    /**
     * Constructs a {@link LoginRepository} object with the given application {@link Context}.
     *
     * @param context The Application context of the calling component.
     */
    public LoginRepository(Context context) {
        super(context);
    }

    /**
     * Performs user login with the provided account and password. Validates the account format,
     * initiates the login operation, and communicates errors or success through the provided
     * {@link AuthErrorCallback}.
     *
     * @param account   User's account (username or email) for login.
     * @param password  User's password for login.
     */
    public void login(String account, String password) {
        if (!EmailValidator.getInstance().isValid(account)) {
            if (account.contains("@")) {
                authCallback.onError(Error.ERROR_INVALID_EMAIL_FORMAT);
            } else {
                firebaseSource.getEmailFromUsername(context, account,
                        new UserCallback<String>() {
                            @Override
                            public void onCallback(String email) {
                                if (email != null) {
                                    signInWithCredentials(email, password);
                                } else {
                                    authCallback.onError(Error.ERROR_NOT_FOUND_DISABLED);
                                }
                            }

                            @Override
                            public void onNetworkUnavailable() {
                                authCallback.onNetworkUnavailable();
                            }
                        });
            }
        } else {
            signInWithCredentials(account, password);
        }
    }

    private void signInWithCredentials(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(this::handleSuccess)
                .addOnFailureListener(this::handleFailure);
    }

    /**
     * Handles successful authentication by saving user data locally.
     *
     * @param authResult The successful authentication result containing user information.
     */
    private void handleSuccess(AuthResult authResult) {
        FirebaseUser firebaseUser = authResult.getUser();
        if (firebaseUser != null) {
            userStorage.login(firebaseUser.getUid());
        } else {
            handleAuthenticationFailure();
        }
    }

    /**
     * Handles authentication failure scenarios by identifying the type of exception.
     *
     * @param exception The exception occurred during the authentication process.
     */
    private void handleFailure(Exception exception) {
        if (exception instanceof FirebaseAuthInvalidUserException) {
            authCallback.onError(Error.ERROR_NOT_FOUND_DISABLED);
        } else if (exception instanceof FirebaseNetworkException) {
            authCallback.onNetworkUnavailable();
        } else {
            authCallback.onError(Error.ERROR_WRONG_PASSWORD);
        }
    }

}
