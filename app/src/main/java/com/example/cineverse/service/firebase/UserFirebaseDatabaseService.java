package com.example.cineverse.service.firebase;

import com.google.firebase.database.DatabaseReference;

/**
 * The {@link UserFirebaseDatabaseService} class extends {@link FirebaseDatabaseService} and
 * provides references to specific nodes ("usernames" and "users") in the Firebase Realtime Database.
 * Additionally, it includes callback interfaces for asynchronous operations with one parameter
 * and network availability checks.
 */
public class UserFirebaseDatabaseService
        extends FirebaseDatabaseService {

    /**
     * Firebase Realtime Database "usernames" reference instance.
     */
    public static final DatabaseReference usernamesDatabase = database.child("usernames");

    /**
     * Firebase Realtime Database "users" reference instance.
     */
    public static final DatabaseReference usersDatabase = database.child("users");

}
