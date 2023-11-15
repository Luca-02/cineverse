package com.example.cineverse.ViewModel.Auth;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.cineverse.Interface.Auth.IForgotPassword;
import com.example.cineverse.Repository.Auth.ForgotPasswordRepository;
import com.example.cineverse.ViewModel.AbstractAuthServicesViewModel;
import com.example.cineverse.ViewModel.AbstractAuthViewModel;

/**
 * The ForgotPasswordViewModel class extends AbstractAuthServicesViewModel and represents the ViewModel
 * for the "Forgot Password" functionality. It handles the user's request to reset their password,
 * communicates changes in the reset password process, and potential errors through LiveData objects.
 * ForgotPasswordViewModel integrates with ForgotPasswordRepository and triggers the password reset
 * email sending process based on user input.
 */
public class ForgotPasswordViewModel extends AbstractAuthServicesViewModel
        implements IForgotPassword {

    protected final ForgotPasswordRepository repository;

    /**
     * Constructs a ForgotPasswordViewModel object with the given Application context.
     *
     * @param application The Application context of the calling component.
     */
    public ForgotPasswordViewModel(@NonNull Application application) {
        super(application);
        repository = new ForgotPasswordRepository(application);
        super.setLiveData(repository);
    }

    /**
     * Initiates the process of sending a password reset email to the specified email address.
     *
     * @param email The user's email address for sending the password reset email.
     */
    @Override
    public void forgotPassword(String email) {
        repository.forgotPassword(email);
    }

}
