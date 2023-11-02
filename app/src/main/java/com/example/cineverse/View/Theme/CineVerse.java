package com.example.cineverse.View.Theme;

import android.app.Application;

import com.google.android.material.color.DynamicColors;

/**
 * The CineVerse class represents the custom application class for the CineVerse application.
 */
public class CineVerse extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // Apply dynamic colors to activities if available
        DynamicColors.applyToActivitiesIfAvailable(this);
    }

}
