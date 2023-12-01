package com.example.cineverse.service.firebase;

import com.google.firebase.database.DatabaseReference;

/**
 * The {@link UserFirebaseDatabaseServices} class extends {@link FirebaseDatabaseServices} and
 * provides references to specific nodes ("usernames" and "users") in the Firebase Realtime Database.
 * Additionally, it includes callback interfaces for asynchronous operations with one parameter
 * and network availability checks.
 */
public class UserFirebaseDatabaseServices
        extends FirebaseDatabaseServices {

    /**
     * Firebase Realtime Database "usernames" reference instance.
     */
    public static final DatabaseReference usernamesDatabase = database.child("usernames");

    /**
     * Firebase Realtime Database "users" reference instance.
     */
    public static final DatabaseReference usersDatabase = database.child("users");

    /**
     * The {@code NetworkCallback} interface defines a method to handle cases when the network is
     * unavailable.
     */
    private interface NetworkCallback {
        void onNetworkUnavailable();
    }

    /**
     * The {@code CallbackOneParam<T>} interface extends {@code NetworkCallback} and defines a method
     * to handle asynchronous callbacks with one parameter.
     *
     * @param <T> The type of the parameter for the callback.
     */
    public interface Callback<T> extends NetworkCallback {
        void onCallback(T data);
    }

}
