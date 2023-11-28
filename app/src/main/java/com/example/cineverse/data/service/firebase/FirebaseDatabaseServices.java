package com.example.cineverse.data.service.firebase;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * The {@code FirebaseDatabaseServices} class provides a centralized access point for interacting
 * with the Firebase Realtime Database.
 */
public class FirebaseDatabaseServices {

    /**
     * Firebase Realtime Database reference instance.
     */
    public static final DatabaseReference database = FirebaseDatabase.getInstance().getReference();

}
