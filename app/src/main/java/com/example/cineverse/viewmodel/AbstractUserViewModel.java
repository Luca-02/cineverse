package com.example.cineverse.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.cineverse.data.model.user.User;
import com.example.cineverse.repository.interfaces.IUser;
import com.example.cineverse.repository.classes.AbstractUserRepository;

/**
 * The {@link AbstractUserViewModel} class serves as the base class for ViewModels related to authentication
 * functionality. It extends {@link AndroidViewModel} and provides {@link MutableLiveData} instances for observing
 * the current user and network error states. Subclasses of {@link AbstractUserViewModel} are expected to
 * handle user authentication logic and communicate changes in user authentication status and network
 * errors through LiveData objects.
 *
 * @param <T> The type of {@link AbstractUserRepository} associated with the ViewModel.
 */
public abstract class AbstractUserViewModel<T extends AbstractUserRepository>
        extends AndroidViewModel
        implements IUser {

    public T repository;
    protected MutableLiveData<User> userLiveData;
    protected MutableLiveData<Boolean> networkErrorLiveData;

    /**
     * Constructs an {@link AbstractUserViewModel} object with the given {@link Application}.
     *
     * @param application The {@link Application} of the calling component.
     * @param repository  The {@link AbstractUserRepository} associated with the ViewModel.
     */
    public AbstractUserViewModel(@NonNull Application application, T repository) {
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
     * Clears the {@link MutableLiveData} instance for network error status.
     * This method is typically used when the network error state is consumed, and you don't want
     * to keep the last state in the LiveData.
     */
    public void clearNetworkErrorLiveData() {
        networkErrorLiveData = new MutableLiveData<>();
    }

}