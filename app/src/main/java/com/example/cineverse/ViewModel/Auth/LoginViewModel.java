package com.example.cineverse.ViewModel.Auth;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.cineverse.Interface.Auth.ILogin;
import com.example.cineverse.Repository.Auth.LoginRepository;
import com.example.cineverse.ViewModel.AbstractAuthServicesViewModel;

/**
 * The LoginViewModel class extends AbstractAuthServicesViewModel and represents the ViewModel for user
 * login functionality. It handles user login operations, communicates changes in login status
 * and errors through LiveData objects. LoginViewModel integrates with LoginRepository and triggers
 * the login process based on user input.
 */
public class LoginViewModel
        extends AbstractAuthServicesViewModel<LoginRepository>
        implements ILogin {

    /**
     * Constructs a LoginViewModel object with the given Application context.
     *
     * @param application The Application context of the calling component.
     */
    public LoginViewModel(@NonNull Application application) {
        super(application, new LoginRepository(application));
    }

    /**
     * Initiates the user login process with the specified email and password.
     *
     * @param email    The user's email address for login.
     * @param password The user's password for login.
     */
    @Override
    public void login(String email, String password) {
        repository.login(email, password);
    }

}
