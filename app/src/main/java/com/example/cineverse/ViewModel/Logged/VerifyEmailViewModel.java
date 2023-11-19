package com.example.cineverse.ViewModel.Logged;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.cineverse.Interface.Logged.IVerifyEmail;
import com.example.cineverse.Repository.Logged.VerifyEmailRepository;
import com.example.cineverse.ViewModel.AbstractLoggedViewModel;

/**
 * The VerifyEmailViewModel class extends AbstractLoggedViewModel and represents the ViewModel for email
 * verification functionality. It handles user email verification operations, communicates changes
 * in email verification status, email sending status, user logout status, and network errors
 * through LiveData objects. VerifyEmailViewModel integrates with VerifyEmailRepository and
 * triggers the email verification process based on user input.
 */
public class VerifyEmailViewModel
        extends AbstractLoggedViewModel<VerifyEmailRepository>
        implements IVerifyEmail {

    private MutableLiveData<Boolean> emailSentLiveData;
    private MutableLiveData<Boolean> emailVerifiedLiveData;

    /**
     * Constructs a VerifyEmailViewModel object with the given Application context.
     *
     * @param application The Application context of the calling component.
     */
    public VerifyEmailViewModel(@NonNull Application application) {
        super(application, new VerifyEmailRepository(application));
        emailSentLiveData = repository.getEmailSentLiveData();
        emailVerifiedLiveData = repository.getEmailVerifiedLiveData();
    }

    public MutableLiveData<Boolean> getEmailSentLiveData() {
        return emailSentLiveData;
    }

    /**
     * Clears the MutableLiveData instance for email sent status.
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
     * Clears the MutableLiveData instance for email verification status.
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
        repository.sendEmailVerification();
    }

    /**
     * Initiates the process of reloading the user's data from the server.
     */
    @Override
    public void reloadUser() {
        repository.reloadUser();
    }

    /**
     * Initiates the user logout process.
     */
    @Override
    public void logOut() {
        repository.logOut();
    }

}
