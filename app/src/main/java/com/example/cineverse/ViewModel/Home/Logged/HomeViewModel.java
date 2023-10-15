package com.example.cineverse.ViewModel.Home.Logged;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.cineverse.Repository.Auth.Logged.HomeRepository;
import com.example.cineverse.ViewModel.Home.LoggedViewModel;

public class HomeViewModel extends LoggedViewModel {

    private final HomeRepository repository;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        repository = new HomeRepository(application);
        setUserLiveData(repository);
        setLoggedOutLiveData(repository);
    }

    @Override
    public void logOut() {
        repository.logOut();
    }

}
