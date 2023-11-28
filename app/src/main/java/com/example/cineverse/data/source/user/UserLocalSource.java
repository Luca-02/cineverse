package com.example.cineverse.data.source.user;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.cineverse.data.model.auth.LocalAuth;
import com.example.cineverse.data.model.user.User;
import com.google.gson.Gson;

/**
 * The {@code UserLocalSource} class provides methods to interact with the local storage (SharedPreferences)
 * for user-related operations. It includes a method to save user information locally.
 */
public class UserLocalSource {

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
        SharedPreferences preferences = context.getSharedPreferences(LocalAuth.PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        Gson gson = new Gson();
        String userJson = gson.toJson(user);

        editor.putString(LocalAuth.USER_KEY, userJson);
        editor.apply();
    }

}
