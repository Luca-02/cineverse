package com.example.cineverse.ViewModel.VerifyEmail;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.cineverse.Repository.Auth.Logged.VerifyEmailRepository;
import com.example.cineverse.ViewModel.LoggedViewModel;

/**
 * The VerifyEmailViewModel class extends LoggedViewModel and represents the ViewModel for email
 * verification functionality. It handles user email verification operations, communicates changes
 * in email verification status, email sending status, user logout status, and network errors
 * through LiveData objects. VerifyEmailViewModel integrates with VerifyEmailRepository and
 * triggers the email verification process based on user input.
 */
public class VerifyEmailViewModel extends LoggedViewModel {

    private final VerifyEmailRepository repository;
    private final MutableLiveData<Boolean> emailSentLiveData;
    private final MutableLiveData<Boolean> emailVerifiedLiveData;

    /**
     * Constructs a VerifyEmailViewModel object with the given Application context.
     *
     * @param application The Application context of the calling component.
     */
    public VerifyEmailViewModel(@NonNull Application application) {
        super(application);
        this.repository = new VerifyEmailRepository(application);
        emailSentLiveData = repository.getEmailSentLiveData();
        emailVerifiedLiveData = repository.getEmailVerifiedLiveData();
        setUserLiveData(repository);
        setLoggedOutLiveData(repository);
        setNetworkErrorLiveData(repository);
    }

    public MutableLiveData<Boolean> getEmailSentLiveData() {
        return emailSentLiveData;
    }

    public MutableLiveData<Boolean> getEmailVerifiedLiveData() {
        return emailVerifiedLiveData;
    }

    /**
     * Initiates the process of sending an email verification to the user's email address.
     */
    public void sendEmailVerification() {
        repository.sendEmailVerification();
    }

    /**
     * Initiates the process of reloading the user's data from the server.
     */
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
