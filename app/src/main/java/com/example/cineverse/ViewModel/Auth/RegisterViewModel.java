package com.example.cineverse.ViewModel.Auth;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.cineverse.Repository.Auth.RegisterRepository;

public class RegisterViewModel extends AbstractAuthViewModel {

    private final RegisterRepository registerRepository;
    private final MutableLiveData<RegisterRepository.Error> errorLiveData;

    public RegisterViewModel(@NonNull Application application) {
        super(application);
        registerRepository = new RegisterRepository(application);
        errorLiveData = registerRepository.getErrorLiveData();
        setUserLiveData();
    }

    @Override
    protected void setUserLiveData() {
        userLiveData = registerRepository.getUserLiveData();
    }

    public MutableLiveData<RegisterRepository.Error> getErrorLiveData() {
        return errorLiveData;
    }

    public void register(String email, String password) {
        registerRepository.register(email, password);
    }

}
