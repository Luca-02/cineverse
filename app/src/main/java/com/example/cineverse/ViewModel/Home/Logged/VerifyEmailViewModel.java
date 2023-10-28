package com.example.cineverse.ViewModel.Home.Logged;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.cineverse.Repository.Auth.Logged.VerifyEmailRepository;
import com.example.cineverse.Repository.Auth.LoggedRepository;
import com.example.cineverse.Repository.AuthRepository;
import com.example.cineverse.ViewModel.Home.LoggedViewModel;

public class VerifyEmailViewModel extends LoggedViewModel {

    private final VerifyEmailRepository repository;
    private final MutableLiveData<Boolean> getEmailSentLiveData;
    private final MutableLiveData<Boolean> emailVerifiedLiveData;

    public VerifyEmailViewModel(@NonNull Application application) {
        super(application);
        this.repository = new VerifyEmailRepository(application);
        getEmailSentLiveData = repository.getEmailSentLiveData();
        emailVerifiedLiveData = repository.getEmailVerifiedLiveData();
        setUserLiveData(repository);
        setLoggedOutLiveData(repository);
        setNetworkErrorLiveData(repository);
    }

    public MutableLiveData<Boolean> getGetEmailSentLiveData() {
        return getEmailSentLiveData;
    }

    public MutableLiveData<Boolean> getEmailVerifiedLiveData() {
        return emailVerifiedLiveData;
    }

    @Override
    public void logOut() {
        repository.logOut();
    }

    public void sendEmailVerification() {
        repository.sendEmailVerification();
    }

    public void reloadUser() {
        repository.reloadUser();
    }

}
