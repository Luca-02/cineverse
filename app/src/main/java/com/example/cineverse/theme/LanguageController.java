package com.example.cineverse.theme;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;

import com.example.cineverse.R;

import java.util.Locale;

public class LanguageController {

    public static final String PREF_NAME = "AppLanguage";
    public static final String LANGUAGE_CODE_KEY = "languageCode";

    private final Context context;
    private final String[] languageCodes;

    public LanguageController(Context context) {
        this.context = context;
        languageCodes = context.getResources().getStringArray(R.array.language_codes);
    }

    public String[] getLanguageCodes() {
        return languageCodes;
    }

    private void setLanguage(String language) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(LANGUAGE_CODE_KEY, language);
        editor.apply();
    }

    public void setLanguage(Activity activity, String selectedLanguageCode) {
        String newLanguageCode = null;
        if ("default".equals(selectedLanguageCode)) {
            setLocaleToDefault(activity);
        } else {
            newLanguageCode = selectedLanguageCode;
            setLocale(activity, selectedLanguageCode);
        }
        setLanguage(newLanguageCode);
    }

    public String getLanguage() {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return preferences.getString(LANGUAGE_CODE_KEY, null);
    }

    public void setLocaleToDefault(Activity activity) {
        Resources resources = activity.getResources();
        Configuration config = resources.getConfiguration();
        resources.updateConfiguration(config, null);
        activity.recreate();
    }

    public void setLocale(Activity activity, String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Resources resources = activity.getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());
        activity.recreate();
    }

    public Locale getLocale() {
        String currentLanguage = getLanguage();
        if (currentLanguage == null) {
            return Locale.getDefault();
        } else {
            return new Locale(currentLanguage);
        }
    }

}
