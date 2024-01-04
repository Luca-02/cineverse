package com.example.cineverse.service.firebase;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * The {@link FirebaseDatabaseService} class provides a centralized access point for interacting
 * with the Firebase Realtime Database.
 */
public class FirebaseDatabaseService {

    /**
     * Firebase Realtime Database reference instance.
     */
    public static final DatabaseReference database = FirebaseDatabase.getInstance().getReference();

}
