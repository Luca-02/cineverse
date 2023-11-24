package com.example.cineverse.repository.storage.firebase;

import com.google.firebase.database.DatabaseReference;

public abstract class AbstractUserFirebaseRepository
        extends AbstractFirebaseRepository {

    // Firebase realtime database "usernames" reference instance.
    protected static final DatabaseReference usernamesDatabase = database.child("usernames");

    // Firebase realtime database "users" reference instance.
    protected static final DatabaseReference usersDatabase = database.child("users");

    public interface Callback<T> {
        void onCallback(T data);
    }

}
