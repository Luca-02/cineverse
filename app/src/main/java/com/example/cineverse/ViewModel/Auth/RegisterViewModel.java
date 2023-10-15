package com.example.cineverse.ViewModel.Auth;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.cineverse.Repository.Auth.RegisterRepository;
import com.example.cineverse.ViewModel.AbstractAuthViewModel;

public class RegisterViewModel extends AbstractAuthViewModel {

    private final RegisterRepository repository;
    private final MutableLiveData<RegisterRepository.Error> errorLiveData;

    public RegisterViewModel(@NonNull Application application) {
        super(application);
        repository = new RegisterRepository(application);
        errorLiveData = repository.getErrorLiveData();
        setUserLiveData(repository);
    }

    public MutableLiveData<RegisterRepository.Error> getErrorLiveData() {
        return errorLiveData;
    }

    public void register(String email, String password) {
        repository.register(email, password);
    }

}
