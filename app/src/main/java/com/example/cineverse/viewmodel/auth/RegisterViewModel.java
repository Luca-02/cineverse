package com.example.cineverse.viewmodel.auth;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.cineverse.interfaces.auth.IRegister;
import com.example.cineverse.repository.auth.RegisterRepository;
import com.example.cineverse.viewmodel.AbstractAuthServicesViewModel;

/**
 * The RegisterViewModel class extends AbstractAuthServicesViewModel and represents the ViewModel for user
 * registration functionality. It handles user registration operations, communicates changes in
 * registration status and errors through LiveData objects. RegisterViewModel integrates with
 * RegisterRepository and triggers the registration process based on user input.
 */
public class RegisterViewModel
        extends AbstractAuthServicesViewModel<RegisterRepository>
        implements IRegister {

    /**
     * Constructs a RegisterViewModel object with the given Application context.
     *
     * @param application The Application context of the calling component.
     */
    public RegisterViewModel(@NonNull Application application) {
        super(application, new RegisterRepository(application));
    }

    /**
     * Initiates the user registration process with the specified email and password.
     *
     * @param email    The user's email address for registration.
     * @param password The user's chosen password for registration.
     */
    @Override
    public void register(String username, String email, String password) {
        repository.register(username, email, password);
    }

}
