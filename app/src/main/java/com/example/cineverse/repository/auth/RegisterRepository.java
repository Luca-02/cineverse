package com.example.cineverse.repository.auth;

import android.app.Application;

import com.example.cineverse.interfaces.auth.IRegister;
import com.example.cineverse.repository.AbstractAuthServiceRepository;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

import org.apache.commons.validator.routines.EmailValidator;

/**
 * The RegisterRepository class extends AbstractAuthServiceRepository and provides functionality for user registration.
 * It validates user input, performs registration operations using Firebase authentication services,
 * and handles various registration-related error scenarios. Errors and registration status are
 * communicated via MutableLiveData for observation and user feedback.
 */
public class RegisterRepository
        extends AbstractAuthServiceRepository
        implements IRegister {

    /**
     * Constructs a RegisterRepository object with the given Application context.
     *
     * @param application The Application context of the calling component.
     */
    public RegisterRepository(Application application) {
        super(application);
    }

    /**
     * Attempts user registration with the provided email and password. Validates the email format,
     * initiates the registration operation, and communicates errors or success through errorLiveData.
     *
     * @param email    User's email address for registration.
     * @param password User's password for registration.
     */
    @Override
    public void register(String email, String password) {
        if (!EmailValidator.getInstance().isValid(email)) {
            errorLiveData.postValue(Error.ERROR_INVALID_EMAIL_FORMAT);
        } else {
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnSuccessListener(this::handleSuccess)
                    .addOnFailureListener(this::handleFailure);
        }
    }

    /**
     * Handles successful registration by updating the user live data.
     *
     * @param authResult The successful registration result containing user information.
     */
    private void handleSuccess(AuthResult authResult) {
        setUserLiveData(authResult.getUser());
    }

    /**
     * Handles registration failure scenarios by identifying the type of exception and
     * posting appropriate errors for user feedback through errorLiveData.
     *
     * @param exception The exception occurred during the registration process.
     */
    private void handleFailure(Exception exception) {
        if (exception instanceof FirebaseAuthWeakPasswordException) {
            errorLiveData.postValue(Error.ERROR_WEAK_PASSWORD);
        } else if (exception instanceof FirebaseAuthInvalidCredentialsException) {
            errorLiveData.postValue(Error.ERROR_INVALID_EMAIL);
        } else if (exception instanceof FirebaseAuthUserCollisionException) {
            errorLiveData.postValue(Error.ERROR_ALREADY_EXISTS);
        } else if (exception instanceof FirebaseNetworkException) {
            setNetworkErrorLiveData(true);
        } else {
            errorLiveData.postValue(Error.ERROR_INVALID_CREDENTIAL);
        }
    }

}
