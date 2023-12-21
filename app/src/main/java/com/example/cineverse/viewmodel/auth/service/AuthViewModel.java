package com.example.cineverse.viewmodel.auth.service;

import android.app.Application;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.example.cineverse.R;
import com.example.cineverse.repository.auth.service.GoogleAuthRepository;
import com.example.cineverse.viewmodel.auth.AbstractAuthServicesViewModel;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

/**
 * The {@link AuthViewModel} class extends {@link AbstractAuthServicesViewModel} and represents the ViewModel for authentication
 * functionality. It handles user authentication operations, including Google Sign-In, and communicates
 * changes in user authentication status and errors through LiveData objects. {@link AuthViewModel} integrates
 * with {@link GoogleAuthRepository} and handles Google Sign-In authentication requests.
 */
public class AuthViewModel
        extends AbstractAuthServicesViewModel<GoogleAuthRepository> {

    private final GoogleSignInOptions googleSignInOptions;

    /**
     * Constructs an {@link AuthViewModel} object with the given {@link Application}.
     *
     * @param application The {@link Application} of the calling component.
     */
    public AuthViewModel(@NonNull Application application) {
        super(application, new GoogleAuthRepository(application.getApplicationContext()));
        // Initialize GoogleSignInOptions
        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(application.getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
    }

    public GoogleSignInOptions getGoogleSignInOptions() {
        return googleSignInOptions;
    }

    /**
     * Initiates Google Sign-In authentication with the provided data from the intent.
     *
     * @param data {@link Intent} containing the Google Sign-In result data.
     */
    public void authWithGoogle(Intent data) {
        userRepository.authWithGoogle(data);
    }

}
