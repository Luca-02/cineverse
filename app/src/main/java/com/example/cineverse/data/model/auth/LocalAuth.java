package com.example.cineverse.data.model.auth;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.cineverse.data.model.user.User;
import com.google.gson.Gson;

/**
 * The {@code LocalAuth} class provides methods for managing local authentication-related data
 * using {@link SharedPreferences}. It includes functionality for storing and retrieving user
 * information locally.
 */
public class LocalAuth {

    /**
     * The name of the shared preferences file.
     */
    public static final String PREF_NAME = "UserPreferences";

    /**
     * The key used to store and retrieve user information in the shared preferences.
     */
    public static final String USER_KEY = "user";

    private static LocalAuth instance = null;

    /**
     * Private constructor to enforce the singleton pattern.
     */
    private LocalAuth() {}

    /**
     * Gets the singleton instance of the {@code LocalAuth} class.
     *
     * @return The {@code LocalAuth} instance.
     */
    public static LocalAuth getInstance() {
        if (instance == null) {
            instance = new LocalAuth();
        }
        return instance;
    }

    /**
     * Retrieves the stored user information from shared preferences.
     *
     * @param context The context used to access shared preferences.
     * @return The {@code User} object representing the stored user information,
     *         or {@code null} if no user information is stored.
     */
    public User getUser(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String userJson = preferences.getString(USER_KEY, null);

        if (userJson != null) {
            Gson gson = new Gson();
            return gson.fromJson(userJson, User.class);
        } else {
            return null;
        }
    }

    /**
     * Clears the stored user information from shared preferences.
     *
     * @param context The context used to access shared preferences.
     */
    public void clearUser(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(USER_KEY);
        editor.apply();
    }

}
