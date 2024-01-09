package com.example.cineverse.view.details.fragment;

import static com.example.cineverse.utils.constant.GlobalConstant.START_TIMESTAMP_VALUE;
import static com.example.cineverse.view.details.fragment.ReviewDetailsFragment.USER_REVIEW_TAG;

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
    private ReviewViewModel viewModel;
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
        viewModel = new ViewModelProvider(requireActivity()).get(ReviewViewModel.class);
        viewModel.getPagedContentReviewLiveData().observe(this.getViewLifecycleOwner(), this::handlePagedContentReview);
        viewModel.getNetworkErrorLiveData().observe(getViewLifecycleOwner(), this::handleNetworkError);
    }

    private void initContentSection() {
        binding.materialToolbar.setTitle(content.getName());
        if (reviewAdapter == null) {
            reviewAdapter = new ReviewAdapter(requireContext(), new ArrayList<>(), this);
        }
        binding.reviewRecyclerView.setAdapter(reviewAdapter);
        binding.reviewRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        if (viewModel.getLastTimestamp() == START_TIMESTAMP_VALUE) {
            viewModel.getPagedContentReviewOfContent(content);
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

                if (!viewModel.isLoading() && !recyclerView.canScrollVertically(1)) {
                    viewModel.getPagedContentReviewOfContent(content);
                    viewModel.setLoading(true);
                }
            }
        });

        binding.materialToolbar.setNavigationOnClickListener(v ->
                requireActivity().getOnBackPressedDispatcher().onBackPressed());
    }

    private void handlePagedContentReview(List<UserReview> userReviewList) {
        reviewAdapter.addPagingData(userReviewList);
        viewModel.setLoading(false);
    }

    private void handleNetworkError(Boolean bool) {
        if (bool != null && bool) {
            ((ContentDetailsActivity) requireActivity()).openNetworkErrorActivity();
            viewModel.getNetworkErrorLiveData().setValue(null);
        }
    }

    @Override
    public void onUserReviewClick(UserReview userReview) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(USER_REVIEW_TAG, userReview);
        Navigation.findNavController(requireView())
                .navigate(R.id.action_viewAllReviewContentFragment_to_reviewDetailsFragment, bundle);
    }

}