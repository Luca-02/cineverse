package com.example.cineverse.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.cineverse.repository.classes.UserRepository;
import com.example.cineverse.repository.interfaces.IUser;

public class ConcreteUserViewModel
        extends AbstractUserViewModel<UserRepository>
        implements IUser {

    /**
     * Constructs an {@link ConcreteUserViewModel} object with the given {@link Application}.
     *
     * @param application The {@link Application} of the calling component.
     */
    public ConcreteUserViewModel(@NonNull Application application) {
        super(application, new UserRepository(application.getBaseContext()));
    }

}
