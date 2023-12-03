package com.example.cineverse.viewmodel.logged.verified_account.section.account;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.cineverse.viewmodel.logged.verified_account.VerifiedAccountViewModel;

public class AccountViewModel
        extends VerifiedAccountViewModel {

    /**
     * Constructs a {@link AccountViewModel} object with the given {@link Application}.
     *
     * @param application The {@link Application} of the calling component.
     */
    public AccountViewModel(@NonNull Application application) {
        super(application);
    }

}
