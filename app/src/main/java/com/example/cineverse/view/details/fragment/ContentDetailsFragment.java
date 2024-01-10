package com.example.cineverse.view.details.fragment;

import static com.example.cineverse.utils.constant.Api.RESPONSE_DATE_FORMAT;
import static com.example.cineverse.utils.constant.Api.TMDB_IMAGE_ORIGINAL_SIZE_URL;
import static com.example.cineverse.view.details.fragment.ReviewDetailsFragment.USER_REVIEW_TAG;
import static com.example.cineverse.view.details.fragment.ViewAllCastCrewFragment.CREDITS_TAG;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.cineverse.R;
import com.example.cineverse.adapter.details.CastAdapter;
import com.example.cineverse.adapter.details.GenreAdapter;
import com.example.cineverse.adapter.review.OnReviewClickListener;
import com.example.cineverse.adapter.review.ReviewAdapter;
import com.example.cineverse.data.model.api.Failure;
import com.example.cineverse.data.model.content.AbstractContent;
import com.example.cineverse.data.model.details.section.ContentDetailsApiResponse;
import com.example.cineverse.data.model.details.section.MovieDetails;
import com.example.cineverse.data.model.details.section.TvDetails;
import com.example.cineverse.data.model.review.UserReview;
import com.example.cineverse.databinding.FragmentContentDetailsBinding;
import com.example.cineverse.handler.ReviewUiHandler;
import com.example.cineverse.utils.DateTimeUtils;
import com.example.cineverse.utils.mapper.ContentTypeMappingManager;
import com.example.cineverse.view.details.ContentDetailsActivity;
import com.example.cineverse.viewmodel.details.AbstractContentDetailsViewModel;
import com.example.cineverse.viewmodel.details.section.MovieDetailsViewModel;
import com.example.cineverse.viewmodel.details.section.TvDetailsViewModel;
import com.example.cineverse.viewmodel.review.ReviewViewModel;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class ContentDetailsFragment extends Fragment
        implements OnReviewClickListener {

    private FragmentContentDetailsBinding binding;
    private AbstractContentDetailsViewModel<? extends ContentDetailsApiResponse> contentDetailsViewModel;
    private ReviewViewModel reviewViewModel;
    private ContentDetailsApiResponse contentDetails;
    private ReviewAdapter reviewAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentContentDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setViewModel();
        setContentDetails();
        setListener();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    /**
     * Sets up the ViewModel for the fragment.
     */
    private void setViewModel() {
        String contentType =
                ((ContentDetailsActivity) requireActivity()).getContentType();

        if (contentType.equals(ContentTypeMappingManager.ContentType.MOVIE.getType())) {
            contentDetailsViewModel = new ViewModelProvider(this).get(MovieDetailsViewModel.class);
        } else if (contentType.equals(ContentTypeMappingManager.ContentType.TV.getType())) {
            contentDetailsViewModel = new ViewModelProvider(this).get(TvDetailsViewModel.class);
        }

        if (contentDetailsViewModel != null) {
            contentDetailsViewModel.getContentDetailsLiveData().observe(getViewLifecycleOwner(), this::handleContentDetails);
            contentDetailsViewModel.getTimestampInWatchlistLiveData().observe(getViewLifecycleOwner(), this::handleTimestampInWatchlist);
            contentDetailsViewModel.getAddedToWatchlistLiveData().observe(getViewLifecycleOwner(), this::handleAddedToWatch);
            contentDetailsViewModel.getRemovedToWatchlistLiveData().observe(getViewLifecycleOwner(), this::handleRemovedToWatch);
            contentDetailsViewModel.getFailureLiveData().observe(getViewLifecycleOwner(), this::handleFailure);
            contentDetailsViewModel.getNetworkErrorLiveData().observe(getViewLifecycleOwner(), this::handleNetworkError);
        }

        reviewViewModel = new ViewModelProvider(requireActivity()).get(ReviewViewModel.class);
        reviewViewModel.getContentRatingLiveData().observe(getViewLifecycleOwner(), this::handleContentRating);
        reviewViewModel.getCurrentUserReviewOfContentLiveData().observe(getViewLifecycleOwner(), this::handleCurrentUserReview);
        reviewViewModel.getPagedUserReviewOfContentLiveData().observe(getViewLifecycleOwner(), this::handlePagedContentReview);
        reviewViewModel.getChangeLikeOfCurrentUserToUserReviewOfContentLiveData().observe(
                getViewLifecycleOwner(), this::handleChangeLikeToUserReview);
        reviewViewModel.getNetworkErrorLiveData().observe(getViewLifecycleOwner(), this::handleNetworkError);
    }

    private void setContentDetails() {
        int contentId =
                ((ContentDetailsActivity) requireActivity()).getContentId();
        if (contentDetails == null) {
            contentDetailsViewModel.fetchDetails(contentId);
        }
        reviewAdapter = new ReviewAdapter(requireContext(), new ArrayList<>(), this);
        binding.recentReviewRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recentReviewRecyclerView.setAdapter(reviewAdapter);
    }

    private void setListener() {
        binding.reviewButton.setOnClickListener(v -> openReviewContentFragment());
        binding.yourReviewLayout.getRoot().setOnClickListener(v -> openReviewContentFragment());
        binding.viewAllCastCrewChip.setOnClickListener(v -> openViewAllCastCrewFragment());
        binding.viewAllReviewChip.setOnClickListener(v -> openViewAllReviewContentFragment());
        binding.materialToolbar.setNavigationOnClickListener(v ->
                requireActivity().getOnBackPressedDispatcher().onBackPressed());
    }

    private void handleContentDetails(ContentDetailsApiResponse contentDetailsApiResponse) {
        if (MovieDetails.class.isAssignableFrom(contentDetailsApiResponse.getClass())) {
            handleMovieDetails((MovieDetails) contentDetailsApiResponse);
        } else if (TvDetails.class.isAssignableFrom(contentDetailsApiResponse.getClass())) {
            handleTvDetails((TvDetails) contentDetailsApiResponse);
        }

        if (contentDetailsViewModel.isFirstTimeLoadTimestampInWatchlist()) {
            contentDetailsViewModel.getTimestampForContentInWatchlist((AbstractContent) contentDetails);
        }
    }

    private void handleMovieDetails(MovieDetails movieDetails) {
        setContentUi(movieDetails);
        setContentDetailsUi(movieDetails);
        String subtitleString = String.format(
                getString(R.string.n_minutes), movieDetails.getRuntime()) +
                " • " +
                movieDetails.getStatus();
        binding.subtitleTextView.setText(subtitleString);
    }

    private void handleTvDetails(TvDetails tvDetails) {
        setContentUi(tvDetails);
        setContentDetailsUi(tvDetails);
        String subtitleString = String.format(
                getString(R.string.n_season), tvDetails.getSeasons().size()) +
                " · " +
                tvDetails.getStatus();
        binding.subtitleTextView.setText(subtitleString);
    }

    private void setContentUi(AbstractContent content) {
        if (content.getBackdropPath() == null) {
            int padding = getResources().getDimensionPixelOffset(R.dimen.double_spacing);
            binding.backdropImageView.setPadding(padding, padding, padding, padding);
            binding.backdropImageView.setImageResource(R.drawable.outline_image_not_supported);
        } else {
            String backdropImageUrl = TMDB_IMAGE_ORIGINAL_SIZE_URL + content.getBackdropPath();
            Glide.with(requireActivity())
                    .load(backdropImageUrl)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(binding.backdropImageView);
        }

        if (content.getPosterPath() == null) {
            int padding = requireContext().getResources().getDimensionPixelOffset(R.dimen.double_spacing);
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
        binding.originalLanguageChip.setText(content.getCountryName());
        if (content.getOverview().isEmpty()) {
            binding.overviewTextView.setVisibility(View.GONE);
        } else {
            binding.overviewTextView.setText(content.getOverview());
        }

        if (reviewViewModel.getContentRatingLiveData().getValue() == null) {
            reviewViewModel.getContentRating(content);
        }
        if (reviewViewModel.getCurrentUserReviewOfContentLiveData().getValue() == null) {
            reviewViewModel.getCurrentUserReviewOfContent(content);
        }
        if (reviewViewModel.getPagedUserReviewOfContentLiveData().getValue() == null) {
            reviewViewModel.getPagedUserReviewOfContent(content);
        }
    }

    private void setContentDetailsUi(ContentDetailsApiResponse contentDetails) {
        this.contentDetails = contentDetails;
        if (contentDetails.getTagline().isEmpty()) {
            binding.taglineTextView.setVisibility(View.GONE);
        } else {
            binding.taglineTextView.setText(contentDetails.getTagline());
        }

        GenreAdapter genreAdapter = new GenreAdapter(contentDetails.getGenres());
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(requireContext());
        binding.genreRecyclerView.setLayoutManager(layoutManager);
        binding.genreRecyclerView.setAdapter(genreAdapter);

        CastAdapter castAdapter = new CastAdapter(
                requireContext(), contentDetails.getCredits().getCast(), true);
        binding.castRecyclerView.setLayoutManager(new LinearLayoutManager(
                requireContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.castRecyclerView.setAdapter(castAdapter);
        binding.castRecyclerView.setHasFixedSize(false);
    }

    private void handleContentRating(Double rating) {
        if (rating != null && !rating.isNaN() && !rating.isInfinite()) {
            binding.ratingContentChip.setVisibility(View.VISIBLE);
            binding.ratingContentChip.setText(String.valueOf(rating));
        } else {
            binding.ratingContentChip.setVisibility(View.GONE);
        }
    }

    private void handleCurrentUserReview(UserReview userReview) {
        if (userReview != null && userReview.getUser() != null && userReview.getReview() != null) {
            binding.yourReviewTextView.setVisibility(View.VISIBLE);
            binding.yourReviewLayout.getRoot().setVisibility(View.VISIBLE);
            ReviewUiHandler.setReviewUi(requireContext(), binding.yourReviewLayout, userReview, false);
        } else {
            binding.yourReviewTextView.setVisibility(View.GONE);
            binding.yourReviewLayout.getRoot().setVisibility(View.GONE);
        }
    }

    private void handlePagedContentReview(List<UserReview> userReviewList) {
        if (userReviewList.size() > 0) {
            reviewAdapter.setData(userReviewList.subList(0, Math.min(userReviewList.size(), 3)));
            binding.recentReviewConstraintLayout.setVisibility(View.VISIBLE);
            binding.recentReviewRecyclerView.setVisibility(View.VISIBLE);
        } else {
            reviewAdapter.clearData();
            binding.recentReviewConstraintLayout.setVisibility(View.GONE);
            binding.recentReviewRecyclerView.setVisibility(View.GONE);
        }
    }

    private void handleChangeLikeToUserReview(UserReview userReview) {
        reviewAdapter.handleChangeLikeToUserReview(userReview);
    }

    private void handleTimestampInWatchlist(Long timestamp) {
        if (contentDetails != null) {
            contentDetails.setTimestamp(timestamp);
            handleToWatchButton();
            contentDetailsViewModel.setFirstTimeLoadTimestampInWatchlist(false);
        }
    }

    private void handleAddedToWatch(Long timestamp) {
        if (timestamp != null) {
            contentDetails.setTimestamp(timestamp);
            contentDetailsViewModel.getTimestampInWatchlistLiveData().setValue(timestamp);
            Snackbar.make(binding.getRoot(),
                    R.string.added_to_watch_list, Snackbar.LENGTH_SHORT).show();
            contentDetailsViewModel.getAddedToWatchlistLiveData().setValue(null);
        }
    }

    private void handleRemovedToWatch(Boolean removed) {
        if (removed != null && removed) {
            contentDetails.setTimestamp(null);
            contentDetailsViewModel.getTimestampInWatchlistLiveData().setValue(null);
            Snackbar.make(binding.getRoot(),
                    R.string.removed_to_watch_list, Snackbar.LENGTH_SHORT).show();
            contentDetailsViewModel.getRemovedToWatchlistLiveData().setValue(null);
        }
    }

    private void handleToWatchButton() {
        if (contentDetails.getTimestamp() != null) {
            binding.toWatchButton.setIcon(ContextCompat.getDrawable(requireContext(), R.drawable.outline_done));
            binding.toWatchButton.setOnClickListener(v ->
                    contentDetailsViewModel.removeContentToWatchlist((AbstractContent) contentDetails));
        } else {
            binding.toWatchButton.setIcon(ContextCompat.getDrawable(requireContext(), R.drawable.outline_add));
            binding.toWatchButton.setOnClickListener(v ->
                    contentDetailsViewModel.addContentToWatchlist((AbstractContent) contentDetails));
        }
    }

    private void handleFailure(Failure failure) {
        if (failure != null) {
            Snackbar.make(binding.getRoot(),
                    failure.getStatusMessage(), Snackbar.LENGTH_SHORT).show();
            contentDetailsViewModel.getFailureLiveData().setValue(null);
        }
    }

    private void handleNetworkError(Boolean bool) {
        if (bool != null && bool) {
            ((ContentDetailsActivity) requireActivity()).openNetworkErrorActivity();
            contentDetailsViewModel.getNetworkErrorLiveData().setValue(null);
        }
    }

    private void openReviewContentFragment() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(ReviewContentFragment.CONTENT_TAG, (AbstractContent) contentDetails);
        Navigation.findNavController(requireView())
                .navigate(R.id.action_contentDetailsFragment_to_reviewContentFragment, bundle);
    }

    private void openViewAllCastCrewFragment() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(ViewAllCastCrewFragment.CREDITS_TAG, contentDetails.getCredits());
        Navigation.findNavController(requireView())
                .navigate(R.id.action_contentDetailsFragment_to_viewAllCastCrewFragment, bundle);
    }

    private void openViewAllReviewContentFragment() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(ViewAllReviewContentFragment.CONTENT_TAG, (AbstractContent) contentDetails);
        Navigation.findNavController(requireView())
                .navigate(R.id.action_contentDetailsFragment_to_viewAllReviewContentFragment, bundle);
    }

    @Override
    public void onUserReviewClick(UserReview userReview) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(ReviewDetailsFragment.CONTENT_TAG, (AbstractContent) contentDetails);
        bundle.putParcelable(ReviewDetailsFragment.USER_REVIEW_TAG, userReview);
        bundle.putBoolean(ReviewDetailsFragment.WITH_LIKE_SECTION_TAG, true);
        Navigation.findNavController(requireView())
                .navigate(R.id.action_contentDetailsFragment_to_reviewDetailsFragment, bundle);
    }

    @Override
    public void addLikeToUserReview(UserReview userReview) {
        reviewViewModel.addLikeOfCurrentUserToUserReviewOfContent((AbstractContent) contentDetails, userReview);
    }

    @Override
    public void removeLikeToUserReview(UserReview userReview) {
        reviewViewModel.removeLikeOfCurrentUserToUserReviewOfContent((AbstractContent) contentDetails, userReview);
    }

}