package com.example.cineverse.data.source.review;

import static com.example.cineverse.utils.constant.GlobalConstant.END_TIMESTAMP_VALUE;
import static com.example.cineverse.utils.constant.GlobalConstant.START_TIMESTAMP_VALUE;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.cineverse.data.model.User;
import com.example.cineverse.data.model.content.AbstractContent;
import com.example.cineverse.data.model.review.Review;
import com.example.cineverse.data.model.review.UserReview;
import com.example.cineverse.service.firebase.FirebaseCallback;
import com.example.cineverse.service.firebase.ReviewFirebaseDatabaseService;
import com.example.cineverse.utils.NetworkUtils;
import com.example.cineverse.utils.constant.GlobalConstant;
import com.example.cineverse.utils.mapper.ContentTypeMappingManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ReviewFirebaseSource
        extends ReviewFirebaseDatabaseService {

    private final Context context;
    private final ReviewFirebaseCallback firebaseCallback;

    public ReviewFirebaseSource(Context context, ReviewFirebaseCallback firebaseCallback) {
        this.context = context;
        this.firebaseCallback = firebaseCallback;
    }

    public void getContentRating(AbstractContent content) {
        if (NetworkUtils.isNetworkAvailable(context)) {
            String contentType = ContentTypeMappingManager.getContentType(content.getClass());
            if (contentType != null) {
                DatabaseReference ref = reviewsDatabase
                        .child(contentType)
                        .child(String.valueOf(content.getId()));

                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Long count = snapshot.child("count").getValue(Long.class);
                        Long ratingCount = snapshot.child("ratingCount").getValue(Long.class);
                        if (count != null && ratingCount != null) {
                            firebaseCallback.onContentRating((double) ratingCount / count);
                        } else {
                            firebaseCallback.onContentRating(null);
                        }
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

    public void getContentReviewOfUser(User user, AbstractContent content) {
        if (NetworkUtils.isNetworkAvailable(context)) {
            String contentType = ContentTypeMappingManager.getContentType(content.getClass());
            if (contentType != null) {
                DatabaseReference ref = reviewsDatabase
                        .child(contentType)
                        .child(String.valueOf(content.getId()))
                        .child("list")
                        .child(user.getUid());

                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Review review = snapshot.getValue(Review.class);
                        UserReview userReview = new UserReview(user, review);
                        firebaseCallback.onContentReviewOfUser(userReview);
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
                DatabaseReference ref = reviewsDatabase
                        .child(contentType)
                        .child(String.valueOf(content.getId()));

                final Review[] oldReview = {null};
                ref.child("list").child(user.getUid()).runTransaction(new Transaction.Handler() {
                    @NonNull
                    @Override
                    public Transaction.Result doTransaction(@NonNull MutableData currentData) {
                        oldReview[0] = currentData.getValue(Review.class);
                        currentData.setValue(review);
                        currentData.child("timestamp").setValue(ServerValue.TIMESTAMP);
                        return Transaction.success(currentData);
                    }

                    @Override
                    public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {
                        UserReview userReview = null;
                        if (error == null && committed && currentData != null) {
                            Review newReview = currentData.getValue(Review.class);
                            if (newReview != null) {
                                review.setTimestamp(newReview.getTimestamp());
                                userReview = new UserReview(user, newReview);
                                int oldRating = 0;
                                if (oldReview[0] == null) {
                                    ref.child("count").setValue(
                                            ServerValue.increment(1));
                                } else {
                                    oldRating = oldReview[0].getRating();
                                }
                                int newRating = newReview.getRating();
                                int sumRating = newRating + (oldRating * -1);
                                ref.child("ratingCount").setValue(
                                        ServerValue.increment(sumRating));
                            }
                        }
                        firebaseCallback.onAddedContentReviewOfUser(userReview);
                    }
                });
            }
        } else {
            firebaseCallback.onNetworkUnavailable();
        }
    }

    public void removeContentReviewOfUser(User user, AbstractContent content, @NonNull Review review) {
        if (NetworkUtils.isNetworkAvailable(context)) {
            String contentType = ContentTypeMappingManager.getContentType(content.getClass());
            if (contentType != null) {
                DatabaseReference ref = reviewsDatabase
                        .child(contentType)
                        .child(String.valueOf(content.getId()));

                ref.child("list").child(user.getUid()).runTransaction(new Transaction.Handler() {
                    @NonNull
                    @Override
                    public Transaction.Result doTransaction(@NonNull MutableData currentData) {
                        if (currentData.hasChildren()) {
                            ref.child("list").child(user.getUid()).removeValue((error1, ref1) -> {
                                ref.child("count").setValue(
                                        ServerValue.increment(-1));
                                ref.child("ratingCount").setValue(
                                        ServerValue.increment(-1 * review.getRating()));
                            });
                        }
                        return Transaction.success(currentData);
                    }

                    @Override
                    public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {
                        firebaseCallback.onRemovedContentReviewOfUser(error == null);
                    }
                });
            }
        } else {
            firebaseCallback.onNetworkUnavailable();
        }
    }

    public void getPagedContentReviewOfContent(AbstractContent content, int pageSize, long lastTimestamp) {
        if (NetworkUtils.isNetworkAvailable(context)) {
            String contentType = ContentTypeMappingManager.getContentType(content.getClass());
            if (contentType != null) {
                DatabaseReference ref = reviewsDatabase
                        .child(contentType)
                        .child(String.valueOf(content.getId()))
                        .child("list");

                Query query;
                if (lastTimestamp == START_TIMESTAMP_VALUE) {
                    // If first iteration, start to end of list
                    query = ref
                            .orderByChild("timestamp")
                            .limitToLast(pageSize);
                } else {
                    // For next iteration, use timestamp to starting point
                    query = ref
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

                        long finalLowestTimestamp;
                        if (lowestTimestamp == Long.MAX_VALUE) {
                            finalLowestTimestamp = END_TIMESTAMP_VALUE;
                        } else {
                            finalLowestTimestamp = lowestTimestamp - 1;
                        }

                        usersDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (UserReview userReview : userReviewList) {
                                    User user = snapshot.child(userReview.getUser().getUid()).getValue(User.class);
                                    userReview.setUser(user);
                                }
                                firebaseCallback.onPagedContentReviewOfContent(userReviewList, finalLowestTimestamp);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                firebaseCallback.onPagedContentReviewOfContent(new ArrayList<>(), lastTimestamp);
                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        firebaseCallback.onPagedContentReviewOfContent(new ArrayList<>(), lastTimestamp);
                    }
                });
            }
        } else {
            firebaseCallback.onNetworkUnavailable();
        }
    }

    public void addLikeOfUserToContentReview(User user, AbstractContent content, UserReview userReview, FirebaseCallback<Boolean> firebaseCallback) {
        if (NetworkUtils.isNetworkAvailable(context)) {
            String contentType = ContentTypeMappingManager.getContentType(content.getClass());
            User userOfReview = userReview.getUser();
            if (contentType != null && userOfReview != null) {
                DatabaseReference ref = reviewsDatabase
                        .child(contentType)
                        .child(String.valueOf(content.getId()))
                        .child("list")
                        .child(userOfReview.getUid())
                        .child("like")
                        .child(user.getUid());

                ref.runTransaction(new Transaction.Handler() {
                    @NonNull
                    @Override
                    public Transaction.Result doTransaction(@NonNull MutableData currentData) {
                        if (currentData.getValue() == null) {
                            currentData.setValue(true);
                            return Transaction.success(currentData);
                        } else {
                            return Transaction.abort();
                        }
                    }

                    @Override
                    public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {
                        firebaseCallback.onCallback(committed && error == null);
                    }
                });
            }
        } else {
            firebaseCallback.onNetworkUnavailable();
        }
    }

    public void removeLikeOfUserToContentReview(User user, AbstractContent content, UserReview userReview, FirebaseCallback<Boolean> firebaseCallback) {
        if (NetworkUtils.isNetworkAvailable(context)) {
            String contentType = ContentTypeMappingManager.getContentType(content.getClass());
            User userOfReview = userReview.getUser();
            if (contentType != null && userOfReview != null) {
                DatabaseReference ref = reviewsDatabase
                        .child(contentType)
                        .child(String.valueOf(content.getId()))
                        .child("list")
                        .child(userOfReview.getUid())
                        .child("like")
                        .child(user.getUid());

                ref.removeValue((error, ref1) -> {
                    firebaseCallback.onCallback(error == null);
                });
            }
        } else {
            firebaseCallback.onNetworkUnavailable();
        }
    }

}
