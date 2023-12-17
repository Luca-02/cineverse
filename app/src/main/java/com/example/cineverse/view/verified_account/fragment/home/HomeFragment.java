package com.example.cineverse.view.verified_account.fragment.home;

import static com.example.cineverse.view.verified_account.fragment.home.SectionContentFragment.MOVIE_SECTION;
import static com.example.cineverse.view.verified_account.fragment.home.SectionContentFragment.SECTION_TYPE_ARGS;
import static com.example.cineverse.view.verified_account.fragment.home.SectionContentFragment.TV_SECTION;
import static com.example.cineverse.view.view_all_content.ViewAllContentActivity.GENRE_TAG;
import static com.example.cineverse.view.view_all_content.ViewAllContentActivity.TITLE_STRING_ID_TAG;
import static com.example.cineverse.view.view_all_content.ViewAllContentActivity.VIEW_MODEL_CLASS_NAME_TAG;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.cineverse.R;
import com.example.cineverse.data.model.genre.Genre;
import com.example.cineverse.data.model.ui.ContentSection;
import com.example.cineverse.databinding.FragmentHomeBinding;
import com.example.cineverse.view.view_all_content.ViewAllContentActivity;
import com.google.android.material.chip.Chip;

/**
 * The {@link HomeFragment} class representing the home section of the application.
 * This fragment serves as one of the tabs in the BottomNavigationView.
 */
public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private NavController navController;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setNavController();
        setListener(view);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    /**
     * Sets up the {@link NavController} for navigating between destinations.
     * This method finds the {@link NavHostFragment} and initializes the {@link NavController}.
     */
    private void setNavController() {
        NavHostFragment navHostFragment = (NavHostFragment) getChildFragmentManager()
                .findFragmentById(R.id.homeFragmentContainerView);
        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();
        }
    }

    /**
     * Sets up click listeners for UI elements in the fragment.
     */
    private void setListener(View view) {
        binding.chipGroup.setOnCheckedStateChangeListener((group, checkedIds) -> {
            int selectedId = group.getCheckedChipId();
            Chip selectedChip = view.findViewById(selectedId);

            if (selectedChip == binding.allChip) {
                navController.navigate(R.id.action_global_allContentFragment);
            } else if (selectedChip == binding.movieChip) {
                Bundle bundle = new Bundle();
                bundle.putString(SECTION_TYPE_ARGS, MOVIE_SECTION);
                navController.navigate(R.id.action_global_movieContentFragment, bundle);
            } else if (selectedChip == binding.tvChip) {
                Bundle bundle = new Bundle();
                bundle.putString(SECTION_TYPE_ARGS, TV_SECTION);
                navController.navigate(R.id.action_global_tvContentFragment, bundle);
            }
        });
    }

    /**
     * Opens the {@link ViewAllContentActivity} for the specified section.
     *
     * @param section The {@link ContentSection} to display in the {@link ViewAllContentActivity}.
     */
    public void openViewAllContentActivity(ContentSection section) {
        Bundle bundle = new Bundle();
        bundle.putInt(TITLE_STRING_ID_TAG, section.getSectionTitleStringId());
        bundle.putString(VIEW_MODEL_CLASS_NAME_TAG, section.getViewModelClass().getCanonicalName());
        navController.navigate(R.id.action_global_viewAllContentActivity, bundle);
    }

    /**
     * Opens the {@link ViewAllContentActivity} for the specified section and genre.
     *
     * @param section The {@link ContentSection} to display in the {@link ViewAllContentActivity}.
     * @param genre   The {@link Genre} to filter content in the {@link ViewAllContentActivity}.
     */
    public void openViewAllContentActivity(ContentSection section, Genre genre) {
        Bundle bundle = new Bundle();
        bundle.putString(VIEW_MODEL_CLASS_NAME_TAG, section.getViewModelClass().getCanonicalName());
        bundle.putParcelable(GENRE_TAG, genre);
        navController.navigate(R.id.action_global_viewAllContentActivity, bundle);
    }

}