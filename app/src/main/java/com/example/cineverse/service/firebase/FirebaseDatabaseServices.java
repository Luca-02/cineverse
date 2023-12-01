package com.example.cineverse.service.firebase;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * The {@link FirebaseDatabaseServices} class provides a centralized access point for interacting
 * with the Firebase Realtime Database.
 */
public class FirebaseDatabaseServices {

    /**
     * Firebase Realtime Database reference instance.
     */
    public static final DatabaseReference database = FirebaseDatabase.getInstance().getReference();

}
