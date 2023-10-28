package com.example.cineverse.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.cineverse.Repository.AuthRepository;
import com.google.firebase.auth.FirebaseUser;

public abstract class AbstractAuthViewModel extends AndroidViewModel {

    protected MutableLiveData<FirebaseUser> userLiveData;
    protected MutableLiveData<Boolean> networkErrorLiveData;

    public AbstractAuthViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<FirebaseUser> getUserLiveData() {
        return userLiveData;
    }

    protected void setUserLiveData(AuthRepository repository) {
        userLiveData = repository.getUserLiveData();
    }

    public MutableLiveData<Boolean> getNetworkErrorLiveData() {
        return networkErrorLiveData;
    }

    public void setNetworkErrorLiveData(AuthRepository repository) {
        networkErrorLiveData = repository.getNetworkErrorLiveData();
    }

}