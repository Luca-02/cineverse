package com.example.cineverse.viewmodel.verified_account.section.home;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.cineverse.viewmodel.verified_account.VerifiedAccountViewModel;

/**
 * The {@link HomeViewModel} class extends {@link VerifiedAccountViewModel} and represents the ViewModel
 * for the home section of the application. It provides data for various content sections and genres
 * displayed on the home screen.
 */
public class HomeViewModel
        extends VerifiedAccountViewModel {

    /**
     * Constructs an {@link HomeViewModel} object with the given {@link Application}.
     *
     * @param application The {@link Application} of the calling component.
     */
    public HomeViewModel(@NonNull Application application) {
        super(application);
    }
    
}
