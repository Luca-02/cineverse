package com.example.cineverse.ViewModel.Auth;

import android.app.Application;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.example.cineverse.Interface.Auth.IAuthGoogle;
import com.example.cineverse.Repository.Auth.GoogleAuthRepository;
import com.example.cineverse.ViewModel.AbstractAuthServicesViewModel;

/**
 * The AuthViewModel class extends AbstractAuthServicesViewModel and represents the ViewModel for authentication
 * functionality. It handles user authentication operations, including Google Sign-In, and communicates
 * changes in user authentication status and errors through LiveData objects. AuthViewModel integrates
 * with GoogleAuthRepository and handles Google Sign-In authentication requests.
 */
public class AuthViewModel
        extends AbstractAuthServicesViewModel<GoogleAuthRepository>
        implements IAuthGoogle {

    /**
     * Constructs an AuthViewModel object with the given Application context.
     *
     * @param application The Application context of the calling component.
     */
    public AuthViewModel(@NonNull Application application) {
        super(application, new GoogleAuthRepository(application));
    }

    /**
     * Initiates Google Sign-In authentication with the provided data from the intent.
     *
     * @param data Intent containing the Google Sign-In result data.
     */
    @Override
    public void authWithGoogle(Intent data) {
        repository.authWithGoogle(data);
    }

}
