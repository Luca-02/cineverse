package com.example.cineverse.repository.classes.logged.email_verified.account;

import android.content.Context;

import com.example.cineverse.repository.classes.logged.AbstractLoggedRepository;
import com.example.cineverse.repository.interfaces.logged.email_verified.account.IAccount;

public class AccountRepository
        extends AbstractLoggedRepository
        implements IAccount {

    /**
     * Constructs a HomeRepository object with the given Application context.
     *
     * @param context The Application context of the calling component.
     */
    public AccountRepository(Context context) {
        super(context);
    }

}
