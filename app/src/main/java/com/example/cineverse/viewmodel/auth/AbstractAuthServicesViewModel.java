package com.example.cineverse.viewmodel.auth;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.cineverse.data.model.User;
import com.example.cineverse.repository.auth.AbstractAuthRepository;
import com.example.cineverse.viewmodel.AbstractUserViewModel;

/**
 * The {@link AbstractAuthServicesViewModel} class extends {@link AbstractUserViewModel} and serves as the base class
 * for ViewModels related to authentication services. It provides {@link MutableLiveData} for observing
 * authentication-related errors.
 *
 * @param <T> The type of {@link AbstractAuthRepository} associated with the ViewModel.
 */
public abstract class AbstractAuthServicesViewModel<T extends AbstractAuthRepository>
        extends AbstractUserViewModel<T>
        implements AbstractAuthRepository.AuthErrorCallback {

    private MutableLiveData<AbstractAuthRepository.Error> errorLiveData;

    /**
     * Constructs an {@link AbstractAuthServicesViewModel} object with the given {@link Application} and initializes
     * {@link MutableLiveData} for observing authentication-related errors.
     *
     * @param application The {@link Application} of the calling component.
     * @param userRepository  The {@link AbstractAuthRepository} associated with the ViewModel.
     */
    public AbstractAuthServicesViewModel(@NonNull Application application, T userRepository) {
        super(application, userRepository);
        userRepository.setAuthCallback(this);
    }

    public MutableLiveData<AbstractAuthRepository.Error> getErrorLiveData() {
        if (errorLiveData == null) {
            errorLiveData = new MutableLiveData<>();
        }
        return errorLiveData;
    }

    /**
     * Overrides the {@link AbstractAuthRepository.AuthErrorCallback#onError(AbstractAuthRepository.Error)} method to handle
     * authentication-related errors and update the error LiveData.
     * Clears authentication-related error {@link MutableLiveData}.
     *
     * @param error The authentication-related error.
     */
    @Override
    public void onError(AbstractAuthRepository.Error error) {
        getErrorLiveData().postValue(error);
    }

    /**
     * Overrides the {@link AbstractAuthRepository.AuthErrorCallback#onUserAuthentication(User)} method
     * to handle successful user authentication and update the user LiveData.
     *
     * @param user The authenticated user.
     */
    @Override
    public void onUserAuthentication(User user) {
        getUserLiveData().postValue(user);
    }

}
