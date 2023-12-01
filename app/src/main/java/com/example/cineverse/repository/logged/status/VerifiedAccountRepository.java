package com.example.cineverse.repository.logged.status;

import android.content.Context;

import com.example.cineverse.repository.logged.AbstractLoggedRepository;

public class VerifiedAccountRepository
        extends AbstractLoggedRepository {

    /**
     * Constructs a {@link VerifiedAccountRepository} object with the given application {@link Context}.
     *
     * @param context The application {@link Context} of the calling component.
     */
    public VerifiedAccountRepository(Context context) {
        super(context);
    }

}
