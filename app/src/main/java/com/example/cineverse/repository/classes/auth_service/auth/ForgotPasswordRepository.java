package com.example.cineverse.repository.classes.auth_service.auth;

import android.content.Context;

import com.example.cineverse.repository.classes.auth_service.AbstractAuthServiceRepository;
import com.example.cineverse.repository.interfaces.auth.IForgotPassword;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

import org.apache.commons.validator.routines.EmailValidator;

/**
 * The {@code ForgotPasswordRepository} class extends {@link AbstractAuthServiceRepository} and provides
 * functionality for handling user password reset requests. It validates the user input (email),
 * initiates a password reset operation using Firebase authentication services, and handles various
 * scenarios, including invalid email format, user not found, network issues, and other exceptions.
 * Errors and password reset status are communicated via {@link androidx.lifecycle.MutableLiveData} for observation and user feedback.
 */
public class ForgotPasswordRepository
        extends AbstractAuthServiceRepository
        implements IForgotPassword {

    /**
     * Constructs a {@code ForgotPasswordRepository} object with the given Application context.
     *
     * @param context The Application context of the calling component.
     */
    public ForgotPasswordRepository(Context context) {
        super(context);
    }

    /**
     * Initiates a password reset request for the provided email address. Validates the email format,
     * initiates the password reset operation, and communicates errors or success through errorLiveData.
     *
     * @param email User's email address for password reset.
     */
    @Override
    public void forgotPassword(String email) {
        if (!EmailValidator.getInstance().isValid(email)) {
            errorLiveData.postValue(Error.ERROR_INVALID_EMAIL_FORMAT);
        } else {
            firebaseAuth.sendPasswordResetEmail(email)
                    .addOnSuccessListener(authResult -> handleSuccess())
                    .addOnFailureListener(this::handleFailure);
        }
    }

    /**
     * Handles successful password reset by posting {@link Error#SUCCESS} to {@code errorLiveData}.
     */
    private void handleSuccess() {
        errorLiveData.postValue(Error.SUCCESS);
    }

    /**
     * Handles password reset failure scenarios by identifying the type of exception and
     * posting appropriate errors for user feedback through {@code errorLiveData}.
     *
     * @param exception The exception occurred during the password reset process.
     */
    private void handleFailure(Exception exception) {
        if (exception instanceof FirebaseAuthInvalidUserException) {
            errorLiveData.postValue(Error.ERROR_NOT_FOUND_DISABLED);
        } else if (exception instanceof FirebaseNetworkException) {
            setNetworkErrorLiveData(true);
        } else {
            errorLiveData.postValue(Error.ERROR_INVALID_CREDENTIAL);
        }
    }

}
