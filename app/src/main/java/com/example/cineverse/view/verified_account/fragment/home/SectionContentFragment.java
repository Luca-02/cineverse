package com.example.cineverse.view.verified_account.fragment.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.cineverse.adapter.OnContentClickListener;
import com.example.cineverse.adapter.home.GenreListAdapter;
import com.example.cineverse.adapter.home.HomeSectionAdapter;
import com.example.cineverse.data.model.content.AbstractContent;
import com.example.cineverse.data.model.genre.Genre;
import com.example.cineverse.data.model.ui.ContentSection;
import com.example.cineverse.data.source.content.section.MovieFromGenreRemoteDataSource;
import com.example.cineverse.data.source.content.section.TvFromGenreRemoteDataSource;
import com.example.cineverse.databinding.FragmentSectionContentBinding;
import com.example.cineverse.utils.constant.GlobalConstant;
import com.example.cineverse.view.verified_account.VerifiedAccountActivity;
import com.example.cineverse.viewmodel.verified_account.section.home.content.AbstractSectionContentViewModelFactory;
import com.example.cineverse.viewmodel.verified_account.section.home.content.section.SectionMovieViewModelFactory;
import com.example.cineverse.viewmodel.verified_account.section.home.content.section.SectionTvViewModelFactory;
import com.example.cineverse.viewmodel.verified_account.section.home.genre.AbstractContentGenreViewModel;
import com.example.cineverse.viewmodel.verified_account.section.home.genre.section.MovieContentGenreViewModel;
import com.example.cineverse.viewmodel.verified_account.section.home.genre.section.TvContentGenreViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@link SectionContentFragment} class represents a fragment displaying all content sections.
 * This fragment is part of the home section of the application.
 */
public class SectionContentFragment extends Fragment
        implements HomeSectionAdapter.OnSectionClickListener,
        GenreListAdapter.OnGenreClickListener,
        OnContentClickListener {

    public static final String SECTION_TYPE_ARGS = "sectionType";
    public static final String MOVIE_SECTION = "movie";
    public static final String TV_SECTION = "tv";

    private FragmentSectionContentBinding binding;
    private AbstractContentGenreViewModel genreViewModel;
    private HomeSectionAdapter sectionAdapter;
    private String sectionType;
    private GenreListDialog genreListDialog;

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
        createGenreDialog();
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
        if (sectionType != null) {
            switch (sectionType) {
                case MOVIE_SECTION:
                    genreViewModel = new ViewModelProvider(this).get(MovieContentGenreViewModel.class);
                    break;
                case TV_SECTION:
                    genreViewModel = new ViewModelProvider(this).get(TvContentGenreViewModel.class);
                    break;
                default:
                    break;
            }
        }

        if (genreViewModel != null) {
            genreViewModel.getContentLiveData().observe(getViewLifecycleOwner(), genres -> {
                if (genreListDialog != null) {
                    genreListDialog.setData(genres);
                }
            });
        }
    }

    /**
     * Create the Dialog for genre content list
     */
    private void createGenreDialog() {
        genreListDialog = new GenreListDialog(requireContext(), this);
        genreListDialog.getDialog().setOnShowListener(dialog -> {
            blurBackground();
            genreListDialog.scrollOnTop();
        });
        genreListDialog.getDialog().setOnDismissListener(dialog ->
                clearBlurBackground());

        if (genreViewModel != null && genreViewModel.isContentEmpty()) {
            genreViewModel.fetch();
        }
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
                case MOVIE_SECTION:
                    sectionList.addAll(HomeSectionContentManager
                            .getMovieContentSection(requireActivity().getApplication()));
                    break;
                case TV_SECTION:
                    sectionList.addAll(HomeSectionContentManager
                            .getTvContentSection(requireActivity().getApplication()));
                    break;
                default:
                    break;
            }
        } else {
            sectionList.addAll(HomeSectionContentManager
                    .getAllContentSection(requireActivity().getApplication()));
        }

        sectionAdapter = new HomeSectionAdapter(
                this,
                requireContext(),
                getViewLifecycleOwner(),
                view,
                sectionList,
                this,
                this);

        binding.sectionRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.sectionRecyclerView.setAdapter(sectionAdapter);
        binding.sectionRecyclerView.setHasFixedSize(true);
    }

    public void openDialog() {
        if (genreListDialog != null) {
            genreListDialog.getDialog().show();
        }
    }

    public void scrollOnTop() {
        binding.sectionRecyclerView.smoothScrollToPosition(0);
    }

    private void blurBackground() {
        ((VerifiedAccountActivity) requireActivity()).enableBlur(true);
    }

    private void clearBlurBackground() {
        ((VerifiedAccountActivity) requireActivity()).enableBlur(false);
    }

    /**
     * Handles the click event for the "View All" button in a content section.
     *
     * @param section The selected content section.
     */
    @Override
    public void onViewAllClick(ContentSection section) {
        if (section != null) {
            HomeFragment homeFragment = (HomeFragment) requireParentFragment().requireParentFragment();
            homeFragment.openViewAllContentActivity(section);
        }
    }

    /**
     * Handles the click event for a specific genre in a content section.
     *
     * @param genre The selected genre.
     */
    @Override
    public void onGenreClick(Genre genre) {
        if (genre != null && sectionType != null) {
            HomeFragment homeFragment = (HomeFragment) requireParentFragment().requireParentFragment();
            AbstractSectionContentViewModelFactory<?> viewModelFactory = null;
            switch (sectionType) {
                case MOVIE_SECTION:
                    viewModelFactory = new SectionMovieViewModelFactory(
                            requireActivity().getApplication(),
                            new MovieFromGenreRemoteDataSource(requireActivity().getApplication(), genre.getId())
                    );
                    break;
                case TV_SECTION:
                    viewModelFactory = new SectionTvViewModelFactory(
                            requireActivity().getApplication(),
                            new TvFromGenreRemoteDataSource(requireActivity().getApplication(), genre.getId())
                    );
                    break;
                default:
                    break;
            }
            if (viewModelFactory != null) {
                homeFragment.openViewAllContentActivity(genre, viewModelFactory);
            }
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