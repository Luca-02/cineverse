package com.example.cineverse.ViewModel.Auth;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.cineverse.ViewModel.AuthViewModel;

public class LoginViewModel extends AuthViewModel {

    private final MutableLiveData<Boolean> invalidEmailLiveData;
    private final MutableLiveData<Boolean> wrongPasswordLiveData;
    private final MutableLiveData<Boolean> userNotFoundLiveData;
    private final MutableLiveData<Boolean> userDisabledLiveData;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        invalidEmailLiveData = authAppRepository.getInvalidEmailLiveData();
        wrongPasswordLiveData = authAppRepository.getWrongPasswordLiveData();
        userNotFoundLiveData = authAppRepository.getUserNotFoundLiveData();
        userDisabledLiveData = authAppRepository.getUserDisabledLiveData();
    }

    public MutableLiveData<Boolean> getInvalidEmailLiveData() {
        return invalidEmailLiveData;
    }

    public MutableLiveData<Boolean> getWrongPasswordLiveData() {
        return wrongPasswordLiveData;
    }

    public MutableLiveData<Boolean> getUserNotFoundLiveData() {
        return userNotFoundLiveData;
    }

    public MutableLiveData<Boolean> getUserDisabledLiveData() {
        return userDisabledLiveData;
    }

    public void login(String email, String password) {
        authAppRepository.login(email, password);
    }

}
