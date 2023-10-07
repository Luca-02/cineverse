package com.example.cineverse.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

public class HomeViewModel extends AuthViewModel {

    private final MutableLiveData<Boolean> loggedOutLiveData;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        loggedOutLiveData = authAppRepository.getLoggedOutLiveData();
    }

    public MutableLiveData<Boolean> getLoggedOutLiveData() {
        return loggedOutLiveData;
    }

    public void logOut() {
        authAppRepository.logOut();
    }

}
