package com.example.cineverse.Repository.Auth.Logged;

import android.app.Application;

import com.example.cineverse.Repository.Auth.LoggedRepository;

/**
 * The HomeRepository class is responsible for handling data operations related to the home screen
 * after the user has successfully logged in. It extends the LoggedRepository class, inheriting
 * common functionalities related to user authentication and data management.
 */
public class HomeRepository extends LoggedRepository {

    /**
     * Constructs a HomeRepository object with the given Application context.
     *
     * @param application The Application context of the calling component.
     */
    public HomeRepository(Application application) {
        super(application);
    }

}
