package com.example.cineverse.handler;

import android.content.Context;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.cineverse.R;
import com.example.cineverse.data.model.User;
import com.example.cineverse.data.model.review.ContentUserReview;
import com.example.cineverse.data.model.review.Review;
import com.example.cineverse.data.model.review.UserReview;
import com.example.cineverse.databinding.ReviewItemLayoutBinding;

public class ReviewUiHandler {

    private ReviewUiHandler() {
        throw new IllegalStateException(ReviewUiHandler.class.getSimpleName());
    }

    public static <T extends UserReview> void setReviewUi(
            Context context,
            ReviewItemLayoutBinding binding,
            T review,
            boolean withLikeSection) {
        binding.likeCheckBox.setVisibility(View.GONE);
        if (review != null) {
            setContentDetails(binding, review);
            setUserDetails(context, binding, review.getUser());
            setReviewDetails(binding, review, withLikeSection);
            setLikeDetails(binding, review, withLikeSection);
        }
    }

    private static <T extends UserReview> void setContentDetails(
            ReviewItemLayoutBinding binding, T review) {
        if (ContentUserReview.class.isAssignableFrom(review.getClass())) {
            ContentUserReview contentUserReview = (ContentUserReview) review;
            binding.contentNameTextView.setVisibility(View.VISIBLE);
            binding.contentNameTextView.setText(contentUserReview.getContent().getName());
        } else {
            binding.contentNameTextView.setVisibility(View.GONE);
        }
    }

    private static void setUserDetails(
            Context context,
            ReviewItemLayoutBinding binding,
            User user) {
        if (user != null) {
            Glide.with(context)
                    .load(user.getPhotoUrl())
                    .placeholder(R.drawable.circular_account_logo)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(binding.circularImageView);
            binding.usernameTextView.setText(user.getUsername());
        }
    }

    private static void setReviewDetails(
            ReviewItemLayoutBinding binding,
            UserReview userReview,
            boolean withLikeSection) {
        Review review = userReview.getReview();
        if (review != null) {
            binding.reviewDateTextView.setText(review.timestampString());
            binding.ratingChip.setText(String.valueOf(review.getRating()));
            binding.reviewTextView.setText(review.getReview());
        }
    }

    public static void setLikeDetails(
            ReviewItemLayoutBinding binding,
            UserReview userReview,
            boolean withLikeSection) {
        if (withLikeSection) {
            binding.likeCheckBox.setVisibility(View.VISIBLE);
            setLikeData(binding, userReview.getLikeCount(), userReview.isUserLikeReview());
        } else {
            binding.likeCheckBox.setVisibility(View.GONE);
        }
    }

    public static void setLikeData(
            ReviewItemLayoutBinding binding,
        long likeCount,
        boolean userLikeReview) {
        binding.likeCheckBox.setChecked(userLikeReview);
        binding.likeCheckBox.setText(String.valueOf(likeCount));
    }

}
