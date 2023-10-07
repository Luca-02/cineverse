package com.example.cineverse.ViewModel.Auth;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.cineverse.ViewModel.AuthViewModel;

public class RegisterViewModel extends AuthViewModel {

    public RegisterViewModel(@NonNull Application application) {
        super(application);
    }

    public void register(String email, String password) {
        authAppRepository.register(email, password);
    }

}
