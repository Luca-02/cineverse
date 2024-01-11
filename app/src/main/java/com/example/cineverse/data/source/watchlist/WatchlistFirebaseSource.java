package com.example.cineverse.data.source.watchlist;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.cineverse.data.model.User;
import com.example.cineverse.data.model.content.AbstractContent;
import com.example.cineverse.data.model.content.section.Movie;
import com.example.cineverse.data.model.content.section.Tv;
import com.example.cineverse.data.model.details.section.IContentDetails;
import com.example.cineverse.data.model.details.section.MovieDetails;
import com.example.cineverse.data.model.details.section.TvDetails;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
            String contentType = getContentType(content.getClass());
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

    public void getUserContentWatchlist(User user, String contentType) {
        if (NetworkUtils.isNetworkAvailable(context)) {
            if (contentType != null) {
                DatabaseReference userMovieWatchlistRef = watchlistDatabase
                        .child(contentType)
                        .child(user.getUid());

                userMovieWatchlistRef.orderByValue().addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        List<AbstractContent> watchlistList = new ArrayList<>();
                        if (snapshot.exists() && snapshot.hasChildren()) {
                            for (DataSnapshot reviewSnapshot : snapshot.getChildren()) {
                                String contentIdString = reviewSnapshot.getKey();
                                if (contentIdString != null) {
                                    int contentId = Integer.parseInt(contentIdString);
                                    Long timestamp = reviewSnapshot.getValue(Long.class);

                                    AbstractContent content = null;
                                    if (Objects.equals(contentType, ContentTypeMappingManager.ContentType.MOVIE.getType())) {
                                        content = new Movie(contentId, timestamp);
                                    } else if (Objects.equals(contentType, ContentTypeMappingManager.ContentType.TV.getType())) {
                                        content = new Tv(contentId, timestamp);
                                    }

                                    if (content != null) {
                                        watchlistList.add(content);
                                    }
                                }
                            }
                        }
                        firebaseCallback.onUserContentWatchlist(watchlistList, contentType);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        firebaseCallback.onUserContentWatchlist(null, contentType);
                    }
                });
            }
        } else {
            firebaseCallback.onNetworkUnavailable();
        }
    }

    public void addContentToWatchlist(User user, AbstractContent content) {
        if (NetworkUtils.isNetworkAvailable(context)) {
            String contentType = getContentType(content.getClass());
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
            String contentType = getContentType(content.getClass());
            if (contentType != null) {
                DatabaseReference userMovieWatchlistRef = watchlistDatabase
                        .child(contentType)
                        .child(user.getUid());

                userMovieWatchlistRef.removeValue((error, ref) ->
                        firebaseCallback.onRemovedContentToWatchlist(error == null));
            }
        } else {
            firebaseCallback.onNetworkUnavailable();
        }
    }

}
