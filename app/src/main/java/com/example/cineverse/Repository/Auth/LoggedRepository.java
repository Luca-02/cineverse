package com.example.cineverse.Repository.Auth;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.example.cineverse.Repository.AuthRepository;

public class LoggedRepository extends AuthRepository {

    private final MutableLiveData<Boolean> loggedOutLiveData;

    public LoggedRepository(Application application) {
        super(application);
        loggedOutLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<Boolean> getLoggedOutLiveData() {
        return loggedOutLiveData;
    }

    public void logOut() {
        firebaseAuth.signOut();
        loggedOutLiveData.postValue(true);
    }

}
