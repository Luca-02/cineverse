package com.example.cineverse.repository.classes.auth_service.auth;

import android.content.Context;

import com.example.cineverse.data.model.user.User;
import com.example.cineverse.data.query.user.UserFirebaseQuery;
import com.example.cineverse.data.service.firebase.UserFirebaseDatabaseServices;
import com.example.cineverse.repository.classes.auth_service.AbstractAuthServiceRepository;
import com.example.cineverse.repository.interfaces.auth.IRegister;
import com.example.cineverse.utils.validator.UsernameValidator;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

import org.apache.commons.validator.routines.EmailValidator;

/**
 * The {@code RegisterRepository} class extends {@link AbstractAuthServiceRepository} and provides functionality
 * for user registration. It validates user input, performs registration operations using Firebase authentication
 * services, and handles various registration-related error scenarios. Errors and registration status are
 * communicated via MutableLiveData for observation and user feedback.
 */
public class RegisterRepository
        extends AbstractAuthServiceRepository
        implements IRegister {

    /**
     * Constructs a {@code RegisterRepository} object with the given Application context.
     *
     * @param context The Application context of the calling component.
     */
    public RegisterRepository(Context context) {
        super(context);
    }

    /**
     * Attempts user registration with the provided username, email, and password. Validates the username
     * and email format, initiates the registration operation, and communicates errors or success through
     * {@code errorLiveData}.
     *
     * @param username  User's username registration.
     * @param email     User's email address for registration.
     * @param password  User's password for registration.
     */
    @Override
    public void register(String username, String email, String password) {
        if (!UsernameValidator.getInstance().isValid(username)) {
            errorLiveData.postValue(Error.ERROR_INVALID_USERNAME_FORMAT);
        } else if (!EmailValidator.getInstance().isValid(email)) {
            errorLiveData.postValue(Error.ERROR_INVALID_EMAIL_FORMAT);
        } else {
            UserFirebaseQuery.isUsernameSaved(username, context,
                    new UserFirebaseDatabaseServices.Callback<Boolean>() {
                        @Override
                        public void onCallback(Boolean exist) {
                            if (exist == null) {
                                errorLiveData.postValue(Error.ERROR_AUTHENTICATION_FAILED);
                            } else if (exist) {
                                errorLiveData.postValue(Error.ERROR_USERNAME_ALREADY_EXISTS);
                            } else {
                                firebaseAuth.createUserWithEmailAndPassword(email, password)
                                        .addOnSuccessListener(authResult -> handleSuccess(authResult, username))
                                        .addOnFailureListener(exception -> handleFailure(exception));
                            }
                        }

                        @Override
                        public void onNetworkUnavailable() {
                            setNetworkErrorLiveData(true);
                        }
                    });
        }
    }

    /**
     * Handles successful registration by saving user data in Firebase and updating the user live data.
     *
     * @param authResult    The successful registration result containing user information.
     * @param username      User's username registration.
     */
    private void handleSuccess(AuthResult authResult, String username) {
        FirebaseUser firebaseUser = authResult.getUser();
        if (firebaseUser != null) {
            userStorage.registerUser(firebaseUser, username,
                    new UserFirebaseDatabaseServices.Callback<User>() {
                        @Override
                        public void onCallback(User user) {
                            if (user != null) {
                                setUserLiveData(user);
                            } else {
                                handleAuthenticationFailure();
                            }
                        }

                        @Override
                        public void onNetworkUnavailable() {
                            handleAuthenticationNetworkFailure();
                        }
                    });
        } else {
            handleAuthenticationFailure();
        }
    }

    /**
     * Handles registration failure scenarios by identifying the type of exception and
     * posting appropriate errors for user feedback through {@code errorLiveData}.
     *
     * @param exception The exception occurred during the registration process.
     */
    private void handleFailure(Exception exception) {
        if (exception instanceof FirebaseAuthWeakPasswordException) {
            errorLiveData.postValue(Error.ERROR_WEAK_PASSWORD);
        } else if (exception instanceof FirebaseAuthInvalidCredentialsException) {
            errorLiveData.postValue(Error.ERROR_INVALID_EMAIL);
        } else if (exception instanceof FirebaseAuthUserCollisionException) {
            errorLiveData.postValue(Error.ERROR_EMAIL_ALREADY_EXISTS);
        } else if (exception instanceof FirebaseNetworkException) {
            setNetworkErrorLiveData(true);
        } else {
            errorLiveData.postValue(Error.ERROR_INVALID_CREDENTIAL);
        }
    }

}
