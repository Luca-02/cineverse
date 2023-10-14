package com.example.cineverse.ViewModel.Auth;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.cineverse.Repository.AuthRepository;

public class AuthViewModel extends AbstractAuthViewModel {

    private final AuthRepository authRepository;

    public AuthViewModel(@NonNull Application application) {
        super(application);
        authRepository = new AuthRepository(application);
        setUserLiveData();
    }

    @Override
    protected void setUserLiveData() {
        userLiveData = authRepository.getUserLiveData();
    }

}
