package com.example.cineverse.view.details.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.cineverse.R;
import com.example.cineverse.data.model.User;
import com.example.cineverse.data.model.content.AbstractContent;
import com.example.cineverse.data.model.review.UserReview;
import com.example.cineverse.data.source.user.UserFirebaseSource;
import com.example.cineverse.databinding.FragmentReviewDetailsBinding;
import com.example.cineverse.handler.ReviewUiHandler;
import com.example.cineverse.service.firebase.FirebaseCallback;
import com.example.cineverse.viewmodel.review.ReviewViewModel;

public class ReviewDetailsFragment extends Fragment {

    public static final String CONTENT_TAG = "Content";
    public static final String USER_REVIEW_TAG = "UserReview";
    public static final String WITH_LIKE_SECTION_TAG = "WithLikeSection";

    private FragmentReviewDetailsBinding binding;
    private ReviewViewModel reviewViewModel;
    private AbstractContent content;
    private UserReview userReview;
    private boolean withLikeSection;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentReviewDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getExtras();
        if (userReview != null) {
            if (withLikeSection) {
                setViewModel();
                binding.reviewLayout.likeCheckBox.setOnClickListener(v -> {
                    if (binding.reviewLayout.likeCheckBox.isChecked()) {
                        reviewViewModel.addLikeOfCurrentUserToUserReviewOfContent(content, userReview);
                    } else {
                        reviewViewModel.removeLikeOfCurrentUserToUserReviewOfContent(content, userReview);
                    }
                });
            }
            ReviewUiHandler.setReviewUi(requireContext(), binding.reviewLayout, userReview, withLikeSection);
            binding.reviewLayout.getRoot().setClickable(false);
            binding.reviewLayout.reviewTextView.setMaxLines(Integer.MAX_VALUE);
            binding.reviewLayout.reviewTextView.setEllipsize(null);
        }
        binding.materialToolbar.setNavigationOnClickListener(v ->
                requireActivity().getOnBackPressedDispatcher().onBackPressed());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    /**
     * Retrieves extra data from the intent bundle, including the title string ID and genre.
     */
    private void getExtras() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            content = bundle.getParcelable(CONTENT_TAG);
            userReview = bundle.getParcelable(USER_REVIEW_TAG);
            withLikeSection = bundle.getBoolean(WITH_LIKE_SECTION_TAG);
        }
    }

    /**
     * Sets up the ViewModel for the fragment.
     */
    private void setViewModel() {
        reviewViewModel = new ViewModelProvider(requireActivity()).get(ReviewViewModel.class);
        reviewViewModel.getChangeLikeOfCurrentUserToUserReviewOfContentLiveData().observe(
                getViewLifecycleOwner(), this::handleChangeLikeToUserReview);
    }

    private void handleChangeLikeToUserReview(UserReview userReview) {
        if (userReview != null) {
            ReviewUiHandler.setLikeDetails(binding.reviewLayout, userReview, true);
        }
    }

}