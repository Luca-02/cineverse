package com.example.cineverse.viewmodel.logged.verified_account.section.home;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.cineverse.viewmodel.logged.verified_account.AbstractVerifiedAccountViewModel;

public class HomeViewModel
        extends AbstractVerifiedAccountViewModel {

    /**
     * Constructs an {@link HomeViewModel} object with the given {@link Application}.
     *
     * @param application The {@link Application} of the calling component.
     */
    public HomeViewModel(@NonNull Application application) {
        super(application);
    }

}
