package com.example.cineverse.data.source.review;

import com.example.cineverse.data.model.review.ContentUserReview;
import com.example.cineverse.data.model.review.Review;
import com.example.cineverse.data.model.review.UserReview;
import com.example.cineverse.service.NetworkCallback;

import java.util.List;

public interface ReviewFirebaseCallback extends NetworkCallback {
    void onContentRating(Double rating);
    void onUserReviewOfContent(UserReview userReview);
    void onUserReviewList(List<ContentUserReview> contentUserReviewList, String contentType);
    void onAddedUserReviewOfContent(UserReview userReview);
    void onRemovedUserReviewOfContent(boolean removed);
    void onPagedUserReviewOfContent(List<UserReview> userReviewList, long lastTimestamp);
    void onAddedLikeOfUserToUserReviewOfContent(UserReview userReview, boolean added);
    void onRemovedLikeOfUserToUserReviewOfContent(UserReview userReview, boolean removed);
}
