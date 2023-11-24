package com.example.cineverse.repository.storage.firebase;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public abstract class AbstractFirebaseRepository {

    // Firebase realtime database reference instance.
    protected static final DatabaseReference database = FirebaseDatabase.getInstance().getReference();

}
