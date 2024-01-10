package com.example.cineverse.view.details.fragment;

import static com.example.cineverse.utils.constant.Api.RESPONSE_DATE_FORMAT;
import static com.example.cineverse.utils.constant.Api.TMDB_IMAGE_ORIGINAL_SIZE_URL;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.cineverse.R;
import com.example.cineverse.data.model.content.AbstractContent;
import com.example.cineverse.data.model.review.Review;
import com.example.cineverse.data.model.review.UserReview;
import com.example.cineverse.databinding.FragmentReviewContentBinding;
import com.example.cineverse.utils.DateTimeUtils;
import com.example.cineverse.view.details.ContentDetailsActivity;
import com.example.cineverse.viewmodel.review.ReviewViewModel;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReviewContentFragment extends Fragment {

    public static final String CONTENT_TAG = "Content";

    private FragmentReviewContentBinding binding;
    private ReviewViewModel viewModel;
    private AbstractContent content;
    private Review oldReview;
    private Review review;
    private List<ImageView> ratingStarList;
    private Map<ImageView, Integer> ratingStarMap;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentReviewContentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getExtras();
        setViewModel();
        setContentUi();
        setListener();
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
        }
    }

    /**
     * Sets up the ViewModel for the fragment.
     */
    private void setViewModel() {
        viewModel = new ViewModelProvider(requireActivity()).get(ReviewViewModel.class);
        viewModel.getCurrentUserReviewOfContentLiveData().observe(getViewLifecycleOwner(), this::handleCurrentUserReview);
        viewModel.getAddedCurrentUserReviewOfContentLiveData().observe(getViewLifecycleOwner(), added ->
                handleActionReview(added, viewModel.getAddedCurrentUserReviewOfContentLiveData()));
        viewModel.getRemovedCurrentUserReviewOfContentLiveData().observe(getViewLifecycleOwner(), removed ->
                handleActionReview(removed, viewModel.getRemovedCurrentUserReviewOfContentLiveData()));
        viewModel.getNetworkErrorLiveData().observe(getViewLifecycleOwner(), this::handleNetworkError);
    }

    private void setContentUi() {
        ratingStarList = new ArrayList<>();
        ratingStarList.add(binding.star1);
        ratingStarList.add(binding.star2);
        ratingStarList.add(binding.star3);
        ratingStarList.add(binding.star4);
        ratingStarList.add(binding.star5);

        ratingStarMap = new HashMap<>();
        for (int i = 0; i < ratingStarList.size(); i++) {
            ratingStarMap.put(ratingStarList.get(i), i + 1);
        }

        if (content.getPosterPath() == null) {
            int padding = getResources().getDimensionPixelOffset(R.dimen.double_spacing);
            binding.posterImageView.setPadding(padding, padding, padding, padding);
            binding.posterImageView.setImageResource(R.drawable.outline_image_not_supported);
        } else {
            String posterImageUrl = TMDB_IMAGE_ORIGINAL_SIZE_URL + content.getPosterPath();
            Glide.with(requireActivity())
                    .load(posterImageUrl)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(binding.posterImageView);
        }
        binding.titleTextView.setText(content.getName());
        binding.releaseDateChip.setText(
                DateTimeUtils.formatDate(requireActivity(), RESPONSE_DATE_FORMAT, content.getReleaseDate()));
    }

    /**
     * Sets up the UI button listeners for navigation, text input, and login functionality.
     */
    private void setListener() {
        for (ImageView star : ratingStarList) {
            star.setOnClickListener(v -> {
                Integer rating = ratingStarMap.get(star);
                if (rating != null && review != null) {
                    review.setRating(rating);
                    setRatingStar(rating);
                    handleButton();
                }
            });
        }

        binding.reviewEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (review != null) {
                    review.setReview(String.valueOf(s).trim());
                    handleButton();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        binding.materialToolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.publicReview) {
                viewModel.addCurrentUserReviewOfContent(content, oldReview, review);
                return true;
            } else if (item.getItemId() == R.id.deleteReview) {
                openDeleteDialog();
                return true;
            }
            return false;
        });

        binding.materialToolbar.setNavigationOnClickListener(v ->
                requireActivity().getOnBackPressedDispatcher().onBackPressed());
    }

    private void handleCurrentUserReview(UserReview userReview) {
        oldReview = null;
        if (userReview == null || userReview.getReview() == null) {
            this.review = Review.createDefaultReview();
        } else {
            oldReview = userReview.getReview().clone();
            review = userReview.getReview();
        }
        setRatingStar(review.getRating());
        setReviewText(review.getReview());
        handleButton();
    }

    private void setRatingStar(int rating) {
        for (int i = 0; i < rating; i++) {
            ratingStarList.get(i).setImageResource(R.drawable.outline_star);
        }
        for (int i = rating; i < ratingStarList.size(); i++) {
            ratingStarList.get(i).setImageResource(R.drawable.outline_grade);
        }
    }

    private void setReviewText(String review) {
        binding.reviewEditText.setText(review);
    }

    private void handleButton() {
        Menu menu = binding.materialToolbar.getMenu();
        MenuItem item = menu.findItem(R.id.publicReview);
        item.setEnabled(review.getRating() != 0 && !review.getReview().isEmpty());
    }

    private void handleActionReview(Boolean actionExecuted, MutableLiveData<Boolean> addedReviewLiveData) {
        if (actionExecuted != null) {
            if (!actionExecuted) {
                Snackbar.make(binding.getRoot(),
                        R.string.unexpected_error, Snackbar.LENGTH_SHORT).show();
            } else {
                viewModel.getContentRating(content);
                viewModel.refreshContentReviewOfContent(content);
                Navigation.findNavController(requireView()).popBackStack();
                addedReviewLiveData.postValue(null);
            }
        }
    }

    private void openDeleteDialog() {
        new MaterialAlertDialogBuilder(requireContext())
                .setTitle(getString(R.string.delete_review))
                .setMessage(getString(R.string.sure_delete_review))
                .setNegativeButton(getString(R.string.cancel), (dialog, which) -> {

                })
                .setPositiveButton(getString(R.string.delete_), (dialog, which) -> {
                    viewModel.removeCurrentUserReviewOfContent(content, review);
                })
                .show();
    }

    private void handleNetworkError(Boolean bool) {
        if (bool != null && bool) {
            ((ContentDetailsActivity) requireActivity()).openNetworkErrorActivity();
            viewModel.getNetworkErrorLiveData().setValue(null);
        }
    }

}