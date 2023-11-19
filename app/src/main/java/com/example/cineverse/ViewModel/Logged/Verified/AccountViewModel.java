package com.example.cineverse.ViewModel.Logged.Verified;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.cineverse.Interface.Logged.Verified.IAccount;
import com.example.cineverse.Repository.Logged.Verified.AccountRepository;
import com.example.cineverse.ViewModel.AbstractLoggedViewModel;

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
