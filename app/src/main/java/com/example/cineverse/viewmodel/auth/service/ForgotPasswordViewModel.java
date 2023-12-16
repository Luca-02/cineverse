package com.example.cineverse.viewmodel.auth.service;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.cineverse.repository.auth.service.ForgotPasswordRepository;
import com.example.cineverse.viewmodel.auth.AbstractAuthServicesViewModel;

/**
 * The {@link ForgotPasswordViewModel} class extends {@link AbstractAuthServicesViewModel} and represents the ViewModel
 * for the "Forgot Password" functionality. It handles the user's request to reset their password,
 * communicates changes in the reset password process, and potential errors through LiveData objects.
 * {@link ForgotPasswordViewModel} integrates with {@link ForgotPasswordRepository} and triggers the password reset
 * email sending process based on user input.
 */
public class ForgotPasswordViewModel
        extends AbstractAuthServicesViewModel<ForgotPasswordRepository> {

    /**
     * Constructs a {@link ForgotPasswordViewModel} object with the given {@link Application}.
     *
     * @param application The {@link Application} of the calling component.
     */
    public ForgotPasswordViewModel(@NonNull Application application) {
        super(application, new ForgotPasswordRepository(application.getApplicationContext()));
    }

    /**
     * Initiates the process of sending a password reset email to the specified email address.
     *
     * @param email The user's email address for sending the password reset email.
     */
    public void forgotPassword(String email) {
        userRepository.forgotPassword(email);
    }

}
