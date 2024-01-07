package com.example.cineverse.data.source.review;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.cineverse.data.model.User;
import com.example.cineverse.data.model.content.AbstractContent;
import com.example.cineverse.data.model.review.Review;
import com.example.cineverse.data.model.review.UserReview;
import com.example.cineverse.service.firebase.ReviewFirebaseDatabaseService;
import com.example.cineverse.utils.NetworkUtils;
import com.example.cineverse.utils.mapper.ContentTypeMappingManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ReviewFirebaseSource
        extends ReviewFirebaseDatabaseService {

    private final Context context;
    private final ReviewFirebaseCallback firebaseCallback;

    public ReviewFirebaseSource(Context context, ReviewFirebaseCallback firebaseCallback) {
        this.context = context;
        this.firebaseCallback = firebaseCallback;
    }

    public void getContentReviewOfUser(User user, AbstractContent content) {
        if (NetworkUtils.isNetworkAvailable(context)) {
            String contentType = ContentTypeMappingManager.getContentType(content.getClass());
            if (contentType != null) {
                DatabaseReference contentReviewsRef = reviewsDatabase
                        .child(contentType)
                        .child(String.valueOf(content.getId()))
                        .child(user.getUid());

                contentReviewsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Review review = snapshot.getValue(Review.class);
                        firebaseCallback.onContentReviewOfUser(review);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        firebaseCallback.onContentReviewOfUser(null);
                    }
                });
            }
        } else {
            firebaseCallback.onNetworkUnavailable();
        }
    }

    public void addContentReviewOfUser(User user, AbstractContent content, Review review) {
        if (NetworkUtils.isNetworkAvailable(context)) {
            String contentType = ContentTypeMappingManager.getContentType(content.getClass());
            if (contentType != null) {
                DatabaseReference contentReviewsRef = reviewsDatabase
                        .child(contentType)
                        .child(String.valueOf(content.getId()))
                        .child(user.getUid());

                contentReviewsRef.runTransaction(new Transaction.Handler() {
                    @NonNull
                    @Override
                    public Transaction.Result doTransaction(@NonNull MutableData currentData) {
                        currentData.setValue(review);
                        return Transaction.success(currentData);
                    }

                    @Override
                    public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {
                        firebaseCallback.onAddedContentReviewOfUser(committed && error == null);
                    }
                });
            }
        } else {
            firebaseCallback.onNetworkUnavailable();
        }
    }

    public void removeContentReviewOfUser(User user, AbstractContent content) {
        if (NetworkUtils.isNetworkAvailable(context)) {
            String contentType = ContentTypeMappingManager.getContentType(content.getClass());
            if (contentType != null) {
                DatabaseReference contentReviewsRef = reviewsDatabase
                        .child(contentType)
                        .child(String.valueOf(content.getId()))
                        .child(user.getUid());

                contentReviewsRef.removeValue((error, ref) -> {
                    firebaseCallback.onRemovedContentReviewOfUser(error == null);
                });
            }
        } else {
            firebaseCallback.onNetworkUnavailable();
        }
    }

    public void getRecentContentReview(AbstractContent content, int recentLimit) {
        if (NetworkUtils.isNetworkAvailable(context)) {
            String contentType = ContentTypeMappingManager.getContentType(content.getClass());
            if (contentType != null) {
                DatabaseReference contentReviewsRef = reviewsDatabase
                        .child(contentType)
                        .child(String.valueOf(content.getId()));

                Query query = contentReviewsRef
                        .orderByChild("timestamp")
                        .limitToLast(recentLimit);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        List<UserReview> userReviewList = new ArrayList<>();
                        if (snapshot.exists() && snapshot.hasChildren()) {
                            for (DataSnapshot reviewSnapshot : snapshot.getChildren()) {
                                String userUid = reviewSnapshot.getKey();
                                Review review = reviewSnapshot.getValue(Review.class);
                                if (review != null) {
                                    userReviewList.add(0, new UserReview(userUid, review));
                                }
                            }
                        }
                        firebaseCallback.onRecentContentReview(userReviewList);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        firebaseCallback.onRecentContentReview(null);
                    }
                });
            }
        } else {
            firebaseCallback.onNetworkUnavailable();
        }
    }

    public void getContentReviewOfContent(AbstractContent content, int pageSize, long lastTimestamp) {
        if (NetworkUtils.isNetworkAvailable(context)) {
            String contentType = ContentTypeMappingManager.getContentType(content.getClass());
            if (contentType != null) {
                DatabaseReference contentReviewsRef = reviewsDatabase
                        .child(contentType)
                        .child(String.valueOf(content.getId()));

                Query query;
                if (lastTimestamp == -1) {
                    // If first iteration, start to end of list
                    query = contentReviewsRef
                            .orderByChild("timestamp")
                            .limitToLast(pageSize);
                } else {
                    // For next iteration, use timestamp to starting point
                    query = contentReviewsRef
                            .orderByChild("timestamp")
                            .endAt(lastTimestamp)
                            .limitToLast(pageSize);
                }

                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        List<UserReview> userReviewList = new ArrayList<>();
                        long lowestTimestamp = Long.MAX_VALUE;
                        if (snapshot.exists() && snapshot.hasChildren()) {
                            for (DataSnapshot reviewSnapshot : snapshot.getChildren()) {
                                String userUid = reviewSnapshot.getKey();
                                Review review = reviewSnapshot.getValue(Review.class);
                                if (review != null) {
                                    userReviewList.add(0, new UserReview(userUid, review));
                                    if (lowestTimestamp > review.getTimestamp()) {
                                        lowestTimestamp = review.getTimestamp();
                                    }
                                }
                            }
                        }
                        firebaseCallback.onContentReviewOfContent(userReviewList, pageSize, lowestTimestamp - 1);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        firebaseCallback.onContentReviewOfContent(null, pageSize, lastTimestamp);
                    }
                });
            }
        } else {
            firebaseCallback.onNetworkUnavailable();
        }
    }

}
