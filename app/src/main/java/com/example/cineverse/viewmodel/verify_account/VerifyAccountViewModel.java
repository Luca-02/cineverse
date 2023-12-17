package com.example.cineverse.viewmodel.verify_account;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.cineverse.repository.auth.logged.VerifyAccountRepository;
import com.example.cineverse.viewmodel.AbstractLoggedViewModel;

/**
 * The {@link VerifyAccountViewModel} class extends {@link AbstractLoggedViewModel} and represents the ViewModel for email
 * verification functionality. It handles user email verification operations, communicates changes
 * in email verification status, email sending status, user logout status, and network errors
 * through LiveData objects. {@link VerifyAccountViewModel} integrates with {@link VerifyAccountRepository} and
 * triggers the email verification process based on user input.
 */
public class VerifyAccountViewModel
        extends AbstractLoggedViewModel<VerifyAccountRepository>
        implements VerifyAccountRepository.SentEmailCallback, VerifyAccountRepository.ReloadUserCallback {

    private MutableLiveData<Boolean> emailSentLiveData;
    private MutableLiveData<Boolean> emailVerifiedLiveData;

    /**
     * Constructs a {@link VerifyAccountViewModel} object with the given {@link Application}.
     *
     * @param application The {@link Application} of the calling component.
     */
    public VerifyAccountViewModel(@NonNull Application application) {
        super(application, new VerifyAccountRepository(application.getApplicationContext()));
        userRepository.setSentEmailCallback(this);
        userRepository.setReloadUserCallback(this);
    }

    public MutableLiveData<Boolean> getEmailSentLiveData() {
        if (emailSentLiveData == null) {
            emailSentLiveData = new MutableLiveData<>();
        }
        return emailSentLiveData;
    }

    public MutableLiveData<Boolean> getEmailVerifiedLiveData() {
        if (emailVerifiedLiveData == null) {
            emailVerifiedLiveData = new MutableLiveData<>();
        }
        return emailVerifiedLiveData;
    }

    /**
     * Initiates the process of sending an email verification to the user's email address.
     */
    public void sendEmailVerification() {
        userRepository.sendEmailVerification();
    }

    /**
     * Initiates the process of reloading the user's data from the server.
     */
    public void reloadUser() {
        userRepository.reloadUser();
    }

    /**
     * Overrides the {@link VerifyAccountRepository.SentEmailCallback#onEmailSent(Boolean)} method
     * to handle the result of the email sending operation and update the email sent LiveData.
     * Clears email sent status {@link MutableLiveData}.
     *
     * @param isSent The status of the email sending operation.
     */
    @Override
    public void onEmailSent(Boolean isSent) {
        getEmailSentLiveData().postValue(isSent);
    }

    /**
     * Overrides the {@link VerifyAccountRepository.ReloadUserCallback#onReloadUser(Boolean)} method
     * to handle the result of the user data reloading operation and update the email verification LiveData.
     * Clears email verification status {@link MutableLiveData}.
     *
     * @param isVerified The status of the email verification.
     */
    @Override
    public void onReloadUser(Boolean isVerified) {
        getEmailVerifiedLiveData().postValue(isVerified);
    }

}
