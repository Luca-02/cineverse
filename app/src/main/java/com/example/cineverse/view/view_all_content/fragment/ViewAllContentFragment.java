package com.example.cineverse.view.view_all_content.fragment;

import static com.example.cineverse.utils.constant.Api.STARTING_PAGE;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cineverse.adapter.OnContentClickListener;
import com.example.cineverse.adapter.view_all_content.ContentViewAllAdapter;
import com.example.cineverse.data.model.api.Failure;
import com.example.cineverse.data.model.content.AbstractContent;
import com.example.cineverse.databinding.FragmentViewAllContentBinding;
import com.example.cineverse.utils.constant.GlobalConstant;
import com.example.cineverse.view.view_all_content.ViewAllContentActivity;
import com.example.cineverse.viewmodel.verified_account.section.home.content.AbstractSectionContentViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class ViewAllContentFragment extends Fragment
        implements OnContentClickListener {

    private static final String SAVE_RECYCLER_VIEW_CONTENT_ID = "SaveRecyclerViewContent";

    private FragmentViewAllContentBinding binding;
    private AbstractSectionContentViewModel viewModel;
    private ContentViewAllAdapter contentAdapter;

    private RecyclerView recyclerView;
    private boolean isLoading = false;
    private boolean savedInstanceStateIsNull = true;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentViewAllContentBinding.inflate(inflater, container, false);
        recyclerView = binding.contentRecyclerView;
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setViewModel();
        if (viewModel != null) {
            initContentSection(savedInstanceState);
            setListener();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager != null) {
            outState.putParcelableArrayList(SAVE_RECYCLER_VIEW_CONTENT_ID,
                    (ArrayList<AbstractContent>) contentAdapter.getData());
        }
        super.onSaveInstanceState(outState);
    }

    public void restorePreviousState(Bundle savedInstanceState){
        List<AbstractContent> dataset = savedInstanceState
                .getParcelableArrayList(SAVE_RECYCLER_VIEW_CONTENT_ID);
        contentAdapter.setData(dataset);
    }

    /**
     * Sets up the ViewModel for the fragment.
     */
    private void setViewModel() {
        viewModel = ((ViewAllContentActivity) requireActivity()).getViewModel(this);
        if (viewModel != null) {
            viewModel.getContentLiveData().observe(getViewLifecycleOwner(), this::handleContent);
            viewModel.getFailureLiveData().observe(getViewLifecycleOwner(), this::handleFailure);
        }
    }

    /**
     * Sets up click listeners for UI elements in the fragment.
     */
    private void setListener() {
        binding.contentRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (!isLoading && !recyclerView.canScrollVertically(1)) {
                    viewModel.fetchAndIncreasePage();
                    isLoading = true;
                }
            }
        });
    }

    private void initContentSection(Bundle savedInstanceState) {
        contentAdapter = new ContentViewAllAdapter(requireContext(), new ArrayList<>(), this);

        binding.contentRecyclerView.setAdapter(contentAdapter);
        binding.contentRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        handleRecyclerViewState(savedInstanceState);
    }

    public void handleRecyclerViewState(Bundle savedInstanceState) {
        savedInstanceStateIsNull = savedInstanceState == null;
        if (savedInstanceStateIsNull) {
            if (viewModel.getPage() == STARTING_PAGE) {
                viewModel.fetchAndIncreasePage();
            }
        } else {
            restorePreviousState(savedInstanceState);
        }
    }

    public void handleContent(List<? extends AbstractContent> abstractPosters) {
        if (savedInstanceStateIsNull) {
            contentAdapter.setData(abstractPosters);
        } else {
            savedInstanceStateIsNull = true;
        }
        isLoading = false;
    }

    public void handleFailure(Failure failure) {
        /*
         * code [22]
         * HTTP Status [400]
         * Message [Invalid page: Pages start at 1 and max at 500. They are expected to be an integer.]
         */

        if (failure != null) {
            if (!failure.isSuccess() && failure.getStatusCode() != 22) {
                Snackbar.make(requireView(),
                        failure.getStatusMessage(), Snackbar.LENGTH_SHORT).show();
            }
            viewModel.getFailureLiveData().setValue(null);
        }
    }

    /**
     * Handles the click event for a specific content in a content section.
     *
     * @param content The selected content.
     */
    @Override
    public void onContentClick(AbstractContent content) {
        Log.d(GlobalConstant.TAG, "onContentClick: " + content.getClass());
        Log.d(GlobalConstant.TAG, "onContentClick: " + content);
    }

}