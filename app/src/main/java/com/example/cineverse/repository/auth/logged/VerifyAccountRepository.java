package com.example.cineverse.repository.auth.logged;

import android.content.Context;

import com.example.cineverse.repository.auth.AbstractLoggedRepository;
import com.example.cineverse.service.NetworkCallback;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.FirebaseUser;

/**
 * The {@link VerifyAccountRepository} class extends {@link AbstractLoggedRepository} and provides functionality
 * for verifying user email addresses. It allows users to request email verification, reload their user data,
 * and check whether their email is verified or not. It provides {@link SentEmailErrorCallback}
 * and {@link ReloadUserErrorCallback} to communicate the operation status to the caller.
 */
public class VerifyAccountRepository
        extends AbstractLoggedRepository {

    private SentEmailErrorCallback sentEmailCallback;
    private ReloadUserErrorCallback reloadUserCallback;

    /**
     * Constructs a {@link VerifyAccountRepository} object with the given application {@link Context}.
     *
     * @param context The application {@link Context} of the calling component.
     */
    public VerifyAccountRepository(Context context) {
        super(context);
    }

    public void setSentEmailCallback(SentEmailErrorCallback sentEmailCallback) {
        this.sentEmailCallback = sentEmailCallback;
    }

    public void setReloadUserCallback(ReloadUserErrorCallback reloadUserCallback) {
        this.reloadUserCallback = reloadUserCallback;
    }

    /**
     * Initiates a request to send email verification to the user's email address. Reloading user
     * data is performed to ensure up-to-date information before sending the verification email.
     */
    public void sendEmailVerification() {
        FirebaseUser user = getCurrentFirebaseUser();
        if (user != null) {
            user.reload()
                    .addOnSuccessListener(unused -> handleReloadSendEmailSuccess())
                    .addOnFailureListener(this::handleReloadSendEmailFailure);
        } else {
            sentEmailCallback.onEmailSent(null);
        }
    }

    /**
     * Handles the successful reload and subsequent email verification request.
     */
    private void handleReloadSendEmailSuccess() {
        FirebaseUser reloadedUser = getCurrentFirebaseUser();
        if (reloadedUser != null) {
            reloadedUser.sendEmailVerification()
                    .addOnSuccessListener(unused -> sentEmailCallback.onEmailSent(true))
                    .addOnFailureListener(e -> sentEmailCallback.onEmailSent(false));
        } else {
            sentEmailCallback.onEmailSent(null);
        }
    }

    /**
     * Handles failures during the reload operation and subsequent email verification request.
     *
     * @param exception The exception occurred during the reload operation.
     */
    private void handleReloadSendEmailFailure(Exception exception) {
        if (exception instanceof FirebaseNetworkException) {
            sentEmailCallback.onNetworkUnavailable();
        } else {
            sentEmailCallback.onEmailSent(null);
        }
    }

    /**
     * Reloads the user's data and provides the email verification status through {@link #reloadUserCallback}.
     */
    public void reloadUser() {
        final FirebaseUser user = getCurrentFirebaseUser();
        if (user != null) {
            user.reload()
                    .addOnSuccessListener(unused -> handleReloadSuccess())
                    .addOnFailureListener(this::handleReloadFailure);
        } else {
            reloadUserCallback.onReloadUser(null);
        }
    }

    /**
     * Handles successful user data reload and provides the email verification status through {@link #reloadUserCallback}.
     */
    private void handleReloadSuccess() {
        reloadUserCallback.onReloadUser(isEmailVerified());
    }

    /**
     * Handles reload operation failures, identifies the type of exception, and communicates
     * appropriate errors or network issues through {@link #reloadUserCallback}.
     *
     * @param exception The exception occurred during the reload operation.
     */
    private void handleReloadFailure(Exception exception) {
        if (exception instanceof FirebaseNetworkException) {
            reloadUserCallback.onNetworkUnavailable();
        } else {
            reloadUserCallback.onReloadUser(null);
        }
    }

    /**
     * Callback interface for handling the result of the email verification request.
     */
    public interface SentEmailErrorCallback extends NetworkCallback {
        /**
         * Invoked when the email verification request is completed.
         *
         * @param isSent A {@link Boolean} indicating whether the email was successfully sent:
         *  <ul>
         *      <li> {@code true} if the email was sent successfully. </li>
         *      <li> {@code false} if the email sending failed. </li>
         *      <li> {@code null} if an error occurred during the process. </li>
         *  </ul>
         */
        void onEmailSent(Boolean isSent);
    }

    /**
     * Callback interface for handling the result of the user data reload operation.
     */
    public interface ReloadUserErrorCallback extends NetworkCallback {
        /**
         * Invoked when the user data reload operation is completed.
         *
         * @param isVerified A {@link Boolean} indicating whether the user's email is verified:
         *  <ul>
         *      <li> {@code true} if the email is verified. </li>
         *      <li> {@code false} if the email is not verified. </li>
         *      <li> {@code null} if an error occurred during the process. </li>
         *  </ul>
         */
        void onReloadUser(Boolean isVerified);
    }

}
