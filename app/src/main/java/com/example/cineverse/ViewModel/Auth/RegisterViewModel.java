package com.example.cineverse.ViewModel.Auth;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.cineverse.Repository.Auth.RegisterRepository;
import com.example.cineverse.ViewModel.AbstractAuthViewModel;

/**
 * The RegisterViewModel class extends AbstractAuthViewModel and represents the ViewModel for user
 * registration functionality. It handles user registration operations, communicates changes in
 * registration status and errors through LiveData objects. RegisterViewModel integrates with
 * RegisterRepository and triggers the registration process based on user input.
 */
public class RegisterViewModel extends AbstractAuthViewModel {

    private final RegisterRepository repository;
    private final MutableLiveData<RegisterRepository.Error> errorLiveData;

    /**
     * Constructs a RegisterViewModel object with the given Application context.
     *
     * @param application The Application context of the calling component.
     */
    public RegisterViewModel(@NonNull Application application) {
        super(application);
        repository = new RegisterRepository(application);
        errorLiveData = repository.getErrorLiveData();
        setUserLiveData(repository);
        setNetworkErrorLiveData(repository);
    }

    public MutableLiveData<RegisterRepository.Error> getErrorLiveData() {
        return errorLiveData;
    }

    /**
     * Initiates the user registration process with the specified email and password.
     *
     * @param email    The user's email address for registration.
     * @param password The user's chosen password for registration.
     */
    public void register(String email, String password) {
        repository.register(email, password);
    }

}
