package com.example.cineverse.view.search_result.fragment;

import static com.example.cineverse.utils.constant.Api.STARTING_PAGE;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cineverse.adapter.content.OnContentClickListener;
import com.example.cineverse.adapter.content.SearchContentAdapter;
import com.example.cineverse.data.model.api.Failure;
import com.example.cineverse.data.model.content.AbstractContent;
import com.example.cineverse.databinding.FragmentSearchResultBinding;
import com.example.cineverse.utils.constant.GlobalConstant;
import com.example.cineverse.view.details.ContentDetailsActivityOpener;
import com.example.cineverse.view.search_result.SearchResultActivity;
import com.example.cineverse.view.view_all_content.ViewAllContentActivity;
import com.example.cineverse.viewmodel.search_result.SearchResultViewModel;
import com.example.cineverse.viewmodel.search_result.SearchResultViewModelFactory;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class SearchResultFragment extends Fragment
        implements OnContentClickListener {

    private static final String SAVE_RECYCLER_VIEW_CONTENT_ID = "SaveRecyclerViewContentSearchResult";

    private FragmentSearchResultBinding binding;
    private SearchResultViewModel viewModel;
    private SearchContentAdapter contentAdapter;

    private RecyclerView recyclerView;
    private boolean savedInstanceStateIsNull = true;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSearchResultBinding.inflate(inflater, container, false);
        recyclerView = binding.contentRecyclerView;
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setViewModel();
        initContentSection(savedInstanceState);
        setListener();
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

    /**
     * Restores the previous state of the RecyclerView using the provided savedInstanceState.
     *
     * @param savedInstanceState The saved instance state bundle.
     */
    public void restorePreviousState(Bundle savedInstanceState){
        List<AbstractContent> dataset = savedInstanceState
                .getParcelableArrayList(SAVE_RECYCLER_VIEW_CONTENT_ID);
        contentAdapter.addPagingData(dataset);
    }

    /**
     * Sets up the ViewModel for the fragment.
     */
    private void setViewModel() {
        String query = ((SearchResultActivity) requireActivity()).getQuery();
        viewModel = new ViewModelProvider(this,
                new SearchResultViewModelFactory(requireActivity().getApplication(), query))
                .get(SearchResultViewModel.class);
        viewModel.getContentLiveData().observe(this.getViewLifecycleOwner(), this::handleContent);
        viewModel.getFailureLiveData().observe(this.getViewLifecycleOwner(), this::handleFailure);
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

                if (!viewModel.isLoading() && !recyclerView.canScrollVertically(1)) {
                    viewModel.fetchAndIncreasePage();
                    viewModel.setLoading(true);
                }
            }
        });
    }

    private void initContentSection(Bundle savedInstanceState) {
        contentAdapter = new SearchContentAdapter(requireContext(), new ArrayList<>(), this);
        binding.contentRecyclerView.setAdapter(contentAdapter);
        binding.contentRecyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 3));
        handleRecyclerViewState(savedInstanceState);
    }

    /**
     * Handles the state of the RecyclerView, initializing it or restoring its state based on the provided savedInstanceState.
     *
     * @param savedInstanceState The saved instance state bundle.
     */
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

    /**
     * Handles the content received from the ViewModel, updating the RecyclerView's data.
     *
     * @param abstractPosters The list of abstract posters received from the ViewModel.
     */
    public void handleContent(List<? extends AbstractContent> abstractPosters) {
        if (savedInstanceStateIsNull) {
            contentAdapter.addPagingData(abstractPosters);
        } else {
            savedInstanceStateIsNull = true;
        }
        viewModel.setLoading(false);
    }

    /**
     * Handles a failure in fetching content, displaying a Snackbar message if applicable.
     *
     * @param failure The failure response received from the ViewModel.
     */
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
        ContentDetailsActivityOpener.openContentDetailsActivity(
                requireContext(),
                ((SearchResultActivity) requireActivity()).getNavController(),
                content
        );
    }

}