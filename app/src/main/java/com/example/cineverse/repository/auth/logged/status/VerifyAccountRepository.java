package com.example.cineverse.repository.auth.logged.status;

import android.content.Context;

import com.example.cineverse.repository.auth.logged.AbstractLoggedRepository;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.FirebaseUser;

/**
 * The {@link VerifyAccountRepository} class extends {@link AbstractLoggedRepository} and provides functionality
 * for verifying user email addresses. It allows users to request email verification, reload their user data,
 * and check whether their email is verified or not. It provides {@link SentEmailCallback}
 *  * and {@link ReloadUserCallback} to communicate the operation status to the caller.
 */
public class VerifyAccountRepository
        extends AbstractLoggedRepository {

    /**
     * Constructs a {@link VerifyAccountRepository} object with the given application {@link Context}.
     *
     * @param context The application {@link Context} of the calling component.
     */
    public VerifyAccountRepository(Context context) {
        super(context);
    }

    /**
     * Initiates a request to send email verification to the user's email address. Reloading user
     * data is performed to ensure up-to-date information before sending the verification email.
     *
     * @param callback Callback to handle the result of the email verification request.
     */
    public void sendEmailVerification(SentEmailCallback callback) {
        FirebaseUser user = getCurrentFirebaseUser();
        if (user != null) {
            user.reload()
                    .addOnSuccessListener(unused -> handleReloadSendEmailSuccess(callback))
                    .addOnFailureListener(e -> handleReloadSendEmailFailure(e, callback));
        } else {
            callback.onEmailSent(null);
        }
    }

    /**
     * Handles the successful reload and subsequent email verification request.
     *
     * @param callback Callback to handle the result of the email verification request.
     */
    private void handleReloadSendEmailSuccess(SentEmailCallback callback) {
        FirebaseUser reloadedUser = getCurrentFirebaseUser();
        if (reloadedUser != null) {
            reloadedUser.sendEmailVerification()
                    .addOnSuccessListener(unused -> callback.onEmailSent(true))
                    .addOnFailureListener(e -> callback.onEmailSent(false));
        } else {
            callback.onEmailSent(null);
        }
    }

    /**
     * Handles failures during the reload operation and subsequent email verification request.
     *
     * @param exception The exception occurred during the reload operation.
     * @param callback  Callback to handle the result of the email verification request.
     */
    private void handleReloadSendEmailFailure(Exception exception, SentEmailCallback callback) {
        if (exception instanceof FirebaseNetworkException) {
            callback.onNetworkError();
        } else {
            callback.onEmailSent(null);
        }
    }

    /**
     * Reloads the user's data and provides the email verification status through the
     * provided {@link ReloadUserCallback}.
     *
     * @param callback Callback to handle the result of the user data reload operation.
     */
    public void reloadUser(ReloadUserCallback callback) {
        final FirebaseUser user = getCurrentFirebaseUser();
        if (user != null) {
            user.reload()
                    .addOnSuccessListener(unused -> handleReloadSuccess(callback))
                    .addOnFailureListener(e -> handleReloadFailure(e, callback));
        } else {
            callback.onReloadUser(null);
        }
    }

    /**
     * Handles successful user data reload and provides the email verification status through the
     * provided {@link ReloadUserCallback}.
     *
     * @param callback Callback to handle the result of the user data reload operation.
     */
    private void handleReloadSuccess(ReloadUserCallback callback) {
        callback.onReloadUser(isEmailVerified());
    }

    /**
     * Handles reload operation failures, identifies the type of exception, and communicates
     * appropriate errors or network issues through the provided {@link ReloadUserCallback}.
     *
     * @param exception The exception occurred during the reload operation.
     * @param callback  Callback to handle the result of the user data reload operation.
     */
    private void handleReloadFailure(Exception exception, ReloadUserCallback callback) {
        if (exception instanceof FirebaseNetworkException) {
            callback.onNetworkError();
        } else {
            callback.onReloadUser(null);
        }
    }

    /**
     * Callback interface for handling the result of the email verification request.
     */
    public interface SentEmailCallback extends NetworkCallback {
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
    public interface ReloadUserCallback extends NetworkCallback {
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
