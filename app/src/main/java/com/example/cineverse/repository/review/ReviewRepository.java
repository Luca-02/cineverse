package com.example.cineverse.repository.review;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.cineverse.data.model.User;
import com.example.cineverse.data.model.content.AbstractContent;
import com.example.cineverse.data.model.review.Review;
import com.example.cineverse.data.model.review.UserReview;
import com.example.cineverse.data.source.review.ReviewFirebaseCallback;
import com.example.cineverse.data.source.review.ReviewFirebaseSource;

import java.util.List;

public class ReviewRepository
        implements ReviewFirebaseCallback {

    private final User currentUser;
    private final ReviewFirebaseSource reviewFirebaseSource;
    private final ReviewFirebaseCallback firebaseCallback;

    public ReviewRepository(Context context, User currentUser, ReviewFirebaseCallback firebaseCallback) {
        this.currentUser = currentUser;
        this.firebaseCallback = firebaseCallback;
        reviewFirebaseSource = new ReviewFirebaseSource(context, this);
    }

    public void getContentRating(AbstractContent content) {
        reviewFirebaseSource.getContentRating(content);
    }

    public void getCurrentUserReviewOfContent(AbstractContent content) {
        if (currentUser != null) {
            reviewFirebaseSource.getUserReviewOfContent(currentUser, content);
        }
    }

    public void addCurrentUserReviewOfContent(AbstractContent content, Review review) {
        if (currentUser != null) {
            reviewFirebaseSource.addUserReviewOfContent(currentUser, content, review);
        }
    }

    public void removeCurrentUserReviewOfContent(AbstractContent content, @NonNull Review review) {
        if (currentUser != null) {
            reviewFirebaseSource.removeUserReviewOfContent(currentUser, content, review);
        }
    }

    public void getPagedUserReviewOfContent(AbstractContent content, int pageSize, long lastTimestamp) {
        if (currentUser != null) {
            reviewFirebaseSource.getPagedUserReviewOfContent(currentUser, content, pageSize, lastTimestamp);
        }
    }

    public void addLikeOfCurrentUserToUserReviewOfContent(AbstractContent content, UserReview userReview) {
        if (currentUser != null) {
            reviewFirebaseSource.addLikeOfUserToUserReviewOfContent(currentUser, content, userReview);
        }
    }

    public void removeLikeOfCurrentUserToUserReviewOfContent(AbstractContent content, UserReview userReview) {
        if (currentUser != null) {
            reviewFirebaseSource.removedLikeOfUserToUserReviewOfContent(currentUser, content, userReview);
        }
    }

    @Override
    public void onContentRating(Double rating) {
        firebaseCallback.onContentRating(rating);
    }

    @Override
    public void onUserReviewOfContent(UserReview userReview) {
        firebaseCallback.onUserReviewOfContent(userReview);
    }

    @Override
    public void onAddedUserReviewOfContent(UserReview userReview) {
        firebaseCallback.onAddedUserReviewOfContent(userReview);
    }

    @Override
    public void onRemovedUserReviewOfContent(boolean removed) {
        firebaseCallback.onRemovedUserReviewOfContent(removed);
    }

    @Override
    public void onPagedUserReviewOfContent(List<UserReview> userReviewList, long lastTimestamp) {
        firebaseCallback.onPagedUserReviewOfContent(userReviewList, lastTimestamp);
    }

    @Override
    public void onAddedLikeOfUserToUserReviewOfContent(UserReview userReview, boolean added) {
        if (added && userReview != null) {
            userReview.setLikeCount(userReview.getLikeCount() + 1);
            userReview.setUserLikeReview(true);
        }
        firebaseCallback.onAddedLikeOfUserToUserReviewOfContent(userReview, added);
    }

    @Override
    public void onRemovedLikeOfUserToUserReviewOfContent(UserReview userReview, boolean removed) {
        if (removed && userReview != null) {
            userReview.setLikeCount(userReview.getLikeCount() - 1);
            userReview.setUserLikeReview(false);
        }
        firebaseCallback.onRemovedLikeOfUserToUserReviewOfContent(userReview, removed);
    }

    @Override
    public void onNetworkUnavailable() {
        firebaseCallback.onNetworkUnavailable();
    }

}
