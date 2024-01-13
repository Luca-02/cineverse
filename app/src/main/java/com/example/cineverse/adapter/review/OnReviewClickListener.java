package com.example.cineverse.adapter.review;

import com.example.cineverse.data.model.review.UserReview;

public interface OnReviewClickListener<T extends UserReview> {
    void onUserReviewClick(T userReview);
    void addLikeToUserReview(T userReview);
    void removeLikeToUserReview(T userReview);
}
