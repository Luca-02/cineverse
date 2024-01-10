package com.example.cineverse.adapter.review;

import com.example.cineverse.data.model.review.UserReview;

public interface OnReviewClickListener {
    void onUserReviewClick(UserReview userReview);
    void addLikeToUserReview(UserReview userReview);
    void removeLikeToUserReview(UserReview userReview);
}
