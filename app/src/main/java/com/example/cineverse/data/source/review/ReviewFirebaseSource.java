package com.example.cineverse.data.source.review;

import static com.example.cineverse.utils.constant.GlobalConstant.END_TIMESTAMP_VALUE;
import static com.example.cineverse.utils.constant.GlobalConstant.START_TIMESTAMP_VALUE;

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
                        long count = snapshot.child("list").getChildrenCount();
                        Long ratingCount = snapshot.child("ratingCount").getValue(Long.class);
                        if (ratingCount != null) {
                            firebaseCallback.onContentRating((double) ratingCount / count);
                        } else {
                            firebaseCallback.onContentRating(null);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        firebaseCallback.onUserReviewOfContent(null);
                    }
                });
            }
        } else {
            firebaseCallback.onNetworkUnavailable();
        }
    }

    public void getUserReviewOfContent(User user, AbstractContent content) {
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
                        long likeCount = snapshot.child("like").getChildrenCount();
                        boolean userLikeReview = snapshot.child("like").hasChild(user.getUid());
                        UserReview userReview = new UserReview(user, review);
                        userReview.setLikeCount(likeCount);
                        userReview.setUserLikeReview(userLikeReview);
                        firebaseCallback.onUserReviewOfContent(userReview);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        firebaseCallback.onUserReviewOfContent(null);
                    }
                });
            }
        } else {
            firebaseCallback.onNetworkUnavailable();
        }
    }

    public void addUserReviewOfContent(User user, AbstractContent content, Review review) {
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
                        currentData.child("rating").setValue(review.getRating());
                        currentData.child("review").setValue(review.getReview());
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
                                userReviewDatabase
                                        .child(contentType)
                                        .child(user.getUid())
                                        .child(String.valueOf(content.getId()))
                                        .setValue(review.getTimestamp());

                                int oldRating = 0;
                                if (oldReview[0] != null) {
                                    oldRating = oldReview[0].getRating();
                                }
                                int newRating = newReview.getRating();
                                int sumRating = newRating + (oldRating * -1);
                                ref.child("ratingCount").setValue(
                                        ServerValue.increment(sumRating));
                            }
                            firebaseCallback.onAddedUserReviewOfContent(userReview);
                        } else {
                            firebaseCallback.onAddedUserReviewOfContent(null);
                        }
                    }
                });
            }
        } else {
            firebaseCallback.onNetworkUnavailable();
        }
    }

    public void removeUserReviewOfContent(User user, AbstractContent content, @NonNull Review review) {
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
                                ref.child("ratingCount").setValue(
                                        ServerValue.increment(-1 * review.getRating()));
                            });
                        }
                        return Transaction.success(currentData);
                    }

                    @Override
                    public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {
                        userReviewDatabase
                                .child(contentType)
                                .child(user.getUid())
                                .child(String.valueOf(content.getId()))
                                .removeValue();

                        firebaseCallback.onRemovedUserReviewOfContent(committed);
                    }
                });
            }
        } else {
            firebaseCallback.onNetworkUnavailable();
        }
    }

    public void getPagedUserReviewOfContent(User currentUser, AbstractContent content, int pageSize, long lastTimestamp) {
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

                                if (userUid != null && review != null) {
                                    long likeCount = reviewSnapshot.child("like").getChildrenCount();
                                    boolean userLikeReview = reviewSnapshot.child("like").hasChild(currentUser.getUid());
                                    UserReview userReview = new UserReview(userUid, review);
                                    userReview.setLikeCount(likeCount);
                                    userReview.setUserLikeReview(userLikeReview);
                                    userReviewList.add(0, userReview);
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
                                firebaseCallback.onPagedUserReviewOfContent(userReviewList, finalLowestTimestamp);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                firebaseCallback.onPagedUserReviewOfContent(new ArrayList<>(), lastTimestamp);
                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        firebaseCallback.onPagedUserReviewOfContent(new ArrayList<>(), lastTimestamp);
                    }
                });
            }
        } else {
            firebaseCallback.onNetworkUnavailable();
        }
    }

    public void addLikeOfUserToUserReviewOfContent(User user, AbstractContent content, UserReview userReview) {
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
                        firebaseCallback.onAddedLikeOfUserToUserReviewOfContent(userReview, committed);
                    }
                });
            }
        } else {
            firebaseCallback.onNetworkUnavailable();
        }
    }

    public void removedLikeOfUserToUserReviewOfContent(User user, AbstractContent content, UserReview userReview) {
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
                    firebaseCallback.onRemovedLikeOfUserToUserReviewOfContent(userReview, error == null);
                });
            }
        } else {
            firebaseCallback.onNetworkUnavailable();
        }
    }

}
