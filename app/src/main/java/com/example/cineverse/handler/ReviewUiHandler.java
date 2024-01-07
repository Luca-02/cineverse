package com.example.cineverse.handler;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.cineverse.R;
import com.example.cineverse.data.model.User;
import com.example.cineverse.data.model.review.Review;
import com.example.cineverse.data.model.review.UserReview;
import com.example.cineverse.data.source.user.UserFirebaseSource;
import com.example.cineverse.databinding.ReviewItemLayoutBinding;
import com.example.cineverse.service.firebase.FirebaseCallback;

public class ReviewUiHandler {

    public static void setReviewUi(
            Context context,
            UserFirebaseSource userFirebaseSource,
            ReviewItemLayoutBinding binding,
            UserReview userReview) {
        if (userReview != null) {
            loadUserDetails(context, userFirebaseSource, binding, userReview.getUserId());
            setReviewDetails(context, binding, userReview.getReview());
        }
    }

    public static void setReviewUi(
            Context context,
            ReviewItemLayoutBinding binding,
            User user,
            Review review) {
        loadUserDetails(context, binding, user);
        setReviewDetails(context, binding, review);
    }

    private static void loadUserDetails(
            Context context,
            UserFirebaseSource userFirebaseSource,
            ReviewItemLayoutBinding binding,
            String userId) {
        userFirebaseSource.getUserFromUid(context, userId, new FirebaseCallback<User>() {
            @Override
            public void onCallback(User user) {
                displayUserDetails(context, binding, user);
            }

            @Override
            public void onNetworkUnavailable() {
                // Handle network unavailable case if needed
            }
        });
    }

    private static void loadUserDetails(
            Context context,
            ReviewItemLayoutBinding binding,
            User user) {
        displayUserDetails(context, binding, user);
    }

    private static void displayUserDetails(
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
            Context context,
            ReviewItemLayoutBinding binding,
            Review review) {
        if (review != null) {
            binding.reviewDateTextView.setText(review.getTimeAgoString(context));
            binding.ratingChip.setText(String.valueOf(review.getRating()));
            binding.reviewTextView.setText(review.getReview());
        }
    }

}
