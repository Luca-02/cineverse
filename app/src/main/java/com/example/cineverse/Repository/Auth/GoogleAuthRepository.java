package com.example.cineverse.Repository.Auth;

import android.app.Application;
import android.content.Intent;

import androidx.lifecycle.MutableLiveData;

import com.example.cineverse.Interface.Auth.IAuthGoogle;
import com.example.cineverse.Repository.AbstractAuthRepository;
import com.example.cineverse.Repository.AbstractAuthServiceRepository;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.GoogleAuthProvider;

/**
 * The GoogleAuthRepository class extends AbstractAuthServiceRepository and provides authentication functionality
 * for Google Sign-In. It allows users to authenticate using their Google accounts. The class
 * handles Google Sign-In operations, processes authentication results, and communicates errors
 * or success through errorLiveData. GoogleAuthRepository integrates with Firebase authentication
 * services for Google Sign-In authentication.
 */
public class GoogleAuthRepository extends AbstractAuthServiceRepository
        implements IAuthGoogle {

    /**
     * Constructs a GoogleAuthRepository object with the given Application context.
     *
     * @param application The Application context of the calling component.
     */
    public GoogleAuthRepository(Application application) {
        super(application);
    }

    /**
     * Initiates Google Sign-In authentication with the provided data from the intent. Handles the
     * Google Sign-In result, processes authentication credentials, and communicates errors or success
     * through errorLiveData.
     *
     * @param data Intent containing the Google Sign-In result data.
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
     * Handles successful Google Sign-In authentication by updating the user live data.
     *
     * @param authResult The successful authentication result containing user information.
     */
    private void handleSuccess(AuthResult authResult)  {
        setUserLiveData(authResult.getUser());
    }

    /**
     * Handles Google Sign-In authentication failure scenarios by identifying the type of exception and
     * posting appropriate errors for user feedback through errorLiveData.
     *
     * @param exception The exception occurred during the Google Sign-In authentication process.
     */
    private void handleFailure(Exception exception) {
        if (exception instanceof FirebaseAuthInvalidUserException) {
            errorLiveData.postValue(Error.ERROR_NOT_FOUND_DISABLED);
        } else if (exception instanceof FirebaseAuthInvalidCredentialsException) {
            errorLiveData.postValue(Error.ERROR_INVALID_CREDENTIAL);
        } else if (exception instanceof FirebaseAuthUserCollisionException) {
            errorLiveData.postValue(Error.ERROR_ALREADY_EXISTS);
        } else if (exception instanceof FirebaseNetworkException) {
            setNetworkErrorLiveData(true);
        } else {
            errorLiveData.postValue(Error.ERROR_AUTHENTICATION_FAILED);
        }
    }

}
