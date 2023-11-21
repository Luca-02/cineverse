package com.example.cineverse.repository.logged.email_verified.account;

import android.app.Application;

import com.example.cineverse.interfaces.logged.email_verified.account.IAccount;
import com.example.cineverse.repository.AbstractLoggedRepository;

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
