package com.example.cineverse.data.source.user;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.cineverse.data.model.User;
import com.example.cineverse.data.model.content.AbstractContent;
import com.example.cineverse.service.firebase.FirebaseCallback;
import com.example.cineverse.service.firebase.WatchlistFirebaseDatabaseService;
import com.example.cineverse.utils.NetworkUtils;
import com.example.cineverse.utils.mapper.ContentTypeMappingManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

public class WatchlistFirebaseSource
        extends WatchlistFirebaseDatabaseService {

    private final Context context;

    public WatchlistFirebaseSource(Context context) {
        this.context = context;
    }

    public void getDateForContentInWatchlist(User user, AbstractContent content, FirebaseCallback<String> firebaseCallback) {
        if (NetworkUtils.isNetworkAvailable(context)) {
            String contentType = ContentTypeMappingManager.getContentType(content.getClass());
            if (contentType != null) {
                DatabaseReference userWatchlistRef = watchlistDatabase
                        .child(contentType)
                        .child(user.getUid())
                        .child(String.valueOf(content.getId()));

                userWatchlistRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            String date = dataSnapshot.getValue(String.class);
                            firebaseCallback.onCallback(date);
                        } else {
                            firebaseCallback.onCallback(null);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        firebaseCallback.onCallback(null);
                    }
                });
            }
        } else {
            firebaseCallback.onNetworkUnavailable();
        }
    }

    public void addContentToWatchlist(User user, AbstractContent content, FirebaseCallback<Boolean> firebaseCallback) {
        if (NetworkUtils.isNetworkAvailable(context)) {
            String contentType = ContentTypeMappingManager.getContentType(content.getClass());
            if (contentType != null) {
                DatabaseReference userMovieWatchlistRef = watchlistDatabase
                        .child(contentType)
                        .child(user.getUid())
                        .child(String.valueOf(content.getId()));

                userMovieWatchlistRef.runTransaction(new Transaction.Handler() {
                    @NonNull
                    @Override
                    public Transaction.Result doTransaction(@NonNull MutableData currentData) {
                        if (currentData.getValue() == null) {
                            currentData.setValue(ServerValue.TIMESTAMP);
                            return Transaction.success(currentData);
                        } else {
                            return Transaction.abort();
                        }
                    }

                    @Override
                    public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {
                        if (committed && error == null) {
                            firebaseCallback.onCallback(true);
                        } else {
                            firebaseCallback.onCallback(false);
                        }
                    }
                });
            }
        } else {
            firebaseCallback.onNetworkUnavailable();
        }
    }

}
