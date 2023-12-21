package com.example.cineverse.data.source.user;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.cineverse.data.model.User;
import com.google.gson.Gson;

/**
 * The {@link UserLocalSource} class provides methods to interact with the local storage (SharedPreferences)
 * for user-related operations. It includes a method to save user information locally.
 */
public class UserLocalSource {

    /**
     * The name of the shared preferences file.
     */
    public static final String PREF_NAME = "UserPreferences";

    /**
     * The key used to store and retrieve user information in the shared preferences.
     */
    public static final String USER_KEY = "user";

    private final Context context;

    public UserLocalSource(Context context) {
        this.context = context;
    }

    /**
     * Saves user information locally in SharedPreferences.
     *
     * @param user The user to be saved locally.
     */
    public void saveUser(User user) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        Gson gson = new Gson();
        String userJson = gson.toJson(user);

        editor.putString(USER_KEY, userJson);
        editor.apply();
    }

    /**
     * Retrieves the stored user information from shared preferences.
     *
     * @return The {@link User} object representing the stored user information,
     *         or {@code null} if no user information is stored.
     */
    public User getUser() {
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
     */
    public void clearUser() {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(USER_KEY);
        editor.apply();
    }

}
