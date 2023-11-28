package com.example.cineverse.repository.classes.logged.status;

import android.content.Context;

import com.example.cineverse.repository.classes.logged.AbstractLoggedRepository;
import com.example.cineverse.repository.interfaces.logged.status.IVerifiedAccount;

public class VerifiedAccountRepository
        extends AbstractLoggedRepository
        implements IVerifiedAccount {

    /**
     * Constructs a {@link VerifiedAccountRepository} object with the given application {@link Context}.
     *
     * @param context The application {@link Context} of the calling component.
     */
    public VerifiedAccountRepository(Context context) {
        super(context);
    }

}
