package com.example.cineverse.ViewModel.Auth;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.cineverse.Repository.Auth.LoginRepository;
import com.example.cineverse.ViewModel.AbstractAuthViewModel;

public class LoginViewModel extends AbstractAuthViewModel {

    private final LoginRepository repository;
    private final MutableLiveData<LoginRepository.Error> errorLiveData;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        repository = new LoginRepository(application);
        errorLiveData = repository.getErrorLiveData();
        setUserLiveData(repository);
        setNetworkErrorLiveData(repository);
    }

    public MutableLiveData<LoginRepository.Error> getErrorLiveData() {
        return errorLiveData;
    }

    public void login(String email, String password) {
        repository.login(email, password);
    }

}
