package com.example.cineverse.ViewModel.Home;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.cineverse.Interface.Logged.IHome;
import com.example.cineverse.Repository.Logged.HomeRepository;
import com.example.cineverse.ViewModel.AbstractLoggedViewModel;

/**
 * HomeViewModel extends AbstractLoggedViewModel and is responsible for managing the data for the home screen
 * when the user is logged in. It communicates with the HomeRepository to retrieve user-related data
 * and manages the user's authentication state.
 */
public class HomeViewModel extends AbstractLoggedViewModel
        implements IHome {

    private final HomeRepository repository;

    /**
     * Constructs a HomeViewModel object with the given Application context.
     *
     * @param application The Application context of the calling component.
     */
    public HomeViewModel(@NonNull Application application) {
        super(application);
        repository = new HomeRepository(application);
        super.setLiveData(repository);
    }

    /**
     * Initiates the user logout process.
     */
    @Override
    public void logOut() {
        repository.logOut();
    }

}
