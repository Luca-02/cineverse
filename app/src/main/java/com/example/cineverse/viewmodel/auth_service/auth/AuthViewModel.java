package com.example.cineverse.viewmodel.auth_service.auth;

import android.app.Application;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.example.cineverse.repository.classes.auth_service.auth.GoogleAuthRepository;
import com.example.cineverse.repository.interfaces.auth.IAuthGoogle;
import com.example.cineverse.viewmodel.auth_service.AbstractAuthServicesViewModel;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

/**
 * The AuthViewModel class extends AbstractAuthServicesViewModel and represents the ViewModel for authentication
 * functionality. It handles user authentication operations, including Google Sign-In, and communicates
 * changes in user authentication status and errors through LiveData objects. AuthViewModel integrates
 * with GoogleAuthRepository and handles Google Sign-In authentication requests.
 */
public class AuthViewModel
        extends AbstractAuthServicesViewModel<GoogleAuthRepository>
        implements IAuthGoogle {

    private final GoogleSignInOptions googleSignInOptions;

    /**
     * Constructs an AuthViewModel object with the given Application context.
     *
     * @param application The Application context of the calling component.
     */
    public AuthViewModel(@NonNull Application application) {
        super(application, new GoogleAuthRepository(application.getBaseContext()));
        googleSignInOptions = repository.getGoogleSignInOptions();
    }

    public GoogleSignInOptions getGoogleSignInOptions() {
        return googleSignInOptions;
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