package com.example.cineverse.data.source.watchlist;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.cineverse.data.model.User;
import com.example.cineverse.data.model.content.AbstractContent;
import com.example.cineverse.service.firebase.WatchlistFirebaseDatabaseService;
import com.example.cineverse.utils.DateTimeUtils;
import com.example.cineverse.utils.NetworkUtils;
import com.example.cineverse.utils.constant.GlobalConstant;
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
    private final WatchlistFirebaseCallback firebaseCallback;

    public WatchlistFirebaseSource(Context context, WatchlistFirebaseCallback firebaseCallback) {
        this.context = context;
        this.firebaseCallback = firebaseCallback;
    }

    public void getTimestampForContentInWatchlist(User user, AbstractContent content) {
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
                            Long timestamp = dataSnapshot.getValue(Long.class);
                            firebaseCallback.onTimestampForContentInWatchlist(timestamp);
                        } else {
                            firebaseCallback.onTimestampForContentInWatchlist(null);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        firebaseCallback.onTimestampForContentInWatchlist(null);
                    }
                });
            }
        } else {
            firebaseCallback.onNetworkUnavailable();
        }
    }

    public void addContentToWatchlist(User user, AbstractContent content) {
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
                        currentData.setValue(ServerValue.TIMESTAMP);
                        return Transaction.success(currentData);
                    }

                    @Override
                    public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {
                        if (committed && error == null && currentData != null) {
                            Long timestamp = currentData.getValue(Long.class);
                            firebaseCallback.onAddedContentToWatchlist(timestamp);
                        } else {
                            firebaseCallback.onAddedContentToWatchlist(null);
                        }
                    }
                });
            }
        } else {
            firebaseCallback.onNetworkUnavailable();
        }
    }

    public void removeContentToWatchlist(User user, AbstractContent content) {
        if (NetworkUtils.isNetworkAvailable(context)) {
            String contentType = ContentTypeMappingManager.getContentType(content.getClass());
            if (contentType != null) {
                DatabaseReference userMovieWatchlistRef = watchlistDatabase
                        .child(contentType)
                        .child(user.getUid());

                userMovieWatchlistRef.removeValue((error, ref) -> {
                    if (error == null) {
                        firebaseCallback.onRemovedContentToWatchlist(true);
                    } else {
                        firebaseCallback.onRemovedContentToWatchlist(false);
                    }
                });
            }
        } else {
            firebaseCallback.onNetworkUnavailable();
        }
    }

}
