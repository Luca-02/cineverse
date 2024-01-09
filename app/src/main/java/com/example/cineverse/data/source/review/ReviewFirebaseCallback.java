package com.example.cineverse.data.source.review;

import com.example.cineverse.data.model.review.UserReview;
import com.example.cineverse.service.NetworkCallback;

import java.util.List;

public interface ReviewFirebaseCallback extends NetworkCallback {
    void onContentRating(Double rating);
    void onContentReviewOfUser(UserReview userReview);
    void onAddedContentReviewOfUser(UserReview userReview);
    void onRemovedContentReviewOfUser(boolean removed);
    void onPagedContentReviewOfContent(List<UserReview> userReviewList, long lastTimestamp);
}
