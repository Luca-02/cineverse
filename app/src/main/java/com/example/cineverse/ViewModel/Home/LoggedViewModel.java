package com.example.cineverse.ViewModel.Home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.cineverse.Repository.Auth.LoggedRepository;
import com.example.cineverse.ViewModel.AbstractAuthViewModel;

public abstract class LoggedViewModel extends AbstractAuthViewModel {

    protected MutableLiveData<Boolean> loggedOutLiveData;

    public LoggedViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Boolean> getLoggedOutLiveData() {
        return loggedOutLiveData;
    }

    protected void setLoggedOutLiveData(LoggedRepository repository) {
        loggedOutLiveData = repository.getLoggedOutLiveData();
    };

    public abstract void logOut();

}
