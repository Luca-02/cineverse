package com.example.cineverse.service.firebase;

import com.example.cineverse.utils.mapper.ContentTypeMappingManager;
import com.google.firebase.database.DatabaseReference;

public class WatchlistFirebaseDatabaseService
        extends FirebaseDatabaseService {

    /**
     * Firebase Realtime Database "watchlist" reference instance.
     */
    public static final DatabaseReference watchlistDatabase = database.child("watchlist");

}
