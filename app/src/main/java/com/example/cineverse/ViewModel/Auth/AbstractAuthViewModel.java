package com.example.cineverse.ViewModel.Auth;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseUser;

public abstract class AbstractAuthViewModel extends AndroidViewModel {

    protected MutableLiveData<FirebaseUser> userLiveData;

    public AbstractAuthViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<FirebaseUser> getUserLiveData() {
        return userLiveData;
    }

    protected abstract void setUserLiveData();

}