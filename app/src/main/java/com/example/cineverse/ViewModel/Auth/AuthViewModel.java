package com.example.cineverse.ViewModel.Auth;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.cineverse.Repository.AuthRepository;
import com.example.cineverse.ViewModel.AbstractAuthViewModel;

public class AuthViewModel extends AbstractAuthViewModel {

    private final AuthRepository repository;

    public AuthViewModel(@NonNull Application application) {
        super(application);
        repository = new AuthRepository(application);
        setUserLiveData(repository);
    }

}
