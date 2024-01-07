package com.example.cineverse.service.firebase;

import com.google.firebase.database.DatabaseReference;

public class ReviewFirebaseDatabaseService
        extends UserFirebaseDatabaseService {

    /**
     * Firebase Realtime Database "reviews" reference instance.
     */
    public static final DatabaseReference reviewsDatabase = database.child("reviews");

}
