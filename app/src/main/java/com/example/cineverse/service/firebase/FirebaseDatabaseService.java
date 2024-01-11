package com.example.cineverse.service.firebase;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.cineverse.data.model.content.AbstractContent;
import com.example.cineverse.service.NetworkCallback;
import com.example.cineverse.utils.NetworkUtils;
import com.example.cineverse.utils.mapper.ContentTypeMappingManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * The {@link FirebaseDatabaseService} class provides a centralized access point for interacting
 * with the Firebase Realtime Database.
 */
public class FirebaseDatabaseService {

    /**
     * Firebase Realtime Database reference instance.
     */
    public static final DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    public String getContentType(Class<? extends AbstractContent> content) {
        return ContentTypeMappingManager.getContentType(content);
    }

    public boolean isNetworkAvailable(Context context, NetworkCallback networkCallback) {
        if (NetworkUtils.isNetworkAvailable(context)) {
            return true;
        } else {
            networkCallback.onNetworkUnavailable();
            return false;
        }
    }

}
