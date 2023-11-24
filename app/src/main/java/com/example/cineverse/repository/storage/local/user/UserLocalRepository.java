package com.example.cineverse.repository.storage.local.user;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.cineverse.model.User;
import com.google.gson.Gson;

public class UserLocalRepository {

    private static final String PREF_NAME = "UserPreferences";
    private static final String USER_KEY = "user";

    private final Context context;

    public UserLocalRepository(Context context) {
        this.context = context;
    }

    public void saveUser(User user) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        Gson gson = new Gson();
        String userJson = gson.toJson(user);

        editor.putString(USER_KEY, userJson);
        editor.apply();
    }

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

    public void clearUser() {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(USER_KEY);
        editor.apply();
    }

}
