package com.example.cineverse.viewmodel.auth;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.cineverse.data.model.user.User;
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
        implements AbstractAuthRepository.AuthCallback {

    private MutableLiveData<AbstractAuthRepository.Error> errorLiveData;

    /**
     * Constructs an {@link AbstractAuthServicesViewModel} object with the given {@link Application} and initializes
     * {@link MutableLiveData} for observing authentication-related errors.
     *
     * @param application The {@link Application} of the calling component.
     * @param repository  The {@link AbstractAuthRepository} associated with the ViewModel.
     */
    public AbstractAuthServicesViewModel(@NonNull Application application, T repository) {
        super(application, repository);
    }

    public MutableLiveData<AbstractAuthRepository.Error> getErrorLiveData() {
        if (errorLiveData == null) {
            errorLiveData = new MutableLiveData<>();
        }
        return errorLiveData;
    }

    /**
     * Clears the {@link MutableLiveData} instance for authentication-related errors status.
     * This method is typically used when the network error state is consumed, and you don't want
     * to keep the last state in the LiveData.
     */
    public void clearErrorLiveData() {
        errorLiveData = new MutableLiveData<>();
    }

    /**
     * Overrides the {@link AbstractAuthRepository.AuthCallback#onError(AbstractAuthRepository.Error)} method to handle
     * authentication-related errors and update the error LiveData.
     *
     * @param error The authentication-related error.
     */
    @Override
    public void onError(AbstractAuthRepository.Error error) {
        getErrorLiveData().postValue(error);
    }

    /**
     * Overrides the {@link AbstractAuthRepository.AuthCallback#onUserAuthentication(User)} method
     * to handle successful user authentication and update the user LiveData.
     *
     * @param user The authenticated user.
     */
    @Override
    public void onUserAuthentication(User user) {
        getUserLiveData().postValue(user);
    }

}
