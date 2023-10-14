package com.example.cineverse.View.Theme;

import android.app.Application;

import com.google.android.material.color.DynamicColors;

public class CineVerse extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DynamicColors.applyToActivitiesIfAvailable(this);
    }

}
