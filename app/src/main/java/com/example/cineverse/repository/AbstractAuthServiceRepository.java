package com.example.cineverse.repository;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.example.cineverse.interfaces.IAuthServices;

/**
 * The AbstractAuthServiceRepository class extends AbstractAuthRepository and serves as a base class
 * for authentication service repositories. It provides MutableLiveData for observing authentication-related errors.
 */
public abstract class AbstractAuthServiceRepository
        extends AbstractAuthRepository
        implements IAuthServices {

    protected final MutableLiveData<Error> errorLiveData;

    /**
     * Constructs an AbstractAuthServiceRepository object with the given Application context and
     * initializes MutableLiveData for observing authentication-related errors.
     *
     * @param application The Application context of the calling component.
     */
    public AbstractAuthServiceRepository(Application application) {
        super(application);
        errorLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<Error> getErrorLiveData() {
        return errorLiveData;
    }

}
