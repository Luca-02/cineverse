package com.example.cineverse.viewmodel.logged.verified_account;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.cineverse.repository.classes.logged.status.VerifiedAccountRepository;
import com.example.cineverse.repository.interfaces.logged.status.IVerifiedAccount;
import com.example.cineverse.viewmodel.logged.AbstractLoggedViewModel;

public abstract class AbstractVerifiedAccountViewModel
        extends AbstractLoggedViewModel<VerifiedAccountRepository>
        implements IVerifiedAccount {

    /**
     * Constructs a {@link AbstractVerifiedAccountViewModel} object with the given {@link Application}.
     *
     * @param application The {@link Application} of the calling component.
     */
    public AbstractVerifiedAccountViewModel(@NonNull Application application) {
        super(application, new VerifiedAccountRepository(application.getBaseContext()));
    }

}
