package com.example.cineverse.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.cineverse.data.model.User;
import com.example.cineverse.repository.UserRepository;
import com.example.cineverse.service.NetworkCallback;
import com.example.cineverse.data.model.CurrentUser;
import com.example.cineverse.utils.constant.GlobalConstant;

/**
 * The {@link AbstractUserViewModel} class serves as the base class for ViewModels related to authentication
 * functionality. It extends {@link AndroidViewModel} and provides {@link MutableLiveData} instances for observing
 * the current user and network error states. Subclasses of {@link AbstractUserViewModel} are expected to
 * handle user authentication logic and communicate changes in user authentication status and network
 * errors through LiveData objects.
 *
 * @param <T> The type of {@link UserRepository} associated with the ViewModel.
 */
public abstract class AbstractUserViewModel<T extends UserRepository>
        extends AndroidViewModel
        implements NetworkCallback {

    /**
     * The repository responsible for handling user authentication and data operations.
     */
    protected T userRepository;
    private MutableLiveData<Boolean> networkErrorLiveData;

    /**
     * Constructs an {@link AbstractUserViewModel} object with the given {@link Application}.
     *
     * @param application The {@link Application} of the calling component.
     * @param userRepository  The {@link UserRepository} associated with the ViewModel.
     */
    public AbstractUserViewModel(@NonNull Application application, T userRepository) {
        super(application);
        this.userRepository = userRepository;

        // Check if there is a currently authenticated user and update userLiveData if available.
        CurrentUser.getInstance().handleCurrentUser(getCurrentUser());
    }

    public MutableLiveData<User> getUserLiveData() {
        return CurrentUser.getInstance().getCurrentUserMutableLiveData();
    }

    public MutableLiveData<Boolean> getNetworkErrorLiveData() {
        if (networkErrorLiveData == null) {
            networkErrorLiveData = new MutableLiveData<>();
        }
        return networkErrorLiveData;
    }

    /**
     * Initiates the process to gets the currently authenticated user.
     *
     * @return The currently authenticated {@link User} object or {@code null} if not authenticated.
     */
    public User getCurrentUser() {
        return userRepository.getCurrentUser();
    }

    /**
     * Initiates the process of checking whether the email of the current user is verified.
     *
     * @return {@code true} if the user's email is verified, {@code false} otherwise.
     */
    public boolean isEmailVerified() {
        return userRepository.isEmailVerified();
    }

    /**
     * Overrides the {@link NetworkCallback#onNetworkUnavailable()} method
     * to handle the network error event and update the network error LiveData.
     * Trigger network error {@link MutableLiveData}.
     */
    @Override
    public void onNetworkUnavailable() {
        getNetworkErrorLiveData().postValue(true);
    }

}