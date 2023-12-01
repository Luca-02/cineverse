package com.example.cineverse.repository.auth.service;

import android.content.Context;

import com.example.cineverse.data.model.user.User;
import com.example.cineverse.data.service.firebase.UserFirebaseDatabaseServices;
import com.example.cineverse.repository.auth.AbstractAuthRepository;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

import org.apache.commons.validator.routines.EmailValidator;

/**
 * The {@link LoginRepository} class extends {@link AbstractAuthRepository} and provides authentication functionality
 * for user login. It validates the user input, performs login operations using Firebase
 * authentication services, and handles success and failure scenarios. Errors and authentication
 * status are communicated through the provided {@link AuthCallback}.
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
     * {@link AuthCallback}.
     *
     * @param account   User's account (username or email) for login.
     * @param password  User's password for login.
     * @param callback  Callback to handle authentication errors and status.
     */
    public void login(String account, String password, AuthCallback callback) {
        if (!EmailValidator.getInstance().isValid(account)) {
            if (account.contains("@")) {
                callback.onError(Error.ERROR_INVALID_EMAIL_FORMAT);
            } else {
                userStorage.getFirebaseSource().getEmailFromUsername(account, context,
                        new UserFirebaseDatabaseServices.Callback<String>() {
                            @Override
                            public void onCallback(String email) {
                                if (email != null) {
                                    signInWithCredentials(email, password, callback);
                                } else {
                                    callback.onError(Error.ERROR_NOT_FOUND_DISABLED);
                                }
                            }

                            @Override
                            public void onNetworkUnavailable() {
                                callback.onNetworkError();
                            }
                        });
            }
        } else {
            signInWithCredentials(account, password, callback);
        }
    }

    private void signInWithCredentials(String email, String password, AuthCallback callback) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> handleSuccess(authResult, callback))
                .addOnFailureListener(e -> handleFailure(e, callback));
    }

    /**
     * Handles successful authentication by saving user data locally.
     *
     * @param authResult The successful authentication result containing user information.
     * @param callback   Callback to handle authentication errors and status.
     */
    private void handleSuccess(AuthResult authResult, AuthCallback callback) {
        FirebaseUser firebaseUser = authResult.getUser();
        if (firebaseUser != null) {
            userStorage.loginUser(firebaseUser.getUid(),
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
     * Handles authentication failure scenarios by identifying the type of exception.
     *
     * @param exception The exception occurred during the authentication process.
     * @param callback  Callback to handle authentication errors and status.
     */
    private void handleFailure(Exception exception, ErrorAuthCallback callback) {
        if (exception instanceof FirebaseAuthInvalidUserException) {
            callback.onError(Error.ERROR_NOT_FOUND_DISABLED);
        } else if (exception instanceof FirebaseNetworkException) {
            callback.onNetworkError();
        } else {
            callback.onError(Error.ERROR_WRONG_PASSWORD);
        }
    }

}
