package com.example.cineverse.ViewModel.Auth;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.cineverse.Interface.Auth.IRegister;
import com.example.cineverse.Repository.Auth.RegisterRepository;
import com.example.cineverse.ViewModel.AbstractAuthServicesViewModel;
import com.example.cineverse.ViewModel.AbstractAuthViewModel;

/**
 * The RegisterViewModel class extends AbstractAuthServicesViewModel and represents the ViewModel for user
 * registration functionality. It handles user registration operations, communicates changes in
 * registration status and errors through LiveData objects. RegisterViewModel integrates with
 * RegisterRepository and triggers the registration process based on user input.
 */
public class RegisterViewModel extends AbstractAuthServicesViewModel
        implements IRegister {

    private final RegisterRepository repository;

    /**
     * Constructs a RegisterViewModel object with the given Application context.
     *
     * @param application The Application context of the calling component.
     */
    public RegisterViewModel(@NonNull Application application) {
        super(application);
        repository = new RegisterRepository(application);
        super.setLiveData(repository);
    }

    /**
     * Initiates the user registration process with the specified email and password.
     *
     * @param email    The user's email address for registration.
     * @param password The user's chosen password for registration.
     */
    @Override
    public void register(String email, String password) {
        repository.register(email, password);
    }

}
