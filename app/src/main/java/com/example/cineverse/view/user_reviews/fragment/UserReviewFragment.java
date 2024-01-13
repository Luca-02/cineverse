package com.example.cineverse.view.user_reviews.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.cineverse.R;
import com.example.cineverse.adapter.review.OnReviewClickListener;
import com.example.cineverse.adapter.review.ReviewAdapter;
import com.example.cineverse.data.model.review.ContentUserReview;
import com.example.cineverse.data.model.review.UserReview;
import com.example.cineverse.databinding.FragmentUserReviewBinding;
import com.example.cineverse.utils.mapper.ContentTypeMappingManager;
import com.example.cineverse.view.details.ContentDetailsActivityOpener;
import com.example.cineverse.view.user_reviews.UserReviewsActivity;
import com.example.cineverse.viewmodel.review.ReviewViewModel;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class UserReviewFragment extends Fragment
        implements OnReviewClickListener<ContentUserReview> {

    private FragmentUserReviewBinding binding;
    private ReviewViewModel reviewViewModel;
    private ReviewAdapter<ContentUserReview> reviewAdapter;
    private int checkedItem = 0;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUserReviewBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setActionBarMenu();
        setViewModel();
        setContentUi();
        setListener();
        binding.materialToggleGroup.check(R.id.buttonMovies);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    /**
     * Sets up the ActionBarMenu of the activity related to the fragment.
     */
    private void setActionBarMenu() {
        requireActivity().addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.sort_content_menu, menu);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.sort) {
                    openSortDialog();
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * Sets up the ViewModel for the fragment.
     */
    private void setViewModel() {
        reviewViewModel = new ViewModelProvider(this).get(ReviewViewModel.class);
        reviewViewModel.getUserMovieReviewListLiveData().observe(getViewLifecycleOwner(), contentUserReviews -> {
            if (contentUserReviews != null && binding.materialToggleGroup.getCheckedButtonId() == R.id.buttonMovies) {
                setReviewAdapterData(contentUserReviews);
            }
        });
        reviewViewModel.getUserTvReviewListLiveData().observe(getViewLifecycleOwner(), contentUserReviews -> {
            if (contentUserReviews != null && binding.materialToggleGroup.getCheckedButtonId() == R.id.buttonSeries) {
                setReviewAdapterData(contentUserReviews);
            }
        });
        reviewViewModel.getChangeLikeOfUserToUserReviewOfContentLiveData().observe(
                getViewLifecycleOwner(), this::handleChangeLikeToUserReview);
        reviewViewModel.getNetworkErrorLiveData().observe(getViewLifecycleOwner(), this::handleNetworkError);
    }

    private void setContentUi() {
        reviewAdapter = new ReviewAdapter<>(requireContext(), new ArrayList<>(), this);
        binding.recyclerViewReview.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerViewReview.setAdapter(reviewAdapter);
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
    }

    private void handleCheckedButton(@IdRes int checkedId) {
        if (checkedId == R.id.buttonMovies) {
            fetchReviewData(
                    reviewViewModel.getUserMovieReviewListLiveData().getValue(),
                    ContentTypeMappingManager.ContentType.MOVIE.getType()
            );
        } else if (checkedId == R.id.buttonSeries) {
            fetchReviewData(
                    reviewViewModel.getUserTvReviewListLiveData().getValue(),
                    ContentTypeMappingManager.ContentType.TV.getType()
            );
        }
        checkedItem = 0;
    }

    private void updateAllData() {
        reviewViewModel.getUserMovieReviewListLiveData().postValue(null);
        reviewViewModel.getUserTvReviewListLiveData().postValue(null);
        handleCheckedButton(binding.materialToggleGroup.getCheckedButtonId());
    }

    private void fetchReviewData(List<ContentUserReview> userDataList, String type) {
        if (userDataList == null) {
            reviewViewModel.getUserReviewList(type);
        } else {
            setReviewAdapterData(userDataList);
        }
    }

    private void setReviewAdapterData(List<ContentUserReview> userDataList) {
        if (userDataList.size() > 0) {
            reviewAdapter.setData(userDataList);
            binding.emptyContentLayout.getRoot().setVisibility(View.GONE);
        } else {
            binding.emptyContentLayout.getRoot().setVisibility(View.VISIBLE);
            reviewAdapter.clearData();
        }
    }

    private void handleNetworkError(Boolean bool) {
        if (bool != null && bool) {
            ((UserReviewsActivity) requireActivity()).openNetworkErrorActivity();
            reviewViewModel.getNetworkErrorLiveData().setValue(null);
        }
    }

    private void openSortDialog() {
        String[] sortingArrayString = UserReview.getSortingArrayString(requireContext());
        AtomicInteger selectedItem = new AtomicInteger(checkedItem);
        new MaterialAlertDialogBuilder(requireContext())
                .setTitle(R.string.sort)
                .setPositiveButton(getString(R.string.confirm), (dialog, which) -> {
                    checkedItem = selectedItem.get();
                    reviewAdapter.sortContentList(checkedItem);
                })
                .setSingleChoiceItems(sortingArrayString, checkedItem, (dialog, which) -> {
                    selectedItem.set(which);
                })
                .show();
    }

    private void handleChangeLikeToUserReview(UserReview userReview) {
        reviewAdapter.handleChangeLikeToUserReview(userReview);
        if (checkedItem == 4) {
            reviewAdapter.sortContentList(checkedItem);
        }
    }

    @Override
    public void onUserReviewClick(ContentUserReview userReview) {
        ContentDetailsActivityOpener.openContentDetailsActivity(
                requireContext(),
                ((UserReviewsActivity) requireActivity()).getNavController(),
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