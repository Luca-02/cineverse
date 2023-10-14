package com.example.cineverse.ViewModel.Home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.cineverse.Repository.Auth.LoggedRepository;
import com.example.cineverse.ViewModel.Auth.AbstractAuthViewModel;

public class HomeViewModel extends AbstractAuthViewModel {

    private final LoggedRepository loggedRepository;
    private final MutableLiveData<Boolean> loggedOutLiveData;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        loggedRepository = new LoggedRepository(application);
        loggedOutLiveData = loggedRepository.getLoggedOutLiveData();
        setUserLiveData();
    }

    @Override
    protected void setUserLiveData() {
        userLiveData = loggedRepository.getUserLiveData();
    }

    public MutableLiveData<Boolean> getLoggedOutLiveData() {
        return loggedOutLiveData;
    }

    public void logOut() {
        loggedRepository.logOut();
    }

}
