package com.example.cineverse.ViewModel.Auth;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.cineverse.Repository.Auth.ForgotPasswordRepository;
import com.example.cineverse.ViewModel.AbstractAuthViewModel;

public class ForgotPasswordViewModel extends AbstractAuthViewModel {

    protected final ForgotPasswordRepository repository;
    private final MutableLiveData<ForgotPasswordRepository.Error> errorLiveData;

    public ForgotPasswordViewModel(@NonNull Application application) {
        super(application);
        repository = new ForgotPasswordRepository(application);
        errorLiveData = repository.getErrorLiveData();
        setUserLiveData(repository);
        setNetworkErrorLiveData(repository);
    }

    public MutableLiveData<ForgotPasswordRepository.Error> getErrorLiveData() {
        return errorLiveData;
    }

    public void forgotPassword(String email) {
        repository.forgotPassword(email);
    }

}
