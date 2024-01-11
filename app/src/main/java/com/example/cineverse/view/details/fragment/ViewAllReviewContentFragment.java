package com.example.cineverse.view.details.fragment;

import static com.example.cineverse.utils.constant.GlobalConstant.START_TIMESTAMP_VALUE;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cineverse.R;
import com.example.cineverse.adapter.review.OnReviewClickListener;
import com.example.cineverse.adapter.review.ReviewAdapter;
import com.example.cineverse.data.model.content.AbstractContent;
import com.example.cineverse.data.model.review.UserReview;
import com.example.cineverse.databinding.FragmentViewAllReviewContentBinding;
import com.example.cineverse.view.details.ContentDetailsActivity;
import com.example.cineverse.viewmodel.review.ReviewViewModel;

import java.util.ArrayList;
import java.util.List;

public class ViewAllReviewContentFragment extends Fragment
        implements OnReviewClickListener {

    public static final String CONTENT_TAG = "Content";

    private FragmentViewAllReviewContentBinding binding;
    private ReviewViewModel reviewViewModel;
    private ReviewAdapter reviewAdapter;
    private AbstractContent content;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentViewAllReviewContentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getExtras();
        if (content != null) {
            setViewModel();
            initContentSection();
            setListener();
        }
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
        reviewViewModel = new ViewModelProvider(requireActivity()).get(ReviewViewModel.class);
        reviewViewModel.getPagedUserReviewOfContentLiveData().observe(this.getViewLifecycleOwner(), this::handlePagedContentReview);
        reviewViewModel.getChangeLikeOfUserToUserReviewOfContentLiveData().observe(
                getViewLifecycleOwner(), this::handleChangeLikeToUserReview);
        reviewViewModel.getNetworkErrorLiveData().observe(getViewLifecycleOwner(), this::handleNetworkError);
    }

    private void initContentSection() {
        binding.materialToolbar.setTitle(content.getName());
        if (reviewAdapter == null) {
            reviewAdapter = new ReviewAdapter(requireContext(), new ArrayList<>(), this);
        }
        binding.reviewRecyclerView.setAdapter(reviewAdapter);
        binding.reviewRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        if (reviewViewModel.getLastTimestamp() == START_TIMESTAMP_VALUE) {
            reviewViewModel.getPagedUserReviewOfContent(content);
        }
    }

    /**
     * Sets up the UI button listeners for navigation, text input, and login functionality.
     */
    private void setListener() {
        binding.reviewRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (!reviewViewModel.isLoading() && !recyclerView.canScrollVertically(1)) {
                    reviewViewModel.getPagedUserReviewOfContent(content);
                    reviewViewModel.setLoading(true);
                }
            }
        });

        binding.materialToolbar.setNavigationOnClickListener(v ->
                requireActivity().getOnBackPressedDispatcher().onBackPressed());
    }

    private void handlePagedContentReview(List<UserReview> userReviewList) {
        reviewAdapter.addPagingData(userReviewList);
        reviewViewModel.setLoading(false);
    }

    private void handleChangeLikeToUserReview(UserReview userReview) {
        if (userReview != null) {
            reviewAdapter.handleChangeLikeToUserReview(userReview);
        }
    }

    private void handleNetworkError(Boolean bool) {
        if (bool != null && bool) {
            ((ContentDetailsActivity) requireActivity()).openNetworkErrorActivity();
            reviewViewModel.getNetworkErrorLiveData().setValue(null);
        }
    }

    @Override
    public void onUserReviewClick(UserReview userReview) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(ReviewDetailsFragment.CONTENT_TAG, content);
        bundle.putParcelable(ReviewDetailsFragment.USER_REVIEW_TAG, userReview);
        bundle.putBoolean(ReviewDetailsFragment.WITH_LIKE_SECTION_TAG, true);
        Navigation.findNavController(requireView())
                .navigate(R.id.action_viewAllReviewContentFragment_to_reviewDetailsFragment, bundle);
    }

    @Override
    public void addLikeToUserReview(UserReview userReview) {
        reviewViewModel.addLikeOfUserToUserReviewOfContent(content, userReview);
    }

    @Override
    public void removeLikeToUserReview(UserReview userReview) {
        reviewViewModel.removedLikeOfUserToUserReviewOfContent(content, userReview);
    }

}