package com.example.cineverse.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.cineverse.Repository.AuthRepository;
import com.google.firebase.auth.FirebaseUser;

/**
 * The AbstractAuthViewModel class serves as the base class for ViewModels related to authentication
 * functionality. It extends AndroidViewModel and provides MutableLiveData instances for observing
 * the current user and network error states. Subclasses of AbstractAuthViewModel are expected to
 * handle user authentication logic and communicate changes in user authentication status and network
 * errors through LiveData objects.
 */
public abstract class AbstractAuthViewModel extends AndroidViewModel {

    protected MutableLiveData<FirebaseUser> userLiveData;
    protected MutableLiveData<Boolean> networkErrorLiveData;

    /**
     * Constructs an AbstractAuthViewModel object with the given Application context.
     *
     * @param application The Application context of the calling component.
     */
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

    /**
     * Clears the networkErrorLiveData object.
     */
    public void clearNetworkErrorLiveData() {
        networkErrorLiveData = new MutableLiveData<>();
    }

}