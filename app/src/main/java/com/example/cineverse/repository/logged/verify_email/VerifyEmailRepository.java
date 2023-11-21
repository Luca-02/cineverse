package com.example.cineverse.repository.logged.verify_email;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.example.cineverse.interfaces.logged.verify_email.IVerifyEmail;
import com.example.cineverse.repository.AbstractLoggedRepository;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.FirebaseUser;

/**
 * The VerifyEmailRepository class extends AbstractLoggedRepository and provides functionality for verifying
 * user email addresses. It allows users to request email verification, reload their user data,
 * and check whether their email is verified or not. The class communicates email verification
 * status, reload operations, and network errors via MutableLiveData for observation and user feedback.
 */
public class VerifyEmailRepository
        extends AbstractLoggedRepository
        implements IVerifyEmail {

    private final MutableLiveData<Boolean> emailSentLiveData;
    private final MutableLiveData<Boolean> emailVerifiedLiveData;

    /**
     * Constructs a VerifyEmailRepository object with the given Application context and initializes
     * MutableLiveData for observing email verification status and reload operations.
     *
     * @param application The Application context of the calling component.
     */
    public VerifyEmailRepository(Application application) {
        super(application);
        emailSentLiveData = new MutableLiveData<>();
        emailVerifiedLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<Boolean> getEmailSentLiveData() {
        return emailSentLiveData;
    }

    public MutableLiveData<Boolean> getEmailVerifiedLiveData() {
        return emailVerifiedLiveData;
    }

    /**
     * Initiates a request to send email verification to the user's email address. Reloading user
     * data is performed to ensure up-to-date information before sending the verification email.
     */
    @Override
    public void sendEmailVerification() {
        FirebaseUser user = getCurrentUser();
        if (user != null) {
            user.reload()
                    .addOnSuccessListener(unused -> handleReloadSendEmailSuccess())
                    .addOnFailureListener(e -> handleReloadFailure(e, emailSentLiveData));
        } else {
            emailSentLiveData.postValue(null);
        }
    }

    /**
     * Reloads user data and updates emailSentLiveData based on the result of sending the
     * email verification request.
     */
    private void handleReloadSendEmailSuccess() {
        FirebaseUser reloadedUser = getCurrentUser();
        if (reloadedUser != null) {
            reloadedUser.sendEmailVerification()
                    .addOnSuccessListener(unused1 -> emailSentLiveData.postValue(true))
                    .addOnFailureListener(e -> emailSentLiveData.postValue(false));
        } else {
            emailSentLiveData.postValue(null);
        }
    }

    /**
     * Reloads user data and updates emailVerifiedLiveData based on the result of reloading.
     */
    @Override
    public void reloadUser() {
        final FirebaseUser user = getCurrentUser();
        if (user != null) {
            user.reload()
                    .addOnSuccessListener(unused -> handleReloadSuccess())
                    .addOnFailureListener(e -> handleReloadFailure(e, emailVerifiedLiveData));
        } else {
            emailVerifiedLiveData.postValue(null);
        }
    }

    /**
     * Handles the successful reload operation and updates emailVerifiedLiveData based on the
     * user's email verification status.
     */
    private void handleReloadSuccess() {
        emailVerifiedLiveData.postValue(isEmailVerified());
    }

    /**
     * Handles reload operation failures, identifies the type of exception, and communicates
     * appropriate errors or network issues via the provided live data.
     *
     * @param exception The exception occurred during the reload operation.
     * @param liveData  The MutableLiveData<Boolean> to post the result (null if network error).
     */
    private void handleReloadFailure(Exception exception, MutableLiveData<Boolean> liveData) {
        if (exception instanceof FirebaseNetworkException) {
            setNetworkErrorLiveData(true);
        } else {
            liveData.postValue(null);
        }
    }

}
