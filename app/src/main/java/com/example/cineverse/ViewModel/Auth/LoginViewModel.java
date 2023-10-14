package com.example.cineverse.ViewModel.Auth;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.cineverse.Repository.Auth.LoginRepository;

public class LoginViewModel extends AbstractAuthViewModel {

    private final LoginRepository loginRepository;
    private final MutableLiveData<LoginRepository.Error> errorLiveData;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        loginRepository = new LoginRepository(application);
        errorLiveData = loginRepository.getErrorLiveData();
        setUserLiveData();
    }

    @Override
    protected void setUserLiveData() {
        userLiveData = loginRepository.getUserLiveData();
    }

    public MutableLiveData<LoginRepository.Error> getErrorLiveData() {
        return errorLiveData;
    }

    public void login(String email, String password) {
        loginRepository.login(email, password);
    }

}
