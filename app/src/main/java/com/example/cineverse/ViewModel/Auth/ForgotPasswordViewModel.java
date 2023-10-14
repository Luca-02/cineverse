package com.example.cineverse.ViewModel.Auth;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.cineverse.Repository.Auth.ForgotPasswordRepository;

public class ForgotPasswordViewModel extends AbstractAuthViewModel {

    protected final ForgotPasswordRepository forgotPasswordRepository;
    private final MutableLiveData<ForgotPasswordRepository.Error> errorLiveData;

    public ForgotPasswordViewModel(@NonNull Application application) {
        super(application);
        forgotPasswordRepository = new ForgotPasswordRepository(application);
        errorLiveData = forgotPasswordRepository.getErrorLiveData();
        setUserLiveData();
    }

    @Override
    protected void setUserLiveData() {
        userLiveData = forgotPasswordRepository.getUserLiveData();
    }

    public MutableLiveData<ForgotPasswordRepository.Error> getErrorLiveData() {
        return errorLiveData;
    }

    public void forgotPassword(String email) {
        forgotPasswordRepository.forgotPassword(email);
    }

}
