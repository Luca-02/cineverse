package com.example.cineverse.repository.classes.auth.service;

import android.content.Context;
import android.content.Intent;

import com.example.cineverse.R;
import com.example.cineverse.data.model.user.User;
import com.example.cineverse.data.service.firebase.UserFirebaseDatabaseServices;
import com.example.cineverse.repository.classes.AbstractUserRepository;
import com.example.cineverse.repository.classes.auth.AbstractAuthRepository;
import com.example.cineverse.repository.interfaces.auth.service.IAuthGoogle;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

/**
 * The {@link GoogleAuthRepository} class extends {@link AbstractAuthRepository} and provides
 * authentication functionality for Google Sign-In. It allows users to authenticate using their Google accounts.
 * The class handles Google Sign-In operations, processes authentication results, and communicates errors
 * or success through {@link AbstractAuthRepository#errorLiveData errorLiveData}. {@link GoogleAuthRepository} integrates with Firebase authentication
 * services for Google Sign-In authentication.
 */
public class GoogleAuthRepository
        extends AbstractAuthRepository
        implements IAuthGoogle {

    private final GoogleSignInOptions googleSignInOptions;

    /**
     * Constructs a {@link GoogleAuthRepository} object with the given application {@link Context}.
     *
     * @param context The application {@link Context} of the calling component.
     */
    public GoogleAuthRepository(Context context) {
        super(context);
        // Initialize GoogleSignInOptions
        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
    }

    public GoogleSignInOptions getGoogleSignInOptions() {
        return googleSignInOptions;
    }

    /**
     * Initiates Google Sign-In authentication with the provided data from the intent. Handles the
     * Google Sign-In result, processes authentication credentials, and communicates errors or success
     * through {@link AbstractAuthRepository#errorLiveData errorLiveData}.
     *
     * @param data {@link Intent} containing the Google Sign-In result data.
     */
    @Override
    public void authWithGoogle(Intent data) {
        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);
            AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
            firebaseAuth.signInWithCredential(credential)
                    .addOnSuccessListener(this::handleSuccess)
                    .addOnFailureListener(this::handleFailure);
        } catch (ApiException e) {
            errorLiveData.postValue(Error.ERROR_GOOGLE_SIGNIN_FAILED);
        }
    }

    /**
     * Handles successful Google Sign-In authentication by saving user data in firebase and
     * updating with {@link AbstractUserRepository#setUserLiveData setUserLiveData}.
     *
     * @param authResult The successful authentication result containing user information.
     */
    private void handleSuccess(AuthResult authResult) {
        FirebaseUser firebaseUser = authResult.getUser();
        if (firebaseUser != null) {
            userStorage.authGoogleUser(firebaseUser,
                    new UserFirebaseDatabaseServices.Callback<User>() {
                        @Override
                        public void onCallback(User user) {
                            handleUserAuthentication(user);
                        }

                        @Override
                        public void onNetworkUnavailable() {
                            handleAuthenticationNetworkFailure();
                        }
                    });
        } else {
            handleAuthenticationFailure();
        }
    }

    /**
     * Handles Google Sign-In authentication failure scenarios by identifying the type of exception and
     * posting appropriate errors for user feedback through {@link AbstractAuthRepository#errorLiveData errorLiveData}.
     *
     * @param exception The exception occurred during the Google Sign-In authentication process.
     */
    private void handleFailure(Exception exception) {
        if (exception instanceof FirebaseAuthInvalidUserException) {
            errorLiveData.postValue(Error.ERROR_NOT_FOUND_DISABLED);
        } else if (exception instanceof FirebaseAuthInvalidCredentialsException) {
            errorLiveData.postValue(Error.ERROR_INVALID_CREDENTIAL);
        } else if (exception instanceof FirebaseAuthUserCollisionException) {
            errorLiveData.postValue(Error.ERROR_EMAIL_ALREADY_EXISTS);
        } else if (exception instanceof FirebaseNetworkException) {
            setNetworkErrorLiveData(true);
        } else {
            errorLiveData.postValue(Error.ERROR_AUTHENTICATION_FAILED);
        }
    }

}
