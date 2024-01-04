package com.example.cineverse.viewmodel.settings.theme_app;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


/**
 * The {@link ThemeViewModel} represents the View Model for Theme Changes
 * Save and Listen to Changes
 */
public class ThemeViewModel extends ViewModel {

    private MutableLiveData<Boolean> isNightMode = new MutableLiveData<>();
    private SharedPreferences sharedPreferences;
    private static final String THEME_PREFERENCES = "theme_prefs";
    private static final String THEME_NIGHT_MODE = "isNightMode";


    public LiveData<Boolean> getNightMode(Context context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(THEME_PREFERENCES, Context.MODE_PRIVATE);
            isNightMode.setValue(sharedPreferences.getBoolean(THEME_NIGHT_MODE, false));
        }
        return isNightMode;
    }
    public void setNightMode(Context context, boolean isNight) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(THEME_PREFERENCES, Context.MODE_PRIVATE);
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(THEME_NIGHT_MODE, isNight);
        editor.apply();
        isNightMode.setValue(isNight);
    }

}
