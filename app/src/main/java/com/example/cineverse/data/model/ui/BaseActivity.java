package com.example.cineverse.data.model.ui;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cineverse.theme.LanguageController;
import com.example.cineverse.theme.ThemeModeController;

import java.util.Locale;

public class BaseActivity extends AppCompatActivity {

    private ThemeModeController themeModeController;
    private LanguageController languageController;
    private Locale mCurrentLocale;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        themeModeController = new ThemeModeController(getBaseContext());
        languageController = new LanguageController(getBaseContext());
        setLocale();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mCurrentLocale = getResources().getConfiguration().getLocales().get(0);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Locale locale = languageController.getLocale();
        if (!locale.equals(mCurrentLocale)) {
            mCurrentLocale = locale;
            recreate();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setThemeMode();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setLocale();
    }

    public ThemeModeController getThemeModeController() {
        return themeModeController;
    }

    public LanguageController getLanguageController() {
        return languageController;
    }

    private void setThemeMode() {
        themeModeController = new ThemeModeController(getBaseContext());
        String themeMode = themeModeController.getThemeMode();
        themeModeController.applyTheme(getDelegate(), themeMode);
    }

    private void setLocale() {
        final Resources resources = getResources();
        final Configuration configuration = resources.getConfiguration();
        final Locale locale = languageController.getLocale();
        if (!configuration.getLocales().get(0).equals(locale)) {
            configuration.setLocale(locale);
            resources.updateConfiguration(configuration, null);
        }
    }

}
