package com.example.cineverse.repository.review;

import static com.example.cineverse.utils.constant.GlobalConstant.RECENT_LIMIT_COUNT;

import android.content.Context;

import com.example.cineverse.data.model.User;
import com.example.cineverse.data.model.content.AbstractContent;
import com.example.cineverse.data.model.review.Review;
import com.example.cineverse.data.model.review.UserReview;
import com.example.cineverse.data.source.review.ReviewFirebaseSource;
import com.example.cineverse.utils.DateTimeUtils;
import com.example.cineverse.data.source.review.ReviewFirebaseCallback;

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

    public void getContentReviewOfCurrentUser(AbstractContent content) {
        if (user != null) {
            reviewFirebaseSource.getContentReviewOfUser(user, content);
        }
    }

    public void addContentReviewOfCurrentUser(AbstractContent content, Review review) {
        if (user != null) {
            review.setTimestamp(DateTimeUtils.getCurrentTimestamp());
            reviewFirebaseSource.addContentReviewOfUser(user, content, review);
        }
    }

    public void removeContentReviewOfCurrentUser(AbstractContent content) {
        if (user != null) {
            reviewFirebaseSource.removeContentReviewOfUser(user, content);
        }
    }

    public void getRecentContentReview(AbstractContent content) {
        reviewFirebaseSource.getRecentContentReview(content, RECENT_LIMIT_COUNT);
    }

    @Override
    public void onContentReviewOfUser(Review review) {
        firebaseCallback.onContentReviewOfUser(review);
    }

    @Override
    public void onAddedContentReviewOfUser(boolean added) {
        firebaseCallback.onAddedContentReviewOfUser(added);
    }

    @Override
    public void onRemovedContentReviewOfUser(boolean removed) {
        firebaseCallback.onRemovedContentReviewOfUser(removed);
    }

    @Override
    public void onRecentContentReview(List<UserReview> recentReviews) {
        firebaseCallback.onRecentContentReview(recentReviews);
    }

    @Override
    public void onContentReviewOfContent(List<UserReview> userReviewList, int pageSize, long lastTimestamp) {
        firebaseCallback.onContentReviewOfContent(userReviewList, pageSize, lastTimestamp);
    }

    @Override
    public void onNetworkUnavailable() {
        firebaseCallback.onNetworkUnavailable();
    }

}
