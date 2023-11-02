package com.example.cineverse.ViewModel.Auth;

import android.app.Application;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.cineverse.Repository.Auth.GoogleAuthRepository;
import com.example.cineverse.Repository.Auth.LoginRepository;
import com.example.cineverse.Repository.AuthRepository;
import com.example.cineverse.ViewModel.AbstractAuthViewModel;

/**
 * The AuthViewModel class extends AbstractAuthViewModel and represents the ViewModel for authentication
 * functionality. It handles user authentication operations, including Google Sign-In, and communicates
 * changes in user authentication status and errors through LiveData objects. AuthViewModel integrates
 * with GoogleAuthRepository and handles Google Sign-In authentication requests.
 */
public class AuthViewModel extends AbstractAuthViewModel {

    private final GoogleAuthRepository repository;
    private final MutableLiveData<GoogleAuthRepository.Error> errorLiveData;

    /**
     * Constructs an AuthViewModel object with the given Application context.
     *
     * @param application The Application context of the calling component.
     */
    public AuthViewModel(@NonNull Application application) {
        super(application);
        repository = new GoogleAuthRepository(application);
        errorLiveData = new MutableLiveData<>();
        setUserLiveData(repository);
        setNetworkErrorLiveData(repository);
    }

    public MutableLiveData<GoogleAuthRepository.Error> getErrorLiveData() {
        return errorLiveData;
    }

    /**
     * Initiates Google Sign-In authentication with the provided data from the intent.
     *
     * @param data Intent containing the Google Sign-In result data.
     */
    public void authWithGoogle(Intent data) {
        repository.authWithGoogle(data);
    }

}
