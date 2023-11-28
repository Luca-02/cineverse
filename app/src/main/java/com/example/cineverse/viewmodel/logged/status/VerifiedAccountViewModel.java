package com.example.cineverse.viewmodel.logged.status;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.cineverse.repository.classes.logged.status.VerifiedAccountRepository;
import com.example.cineverse.repository.interfaces.logged.status.IVerifiedAccount;
import com.example.cineverse.viewmodel.logged.AbstractLoggedViewModel;

public class VerifiedAccountViewModel
        extends AbstractLoggedViewModel<VerifiedAccountRepository>
        implements IVerifiedAccount {

    /**
     * Constructs a {@link VerifiedAccountViewModel} object with the given {@link Application}.
     *
     * @param application The {@link Application} of the calling component.
     */
    public VerifiedAccountViewModel(@NonNull Application application) {
        super(application, new VerifiedAccountRepository(application.getBaseContext()));
    }

    /**
     * Initiates the user logout process.
     */
    @Override
    public void logOut() {
        repository.logOut();
    }

}
