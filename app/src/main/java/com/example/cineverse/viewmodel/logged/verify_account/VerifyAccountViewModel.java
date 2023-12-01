package com.example.cineverse.viewmodel.logged.verify_account;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.cineverse.repository.classes.logged.status.VerifyAccountRepository;
import com.example.cineverse.repository.interfaces.logged.status.IVerifyAccount;
import com.example.cineverse.viewmodel.logged.AbstractLoggedViewModel;

/**
 * The {@link VerifyAccountViewModel} class extends {@link AbstractLoggedViewModel} and represents the ViewModel for email
 * verification functionality. It handles user email verification operations, communicates changes
 * in email verification status, email sending status, user logout status, and network errors
 * through LiveData objects. {@link VerifyAccountViewModel} integrates with {@link VerifyAccountRepository} and
 * triggers the email verification process based on user input.
 */
public class VerifyAccountViewModel
        extends AbstractLoggedViewModel<VerifyAccountRepository>
        implements IVerifyAccount {

    private MutableLiveData<Boolean> emailSentLiveData;
    private MutableLiveData<Boolean> emailVerifiedLiveData;

    /**
     * Constructs a {@link VerifyAccountViewModel} object with the given {@link Application}.
     *
     * @param application The {@link Application} of the calling component.
     */
    public VerifyAccountViewModel(@NonNull Application application) {
        super(application, new VerifyAccountRepository(application.getBaseContext()));
        emailSentLiveData = userRepository.getEmailSentLiveData();
        emailVerifiedLiveData = userRepository.getEmailVerifiedLiveData();
    }

    public MutableLiveData<Boolean> getEmailSentLiveData() {
        return emailSentLiveData;
    }

    /**
     * Clears the {@link MutableLiveData} instance for email sent status.
     * This method is typically used when the network error state is consumed, and you don't want
     * to keep the last state in the LiveData.
     */
    public void clearEmailSentLiveData() {
        emailSentLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<Boolean> getEmailVerifiedLiveData() {
        return emailVerifiedLiveData;
    }

    /**
     * Clears the {@link MutableLiveData} instance for email verification status.
     * This method is typically used when the network error state is consumed, and you don't want
     * to keep the last state in the LiveData.
     */
    public void clearEmailVerifiedLiveData() {
        emailVerifiedLiveData = new MutableLiveData<>();
    }

    /**
     * Initiates the process of sending an email verification to the user's email address.
     */
    @Override
    public void sendEmailVerification() {
        userRepository.sendEmailVerification();
    }

    /**
     * Initiates the process of reloading the user's data from the server.
     */
    @Override
    public void reloadUser() {
        userRepository.reloadUser();
    }

}
