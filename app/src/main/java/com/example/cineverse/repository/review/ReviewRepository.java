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

    private final User user;
    private final ReviewFirebaseSource reviewFirebaseSource;
    private final ReviewFirebaseCallback firebaseCallback;

    public ReviewRepository(Context context, User user, ReviewFirebaseCallback firebaseCallback) {
        this.user = user;
        this.firebaseCallback = firebaseCallback;
        reviewFirebaseSource = new ReviewFirebaseSource(context, this);
    }

    public void getContentRating(AbstractContent content) {
        reviewFirebaseSource.getContentRating(content);
    }

    public void getContentReviewOfCurrentUser(AbstractContent content) {
        if (user != null) {
            reviewFirebaseSource.getContentReviewOfUser(user, content);
        }
    }

    public void addContentReviewOfCurrentUser(AbstractContent content, Review review) {
        if (user != null) {
            reviewFirebaseSource.addContentReviewOfUser(user, content, review);
        }
    }

    public void removeContentReviewOfCurrentUser(AbstractContent content, @NonNull Review review) {
        if (user != null) {
            reviewFirebaseSource.removeContentReviewOfUser(user, content, review);
        }
    }

    public void getPagedContentReviewOfContent(AbstractContent content, int pageSize, long lastTimestamp) {
        reviewFirebaseSource.getPagedContentReviewOfContent(content, pageSize, lastTimestamp);
    }

    @Override
    public void onContentRating(Double rating) {
        firebaseCallback.onContentRating(rating);
    }

    @Override
    public void onContentReviewOfUser(UserReview userReview) {
        firebaseCallback.onContentReviewOfUser(userReview);
    }

    @Override
    public void onAddedContentReviewOfUser(UserReview userReview) {
        firebaseCallback.onAddedContentReviewOfUser(userReview);
    }

    @Override
    public void onRemovedContentReviewOfUser(boolean removed) {
        firebaseCallback.onRemovedContentReviewOfUser(removed);
    }

    @Override
    public void onPagedContentReviewOfContent(List<UserReview> userReviewList, long lastTimestamp) {
        firebaseCallback.onPagedContentReviewOfContent(userReviewList, lastTimestamp);
    }

    @Override
    public void onNetworkUnavailable() {
        firebaseCallback.onNetworkUnavailable();
    }

}
