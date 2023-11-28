package com.example.cineverse.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.cineverse.data.model.user.User;
import com.example.cineverse.repository.interfaces.IAuth;
import com.example.cineverse.repository.classes.AbstractAuthRepository;

/**
 * The AbstractAuthViewModel class serves as the base class for ViewModels related to authentication
 * functionality. It extends AndroidViewModel and provides MutableLiveData instances for observing
 * the current user and network error states. Subclasses of AbstractAuthViewModel are expected to
 * handle user authentication logic and communicate changes in user authentication status and network
 * errors through LiveData objects.
 *
 * @param <T> The type of repository associated with the ViewModel.
 */
public abstract class AbstractAuthViewModel<T extends AbstractAuthRepository>
        extends AndroidViewModel
        implements IAuth {

    public T repository;
    protected MutableLiveData<User> userLiveData;
    protected MutableLiveData<Boolean> networkErrorLiveData;

    /**
     * Constructs an AbstractAuthViewModel object with the given Application context.
     *
     * @param application The Application context of the calling component.
     * @param repository  The repository associated with the ViewModel.
     */
    public AbstractAuthViewModel(@NonNull Application application, T repository) {
        super(application);
        this.repository = repository;
        userLiveData = repository.getUserLiveData();
        networkErrorLiveData = repository.getNetworkErrorLiveData();
    }

    public MutableLiveData<User> getUserLiveData() {
        return userLiveData;
    }

    public MutableLiveData<Boolean> getNetworkErrorLiveData() {
        return networkErrorLiveData;
    }

    /**
     * Clears the MutableLiveData instance for network error status.
     * This method is typically used when the network error state is consumed, and you don't want
     * to keep the last state in the LiveData.
     */
    public void clearNetworkErrorLiveData() {
        networkErrorLiveData = new MutableLiveData<>();
    }

}