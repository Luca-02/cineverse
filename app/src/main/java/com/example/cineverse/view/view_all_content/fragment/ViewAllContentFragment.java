package com.example.cineverse.view.view_all_content.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cineverse.adapter.ContentViewAllAdapter;
import com.example.cineverse.data.model.content.AbstractContent;
import com.example.cineverse.data.model.content.Failure;
import com.example.cineverse.databinding.FragmentViewAllContentBinding;
import com.example.cineverse.view.view_all_content.ViewAllContentActivity;
import com.example.cineverse.viewmodel.logged.verified_account.section.home.AbstractSectionViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class ViewAllContentFragment extends Fragment {

    private FragmentViewAllContentBinding binding;
    private AbstractSectionViewModel viewModel;
    private ContentViewAllAdapter contentAdapter;

    private boolean isLoading = false;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentViewAllContentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setViewModel();
        initContentSection();
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
        Class<? extends AbstractSectionViewModel> viewModelClass =
                ((ViewAllContentActivity) requireActivity()).getViewModelClass();
        if (viewModelClass != null) {
            viewModel = new ViewModelProvider(this).get(viewModelClass);
            viewModel.getContentLiveData().observe(getViewLifecycleOwner(), this::handleContent);
            viewModel.getFailureLiveData().observe(getViewLifecycleOwner(), this::handleFailure);
        }
    }

    private void initContentSection() {
        contentAdapter = new ContentViewAllAdapter(
            requireContext(),
                new ArrayList<>()
        );

        binding.contentRecyclerView.setLayoutManager(new LinearLayoutManager(
                requireContext(), LinearLayoutManager.VERTICAL, false));
        binding.contentRecyclerView.setAdapter(contentAdapter);
        viewModel.fetchAndIncreasePage();

        binding.contentRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if (!isLoading) {
                    if (linearLayoutManager != null &&
                            linearLayoutManager.findLastCompletelyVisibleItemPosition() == contentAdapter.getItemCount() - 1) {
                        viewModel.fetchAndIncreasePage();
                        isLoading = true;
                    }
                }
            }
        });
    }

    public void handleContent(List<? extends AbstractContent> abstractPosters) {
        contentAdapter.setData(abstractPosters);
        isLoading = false;
    }

    public void handleFailure(Failure failure) {
        /*
         * code [22]
         * HTTP Status [400]
         * Message [Invalid page: Pages start at 1 and max at 500. They are expected to be an integer.]
         */

        if (!failure.isSuccess() && failure.getStatusCode() != 22) {
            Snackbar.make(requireView(),
                    failure.getStatusMessage(), Snackbar.LENGTH_SHORT).show();
        }
    }

}