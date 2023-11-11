package com.example.cineverse.ViewModel.Home;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.cineverse.Repository.Auth.Logged.HomeRepository;
import com.example.cineverse.ViewModel.LoggedViewModel;

/**
 * HomeViewModel extends LoggedViewModel and is responsible for managing the data for the home screen
 * when the user is logged in. It communicates with the HomeRepository to retrieve user-related data
 * and manages the user's authentication state.
 */
public class HomeViewModel extends LoggedViewModel {

    private final HomeRepository repository;

    /**
     * Constructs a HomeViewModel object.
     *
     * @param application The Application context of the calling component.
     */
    public HomeViewModel(@NonNull Application application) {
        super(application);
        repository = new HomeRepository(application);
        setUserLiveData(repository);
        setLoggedOutLiveData(repository);
    }

    /**
     * Initiates the user logout process.
     */
    @Override
    public void logOut() {
        repository.logOut();
    }

}
