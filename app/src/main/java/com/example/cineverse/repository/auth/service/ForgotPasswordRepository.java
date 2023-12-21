package com.example.cineverse.repository.auth.service;

import android.content.Context;

import com.example.cineverse.repository.auth.AbstractAuthRepository;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

import org.apache.commons.validator.routines.EmailValidator;

/**
 * The {@code ForgotPasswordRepository} class extends {@link AbstractAuthRepository} and provides
 * functionality for handling user password reset requests. It validates the user input (email),
 * initiates a password reset operation using Firebase authentication services, and handles various
 * scenarios, including invalid email format, user not found, network issues, and other exceptions.
 * Errors and password reset status are communicated through the provided {@link ErrorAuthCallback}.
 */
public class ForgotPasswordRepository
        extends AbstractAuthRepository {

    /**
     * Constructs a {@link ForgotPasswordRepository} object with the given application {@link Context}.
     *
     * @param context The application {@link Context} of the calling component.
     */
    public ForgotPasswordRepository(Context context) {
        super(context);
    }

    /**
     * Initiates a password reset request for the provided email address. Validates the email format,
     * initiates the password reset operation, and communicates errors or success through the
     * provided {@link ErrorAuthCallback}.
     *
     * @param email    User's email address for password reset.
     */
    public void forgotPassword(String email) {
        if (!EmailValidator.getInstance().isValid(email)) {
            authCallback.onError(Error.ERROR_INVALID_EMAIL_FORMAT);
        } else {
            firebaseAuth.sendPasswordResetEmail(email)
                    .addOnSuccessListener(authResult -> handleSuccess())
                    .addOnFailureListener(this::handleFailure);
        }
    }

    /**
     * Handles the successful completion of the password reset operation.
     */
    private void handleSuccess() {
        authCallback.onError(Error.SUCCESS);
    }

    /**
     * Handles password reset failure scenarios by identifying the type of exception.
     *
     * @param exception The exception occurred during the password reset process.
     */
    private void handleFailure(Exception exception) {
        if (exception instanceof FirebaseAuthInvalidUserException) {
            authCallback.onError(Error.ERROR_NOT_FOUND_DISABLED);
        } else if (exception instanceof FirebaseNetworkException) {
            authCallback.onNetworkError();
        } else {
            authCallback.onError(Error.ERROR_INVALID_CREDENTIAL);
        }
    }

}
