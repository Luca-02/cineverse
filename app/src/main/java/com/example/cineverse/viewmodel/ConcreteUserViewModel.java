package com.example.cineverse.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.cineverse.data.model.User;
import com.example.cineverse.repository.UserRepository;

public class ConcreteUserViewModel
        extends AbstractUserViewModel<UserRepository>
        implements UserRepository.SynchronizeLocalUserCallback {

    /**
     * Constructs an {@link ConcreteUserViewModel} object with the given {@link Application}.
     *
     * @param application The {@link Application} of the calling component.
     */
    public ConcreteUserViewModel(@NonNull Application application) {
        super(application, new UserRepository(application.getApplicationContext()));
        userRepository.setSynchronizedUserCallback(this);
    }

    /**
     * Initiates the process to gets the synchronized currently authenticated user.
     */
    public void synchronizeCurrentUser(User currentUser) {
        userRepository.synchronizeLocalUser(currentUser);
    }

    @Override
    public void onSynchronizedLocalUser(User currentUser) {
        getUserLiveData().postValue(currentUser);
    }

}
