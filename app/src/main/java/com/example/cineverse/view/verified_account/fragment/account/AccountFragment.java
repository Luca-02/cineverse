package com.example.cineverse.view.verified_account.fragment.account;

import static com.example.cineverse.utils.constant.GlobalConstant.USER_RECENT_REVIEW_COUNT;
import static com.example.cineverse.utils.constant.GlobalConstant.USER_RECENT_WATCHLIST_COUNT;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.example.cineverse.R;
import com.example.cineverse.adapter.content.ContentSectionAdapter;
import com.example.cineverse.adapter.content.OnContentClickListener;
import com.example.cineverse.adapter.review.OnReviewClickListener;
import com.example.cineverse.adapter.review.ReviewAdapter;
import com.example.cineverse.data.model.User;
import com.example.cineverse.data.model.content.AbstractContent;
import com.example.cineverse.data.model.review.ContentUserReview;
import com.example.cineverse.data.model.review.UserReview;
import com.example.cineverse.data.model.ui.ContentSection;
import com.example.cineverse.databinding.FragmentAccountBinding;
import com.example.cineverse.utils.mapper.ContentTypeMappingManager;
import com.example.cineverse.view.details.ContentDetailsActivity;
import com.example.cineverse.view.details.ContentDetailsActivityOpener;
import com.example.cineverse.view.verified_account.VerifiedAccountActivity;
import com.example.cineverse.viewmodel.review.ReviewViewModel;
import com.example.cineverse.viewmodel.verified_account.VerifiedAccountViewModel;
import com.example.cineverse.viewmodel.watchlist.WatchlistViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@link AccountFragment} class representing the user account section of the application.
 * This fragment serves as one of the tabs in the BottomNavigationView.
 */
public class AccountFragment extends Fragment
        implements OnContentClickListener, OnReviewClickListener<ContentUserReview> {

    private FragmentAccountBinding binding;
    private VerifiedAccountViewModel verifiedAccountViewModel;
    private WatchlistViewModel watchlistViewModel;
    private ReviewViewModel reviewViewModel;
    private ContentSectionAdapter watchlistAdapter;
    private ReviewAdapter<ContentUserReview> reviewAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAccountBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setViewModel();
        setContentUi();
        setListener();
        binding.materialToggleGroup.check(R.id.buttonMovies);
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
        verifiedAccountViewModel = new ViewModelProvider(this).get(VerifiedAccountViewModel.class);
        verifiedAccountViewModel.getUserLiveData().observe(getViewLifecycleOwner(), this::handleUser);
        verifiedAccountViewModel.getLoggedOutLiveData().observe(getViewLifecycleOwner(), this::handleLoggedOut);
        verifiedAccountViewModel.getNetworkErrorLiveData().observe(getViewLifecycleOwner(), aBoolean ->
                handleNetworkError(aBoolean, verifiedAccountViewModel.getNetworkErrorLiveData()));

        watchlistViewModel = new ViewModelProvider(this).get(WatchlistViewModel.class);
        watchlistViewModel.getUserMovieWatchlistLiveData().observe(getViewLifecycleOwner(), abstractContents -> {
            if (abstractContents != null && binding.materialToggleGroup.getCheckedButtonId() == R.id.buttonMovies) {
                setWatchlistAdapterData(abstractContents);
            }
            handleEmptyLayout();
        });
        watchlistViewModel.getUserTvWatchlistLiveData().observe(getViewLifecycleOwner(), abstractContents -> {
            if (abstractContents != null && binding.materialToggleGroup.getCheckedButtonId() == R.id.buttonSeries) {
                setWatchlistAdapterData(abstractContents);
            }
            handleEmptyLayout();
        });
        watchlistViewModel.getNetworkErrorLiveData().observe(getViewLifecycleOwner(), aBoolean ->
                handleNetworkError(aBoolean, watchlistViewModel.getNetworkErrorLiveData()));

        reviewViewModel = new ViewModelProvider(this).get(ReviewViewModel.class);
        reviewViewModel.getUserMovieReviewListLiveData().observe(getViewLifecycleOwner(), contentUserReviews -> {
            if (contentUserReviews != null && binding.materialToggleGroup.getCheckedButtonId() == R.id.buttonMovies) {
                setReviewAdapterData(contentUserReviews);
            }
            handleEmptyLayout();
        });
        reviewViewModel.getUserTvReviewListLiveData().observe(getViewLifecycleOwner(), contentUserReviews -> {
            if (contentUserReviews != null && binding.materialToggleGroup.getCheckedButtonId() == R.id.buttonSeries) {
                setReviewAdapterData(contentUserReviews);
            }
            handleEmptyLayout();
        });
        reviewViewModel.getChangeLikeOfUserToUserReviewOfContentLiveData().observe(
                getViewLifecycleOwner(), this::handleChangeLikeToUserReview);
        reviewViewModel.getNetworkErrorLiveData().observe(getViewLifecycleOwner(), aBoolean ->
                handleNetworkError(aBoolean, reviewViewModel.getNetworkErrorLiveData()));
    }

    private void setContentUi() {
        watchlistAdapter = new ContentSectionAdapter(
                requireContext(), ContentSection.ViewType.POSTER_TYPE, new ArrayList<>(), this);
        binding.recyclerViewRecentWatchlist.setLayoutManager(new LinearLayoutManager(
                requireContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.recyclerViewRecentWatchlist.setAdapter(watchlistAdapter);

        reviewAdapter = new ReviewAdapter<>(requireContext(), new ArrayList<>(), this);
        binding.recyclerViewRecentReviews.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerViewRecentReviews.setAdapter(reviewAdapter);
    }

    private void setListener() {
        binding.swipeContainer.setOnRefreshListener(() -> {
            binding.swipeContainer.setRefreshing(false);
            updateAllData();
        });
        binding.materialToggleGroup.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if (isChecked){
                handleCheckedButton(checkedId);
            }
        });
        binding.materialToolbar.setOnMenuItemClickListener(menuItem -> {
            int itemId = menuItem.getItemId();
            if (itemId == R.id.logoutButton) {
                verifiedAccountViewModel.logOut();
                return true;
            } else if (itemId == R.id.settingProfile) {
                ((VerifiedAccountActivity) requireActivity()).openSettingsActivity();
                return true;
            }
            return false;
        });
        binding.seeAllRecentWatched.setOnClickListener(v ->
                ((VerifiedAccountActivity) requireActivity()).openUserWatchlistActivity());
        binding.seeAllRecentReviews.setOnClickListener(v ->
                ((VerifiedAccountActivity) requireActivity()).openUserReviewsActivity());
    }

    private void handleCheckedButton(@IdRes int checkedId) {
        if (checkedId == R.id.buttonMovies) {
            fetchWatchlistData(
                    watchlistViewModel.getUserMovieWatchlistLiveData().getValue(),
                    ContentTypeMappingManager.ContentType.MOVIE.getType()
            );
            fetchReviewData(
                    reviewViewModel.getUserMovieReviewListLiveData().getValue(),
                    ContentTypeMappingManager.ContentType.MOVIE.getType()
            );
        } else if (checkedId == R.id.buttonSeries) {
            fetchWatchlistData(
                    watchlistViewModel.getUserTvWatchlistLiveData().getValue(),
                    ContentTypeMappingManager.ContentType.TV.getType()
            );
            fetchReviewData(
                    reviewViewModel.getUserTvReviewListLiveData().getValue(),
                    ContentTypeMappingManager.ContentType.TV.getType()
            );
        }
    }

    private void updateAllData() {
        watchlistViewModel.getUserMovieWatchlistLiveData().postValue(null);
        reviewViewModel.getUserMovieReviewListLiveData().postValue(null);
        watchlistViewModel.getUserTvWatchlistLiveData().postValue(null);
        reviewViewModel.getUserTvReviewListLiveData().postValue(null);
        handleCheckedButton(binding.materialToggleGroup.getCheckedButtonId());
    }

    private void fetchWatchlistData(List<AbstractContent> userDataList, String type) {
        if (userDataList == null) {
            watchlistViewModel.getUserContentWatchlist(type, USER_RECENT_WATCHLIST_COUNT);
        } else {
            setWatchlistAdapterData(userDataList);
        }
    }

    private void fetchReviewData(List<ContentUserReview> userDataList, String type) {
        if (userDataList == null) {
            reviewViewModel.getUserReviewList(type, USER_RECENT_REVIEW_COUNT);
        } else {
            setReviewAdapterData(userDataList);
        }
    }

    private void setWatchlistAdapterData(List<AbstractContent> userDataList) {
        watchlistAdapter.setData(userDataList);
        binding.watchlistLinearLayout.setVisibility((userDataList.size() == 0) ? View.GONE : View.VISIBLE);
        handleEmptyLayout();
    }

    private void setReviewAdapterData(List<ContentUserReview> userDataList) {
        reviewAdapter.setData(userDataList);
        binding.reviewLinearLayout.setVisibility((userDataList.size() == 0) ? View.GONE : View.VISIBLE);
        handleEmptyLayout();
    }

    private void handleEmptyLayout() {
        if (binding.watchlistLinearLayout.getVisibility() == View.GONE &&
                binding.reviewLinearLayout.getVisibility() == View.GONE) {
            binding.emptyContentLayout.getRoot().setVisibility(View.VISIBLE);
        } else {
            binding.emptyContentLayout.getRoot().setVisibility(View.GONE);
        }
    }

    /**
     * Handles the user's authentication status and updates the UI accordingly.
     *
     * @param user The current {@link User} user object representing the logged-in user.
     */
    private void handleUser(User user) {
        if (user != null) {
            binding.userName.setText(String.format("%s", user.getUsername()));
            if (user.getPhotoUrl() != null) {
                Glide.with(this)
                        .load(user.getPhotoUrl())
                        .into(binding.profileImage);
            }
        }
    }

    /**
     * Handles the event when the user is logged out.
     *
     * @param loggedOut A boolean indicating whether the user has been successfully logged out.
     */
    private void handleLoggedOut(Boolean loggedOut) {
        if (loggedOut) {
            ((VerifiedAccountActivity) requireActivity()).openAuthActivity();
        }
    }

    private void handleNetworkError(Boolean bool, MutableLiveData<Boolean> networkMutableLiveData) {
        if (bool != null && bool) {
            ((VerifiedAccountActivity) requireActivity()).openNetworkErrorActivity();
            networkMutableLiveData.setValue(null);
        }
    }

    private void handleChangeLikeToUserReview(UserReview userReview) {
        reviewAdapter.handleChangeLikeToUserReview(userReview);
    }

    @Override
    public void onContentClick(AbstractContent content) {
        ContentDetailsActivityOpener.openContentDetailsActivity(
                requireContext(),
                ((VerifiedAccountActivity) requireActivity()).getNavController(),
                content
        );
    }

    @Override
    public void onUserReviewClick(ContentUserReview userReview) {
        ContentDetailsActivityOpener.openContentDetailsActivity(
                requireContext(),
                ((VerifiedAccountActivity) requireActivity()).getNavController(),
                userReview.getContent()
        );
    }

    @Override
    public void addLikeToUserReview(ContentUserReview userReview) {
        reviewViewModel.addLikeOfUserToUserReviewOfContent(userReview.getContent(), userReview);
    }

    @Override
    public void removeLikeToUserReview(ContentUserReview userReview) {
        reviewViewModel.removedLikeOfUserToUserReviewOfContent(userReview.getContent(), userReview);
    }

}