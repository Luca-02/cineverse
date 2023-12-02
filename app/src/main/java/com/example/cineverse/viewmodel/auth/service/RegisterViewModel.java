package com.example.cineverse.viewmodel.auth.service;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.cineverse.repository.auth.service.RegisterRepository;
import com.example.cineverse.viewmodel.auth.AbstractAuthServicesViewModel;

/**
 * The {@link RegisterViewModel} class extends {@link AbstractAuthServicesViewModel} and represents the ViewModel for user
 * registration functionality. It handles user registration operations, communicates changes in
 * registration status and errors through LiveData objects. {@link RegisterViewModel} integrates with
 * {@link RegisterRepository} and triggers the registration process based on user input.
 */
public class RegisterViewModel
        extends AbstractAuthServicesViewModel<RegisterRepository> {

    /**
     * Constructs a {@link RegisterViewModel} object with the given {@link Application}.
     *
     * @param application The {@link Application of the calling component.
     */
    public RegisterViewModel(@NonNull Application application) {
        super(application, new RegisterRepository(application.getBaseContext()));
    }

    /**
     * Initiates the user registration process with the specified email and password.
     *
     * @param email    The user's email address for registration.
     * @param password The user's chosen password for registration.
     */
    public void register(String username, String email, String password) {
        userRepository.register(username, email, password, this);
    }

}
