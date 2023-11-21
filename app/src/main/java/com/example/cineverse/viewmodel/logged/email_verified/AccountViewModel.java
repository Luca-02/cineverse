package com.example.cineverse.viewmodel.logged.email_verified;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.cineverse.interfaces.logged.email_verified.account.IAccount;
import com.example.cineverse.repository.logged.email_verified.account.AccountRepository;
import com.example.cineverse.viewmodel.AbstractLoggedViewModel;

public class AccountViewModel
        extends AbstractLoggedViewModel<AccountRepository>
        implements IAccount {

    /**
     * Constructs a HomeViewModel object with the given Application context.
     *
     * @param application The Application context of the calling component.
     */
    public AccountViewModel(@NonNull Application application) {
        super(application, new AccountRepository(application));
    }

    /**
     * Initiates the user logout process.
     */
    @Override
    public void logOut() {
        repository.logOut();
    }

}
