package com.example.cineverse.data.source.review;

import com.example.cineverse.data.model.review.Review;
import com.example.cineverse.data.model.review.UserReview;
import com.example.cineverse.service.NetworkCallback;

import java.util.List;

public interface ReviewFirebaseCallback extends NetworkCallback {
    void onContentReviewOfUser(Review review);
    void onAddedContentReviewOfUser(boolean added);
    void onRemovedContentReviewOfUser(boolean removed);
    void onRecentContentReview(List<UserReview> recentReviews);
    void onContentReviewOfContent(List<UserReview> userReviewList, int pageSize, long lastTimestamp);
}
