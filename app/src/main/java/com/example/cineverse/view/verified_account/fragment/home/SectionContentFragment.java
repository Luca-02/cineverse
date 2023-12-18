package com.example.cineverse.view.verified_account.fragment.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.cineverse.adapter.home.HomeSectionAdapter;
import com.example.cineverse.data.model.genre.Genre;
import com.example.cineverse.data.model.ui.ContentSection;
import com.example.cineverse.databinding.FragmentSectionContentBinding;
import com.example.cineverse.viewmodel.verified_account.section.home.HomeViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@link SectionContentFragment} class represents a fragment displaying all content sections.
 * This fragment is part of the home section of the application.
 */
public class SectionContentFragment extends Fragment
        implements HomeSectionAdapter.OnSectionClickListener {

    public static final String SECTION_TYPE_ARGS = "sectionType";
    public static final String MOVIE_SECTION = "movie";
    public static final String TV_SECTION = "tv";

    private FragmentSectionContentBinding binding;
    private HomeViewModel viewModel;
    private HomeSectionAdapter sectionAdapter;
    private String sectionType;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSectionContentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        extractArguments();
        setViewModel();
        initContentSection(view);
        setListener();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    /**
     * Extract arguments from the Bundle
     */
    private void extractArguments() {
        Bundle args = getArguments();
        if (args != null) {
            sectionType = args.getString(SECTION_TYPE_ARGS);
        }
    }

    /**
     * Sets up the ViewModel for the fragment.
     */
    private void setViewModel() {
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
    }

    /**
     * Sets up click listeners for UI elements in the fragment.
     */
    private void setListener() {
        binding.swipeContainer.setOnRefreshListener(() -> {
            sectionAdapter.refresh();
            binding.swipeContainer.setRefreshing(false);
        });
    }

    /**
     * Initializes the content section by setting up the adapter and RecyclerView.
     *
     * @param view The view of the fragment.
     */
    private void initContentSection(View view) {
        List<ContentSection> sectionList = new ArrayList<>();
        if (sectionType != null) {
            switch (sectionType) {
                case "movie":
                    sectionList.addAll(viewModel.getMovieContentSection(true));
                    break;
                case "tv":
                    sectionList.addAll(viewModel.getTvContentSection(true));
                    break;
                default:
                    break;
            }
        } else {
            sectionList.addAll(viewModel.getAllContentSection());
        }

        sectionAdapter = new HomeSectionAdapter(
                this,
                requireContext(),
                getViewLifecycleOwner(),
                view,
                sectionList,
                this
        );

        binding.sectionRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.sectionRecyclerView.setAdapter(sectionAdapter);
        binding.sectionRecyclerView.setHasFixedSize(true);
    }

    /**
     * Handles the click event for the "View All" button in a content section.
     *
     * @param section The selected content section.
     */
    @Override
    public void onViewAllClick(ContentSection section) {
        HomeFragment homeFragment = (HomeFragment) requireParentFragment().requireParentFragment();
        homeFragment.openViewAllContentActivity(section);
    }

    /**
     * Handles the click event for a specific genre in a content section.
     *
     * @param section The selected content section.
     * @param genre   The selected genre.
     */
    @Override
    public void onGenreClick(ContentSection section, Genre genre) {
        HomeFragment homeFragment = (HomeFragment) requireParentFragment().requireParentFragment();
        homeFragment.openViewAllContentActivity(section, genre);
    }

    public void test() {
        binding.sectionRecyclerView.smoothScrollToPosition(0);
    }

}