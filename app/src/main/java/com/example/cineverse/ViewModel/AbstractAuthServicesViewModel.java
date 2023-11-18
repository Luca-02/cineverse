package com.example.cineverse.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.cineverse.Interface.IAuthServices;
import com.example.cineverse.Repository.AbstractAuthRepository;
import com.example.cineverse.Repository.AbstractAuthServiceRepository;

/**
 * The AbstractAuthServicesViewModel class extends AbstractAuthViewModel and serves as the base class
 * for ViewModels related to authentication services. It provides MutableLiveData for observing
 * authentication-related errors.
 *
 * @param <T> The type of repository associated with the ViewModel.
 */
public abstract class AbstractAuthServicesViewModel<T extends AbstractAuthServiceRepository>
        extends AbstractAuthViewModel<T>
        implements IAuthServices {

    private MutableLiveData<AbstractAuthRepository.Error> errorLiveData;

    /**
     * Constructs an AbstractAuthServicesViewModel object with the given Application context and initializes
     * MutableLiveData for observing authentication-related errors.
     *
     * @param application The Application context of the calling component.
     * @param repository  The repository associated with the ViewModel.
     */
    public AbstractAuthServicesViewModel(@NonNull Application application, T repository) {
        super(application, repository);
        errorLiveData = repository.getErrorLiveData();
    }

    public MutableLiveData<AbstractAuthRepository.Error> getErrorLiveData() {
        return errorLiveData;
    }

    /**
     * Clears the MutableLiveData instance for authentication-related errors status.
     * This method is typically used when the network error state is consumed, and you don't want
     * to keep the last state in the LiveData.
     */
    public void clearErrorLiveData() {
        errorLiveData = new MutableLiveData<>();
    }

}
