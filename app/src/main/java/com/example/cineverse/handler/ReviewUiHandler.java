package com.example.cineverse.handler;

import android.content.Context;
import android.text.Layout;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.cineverse.R;
import com.example.cineverse.data.model.User;
import com.example.cineverse.data.model.review.ContentUserReview;
import com.example.cineverse.data.model.review.Review;
import com.example.cineverse.data.model.review.UserReview;
import com.example.cineverse.databinding.ReviewItemLayoutBinding;
import com.example.cineverse.utils.constant.GlobalConstant;

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
            setReviewDetails(binding, review);
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
            UserReview userReview) {
        Review review = userReview.getReview();
        if (review != null) {
            binding.reviewDateTextView.setText(review.timestampString());
            binding.ratingChip.setText(String.valueOf(review.getRating()));
            binding.reviewTextView.setText(review.getReview());
            setReviewTextView(binding);
        }
    }

    private static void setReviewTextView(ReviewItemLayoutBinding binding) {
        binding.reviewTextView.post(() -> {
            Layout layout = binding.reviewTextView.getLayout();
            if (layout != null) {
                int lines = layout.getLineCount();
                if (lines > 0) {
                    if (lines > 5 || layout.getEllipsisCount(lines - 1) > 0) {
                        binding.readMoreLessTextView.setVisibility(View.VISIBLE);
                    } else {
                        binding.readMoreLessTextView.setVisibility(View.GONE);
                    }
                }
            }
        });
        binding.readMoreLessTextView.setOnClickListener(v -> {
            if (binding.reviewTextView.getMaxLines() == 5) {
                binding.reviewTextView.setMaxLines(Integer.MAX_VALUE);
                binding.readMoreLessTextView.setText(R.string.read_less);
            } else {
                binding.reviewTextView.setMaxLines(5);
                binding.readMoreLessTextView.setText(R.string.read_more);
            }
        });
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
