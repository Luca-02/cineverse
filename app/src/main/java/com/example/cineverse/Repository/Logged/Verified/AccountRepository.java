package com.example.cineverse.Repository.Logged.Verified;

import android.app.Application;

import com.example.cineverse.Interface.Logged.Verified.IAccount;
import com.example.cineverse.Repository.AbstractLoggedRepository;

public class AccountRepository
        extends AbstractLoggedRepository
        implements IAccount {

    /**
     * Constructs a HomeRepository object with the given Application context.
     *
     * @param application The Application context of the calling component.
     */
    public AccountRepository(Application application) {
        super(application);
    }

}
