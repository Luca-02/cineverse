package com.example.cineverse.theme;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatDelegate;

import org.jetbrains.annotations.NotNull;

public class ThemeModeController {

    public static final String LIGHT_MODE = "light";
    public static final String DARK_MODE = "dark";
    public static final String SYSTEM_DEFAULT_MODE = "systemDefault";

    public static final String PREF_NAME = "AppThemeMode";
    public static final String THEME_MODE_KEY = "themeMode";

    private final Context context;

    public ThemeModeController(Context context) {
        this.context = context;
    }

    public void applyTheme(@NotNull AppCompatDelegate delegate, String themePref) {
        if (themePref == null) {
            delegate.setLocalNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        } else {
            switch (themePref) {
                case LIGHT_MODE: {
                    delegate.setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    setThemeMode(themePref);
                    break;
                }
                case DARK_MODE: {
                    delegate.setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    setThemeMode(themePref);
                    break;
                }
                case SYSTEM_DEFAULT_MODE: {
                    delegate.setLocalNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                    setThemeMode(themePref);
                    break;
                }
            }
        }
    }

    public void setThemeMode(String mode) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(THEME_MODE_KEY, mode);
        editor.apply();
    }

    public String getThemeMode() {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return preferences.getString(THEME_MODE_KEY, null);
    }

    public static void clearThemeMode(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(THEME_MODE_KEY);
        editor.apply();
    }

}