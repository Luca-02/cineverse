package com.example.cineverse.viewmodel.verified_account;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.cineverse.repository.auth.logged.VerifiedAccountRepository;
import com.example.cineverse.viewmodel.AbstractLoggedViewModel;

/**
 * The {@link VerifiedAccountViewModel} class represents the ViewModel for a verified user's account.
 * It extends the {@link AbstractLoggedViewModel} class and provides access to the associated repository.
 */
public class VerifiedAccountViewModel
        extends AbstractLoggedViewModel<VerifiedAccountRepository> {

    /**
     * Constructs a {@link VerifiedAccountViewModel} object with the given {@link Application}.
     *
     * @param application The {@link Application} of the calling component.
     */
    public VerifiedAccountViewModel(@NonNull Application application) {
        super(application, new VerifiedAccountRepository(application.getApplicationContext()));
    }

}
