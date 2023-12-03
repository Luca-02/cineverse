package com.example.cineverse.viewmodel.logged.verified_account;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.cineverse.repository.logged.status.VerifiedAccountRepository;
import com.example.cineverse.viewmodel.logged.AbstractLoggedViewModel;

public class VerifiedAccountViewModel
        extends AbstractLoggedViewModel<VerifiedAccountRepository> {

    /**
     * Constructs a {@link VerifiedAccountViewModel} object with the given {@link Application}.
     *
     * @param application The {@link Application} of the calling component.
     */
    public VerifiedAccountViewModel(@NonNull Application application) {
        super(application, new VerifiedAccountRepository(application.getBaseContext()));
    }

}
