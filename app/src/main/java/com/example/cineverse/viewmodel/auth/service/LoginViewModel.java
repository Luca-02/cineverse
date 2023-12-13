package com.example.cineverse.viewmodel.auth.service;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.cineverse.repository.auth.service.LoginRepository;
import com.example.cineverse.viewmodel.auth.AbstractAuthServicesViewModel;

/**
 * The {@link LoginViewModel} class extends {@link AbstractAuthServicesViewModel} and represents the ViewModel for user
 * login functionality. It handles user login operations, communicates changes in login status
 * and errors through LiveData objects. {@link LoginViewModel} integrates with {@link LoginRepository} and triggers
 * the login process based on user input.
 */
public class LoginViewModel
        extends AbstractAuthServicesViewModel<LoginRepository> {

    /**
     * Constructs a {@link LoginViewModel} object with the given {@link Application}.
     *
     * @param application The {@link Application} of the calling component.
     */
    public LoginViewModel(@NonNull Application application) {
        super(application, new LoginRepository(application.getApplicationContext()));
    }

    /**
     * Initiates the user login process with the specified email and password.
     *
     * @param email    The user's email address for login.
     * @param password The user's password for login.
     */
    public void login(String email, String password) {
        userRepository.login(email, password, this);
    }

}
