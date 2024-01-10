package com.example.cineverse.view.verified_account.fragment.home;

import static com.example.cineverse.view.verified_account.fragment.home.SectionContentFragment.MOVIE_SECTION;
import static com.example.cineverse.view.verified_account.fragment.home.SectionContentFragment.SECTION_TYPE_ARGS;
import static com.example.cineverse.view.verified_account.fragment.home.SectionContentFragment.TV_SECTION;
import static com.example.cineverse.view.view_all_content.ViewAllContentActivity.GENRE_TAG;
import static com.example.cineverse.view.view_all_content.ViewAllContentActivity.TITLE_STRING_ID_TAG;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.cineverse.R;
import com.example.cineverse.data.model.genre.Genre;
import com.example.cineverse.data.model.ui.ChipSelector;
import com.example.cineverse.data.model.ui.ContentSection;
import com.example.cineverse.databinding.FragmentHomeBinding;
import com.example.cineverse.databinding.HomeSectionChipLayoutBinding;
import com.example.cineverse.view.verified_account.VerifiedAccountActivity;
import com.example.cineverse.view.view_all_content.ViewAllContentActivity;
import com.example.cineverse.view.view_all_content.ViewAllContentController;
import com.example.cineverse.viewmodel.verified_account.section.home.content.AbstractSectionContentViewModelFactory;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@link HomeFragment} class representing the home section of the application.
 * This fragment serves as one of the tabs in the BottomNavigationView.
 */
public class HomeFragment extends Fragment {

    public static final int MOVIE_CHIP_POSITION = 0;
    public static final int TV_CHIP_POSITION = 1;

    private FragmentHomeBinding binding;
    private NavController navController;
    private List<ChipSelector> chipList;

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
        setChipGroup(view);
        binding.materialToolbar.setNavigationOnClickListener(v ->
                ((VerifiedAccountActivity) requireActivity()).openDrawer());
        binding.categoryChip.setOnClickListener(v -> openDialog());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding.chipGroup.removeAllViews();
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
     * Sets up Chip Group.
     */
    private void setChipGroup(View view) {
        binding.chipGroup.removeAllViews();
        if (chipList == null) {
            chipList = new ArrayList<>();
            Chip movieChip = createChip(R.string.movies, MOVIE_CHIP_POSITION);
            Chip tvChip = createChip(R.string.tv_series, TV_CHIP_POSITION);
            binding.chipGroup.addView(movieChip);
            binding.chipGroup.addView(tvChip);
        } else {
            for (ChipSelector myChip : chipList) {
                if (myChip.isVisible()) {
                    binding.chipGroup.addView(myChip.getChip());
                } else {
                    binding.categoryChip.setVisibility(View.VISIBLE);
                }
            }
        }

        binding.chipGroup.setOnCheckedStateChangeListener((group, checkedIds) -> {
            int selectedId = group.getCheckedChipId();
            Chip selectedChip = view.findViewById(selectedId);
            int positionRemoved = handleChipSelectedUI(group, selectedChip);
            handleNavigation(positionRemoved);
        });
    }

    /**
     * Creates and initializes a {@link Chip} with the specified text and position.
     * The created chip is added to the {@link #chipList} with the provided position and visibility settings.
     *
     * @param textResId The resource ID of the text to set on the chip.
     * @param position  The position of the chip in the list or group.
     * @return The initialized {@link Chip} instance.
     */
    private Chip createChip(int textResId, int position) {
        Chip chip = HomeSectionChipLayoutBinding.inflate(getLayoutInflater()).getRoot();
        chip.setText(textResId);
        chipList.add(new ChipSelector(chip, position, true));
        return chip;
    }

    /**
     * Handles the UI changes when a chip is selected in a {@link ChipGroup}.
     * This method updates the visibility and appearance of chips based on the selected chip.
     *
     * @param group        The {@link ChipGroup} containing the chips.
     * @param selectedChip The selected {@link Chip} in the group.
     * @return The position of the chip removed from the group, or -1 if no chip was removed.
     */
    public int handleChipSelectedUI(ChipGroup group, Chip selectedChip) {
        int positionRemoved = -1;
        if (selectedChip != null) {
            selectedChip.setCloseIconVisible(true);
            for (int i = 0; i < group.getChildCount(); i++) {
                Chip chip = (Chip) group.getChildAt(i);
                if (chip.getId() != selectedChip.getId()) {
                    group.removeView(chip);
                    chipList.get(i).setVisible(false);
                    positionRemoved = i;
                }
            }
        } else {
            for (ChipSelector myChip : chipList) {
                myChip.getChip().setCloseIconVisible(false);
                if (!myChip.isVisible()) {
                    binding.chipGroup.addView(myChip.getChip(), myChip.getPosition());
                    myChip.setVisible(true);
                }
            }
        }

        return positionRemoved;
    }

    /**
     * Handle navigation through fragment
     *
     * @param positionRemoved Removed chip position
     */
    private void handleNavigation(int positionRemoved) {
        scrollOnTop();
        if (positionRemoved == -1) {
            navigateToFragment(R.id.action_global_allContentFragment, null);
        } else if (positionRemoved == 1) {
            Bundle bundle = new Bundle();
            bundle.putString(SECTION_TYPE_ARGS, MOVIE_SECTION);
            navigateToFragment(R.id.action_global_movieContentFragment, bundle);
        } else if (positionRemoved == 0) {
            Bundle bundle = new Bundle();
            bundle.putString(SECTION_TYPE_ARGS, TV_SECTION);
            navigateToFragment(R.id.action_global_tvContentFragment, bundle);
        }
    }

    /**
     * Navigate to a specific destination
     *
     * @param destinationId Destination id
     * @param bundle {@link Bundle} to pass at specified destination
     */
    private void navigateToFragment(@IdRes int destinationId, Bundle bundle) {
        if (bundle != null) {
            navController.navigate(destinationId, bundle);
            binding.categoryChip.setVisibility(View.VISIBLE);
        } else {
            navController.navigate(destinationId);
            binding.categoryChip.setVisibility(View.GONE);
        }
    }

    /**
     * Opens the {@link ViewAllContentActivity} for the specified section.
     *
     * @param section The {@link ContentSection} to display in the {@link ViewAllContentActivity}.
     */
    public void openViewAllContentActivity(@NotNull ContentSection section) {
        ViewAllContentController.getInstance().setParameters(section.getViewModelFactory().newInstance(), section.getViewModelClass());
        Bundle bundle = new Bundle();
        bundle.putInt(TITLE_STRING_ID_TAG, section.getSectionTitleStringId());
        navController.navigate(R.id.action_global_viewAllContentActivity, bundle);
    }

    /**
     * Opens the {@link ViewAllContentActivity} for the specified genre using the provided {@link AbstractSectionContentViewModelFactory}.
     *
     * @param genre            The {@link Genre} to filter content in the {@link ViewAllContentActivity}.
     * @param viewModelFactory The {@link AbstractSectionContentViewModelFactory} used to create the view model for
     *                         the content from genre section.
     */
    public void openViewAllContentActivity(@NotNull Genre genre, @NotNull AbstractSectionContentViewModelFactory<?> viewModelFactory) {
        ViewAllContentController.getInstance().setParameters(viewModelFactory.newInstance(), viewModelFactory.getViewModelClass());
        Bundle bundle = new Bundle();
        bundle.putParcelable(GENRE_TAG, genre);
        navController.navigate(R.id.action_global_viewAllContentActivity, bundle);
    }

    /**
     * Opens a dialog within the {@link SectionContentFragment} displayed in the current {@link NavHostFragment}.
     * This method is responsible for invoking the {@link SectionContentFragment#openDialog()} method
     * to display a dialog related to the content section.
     */
    private void openDialog() {
        NavHostFragment navHostFragment = (NavHostFragment) getChildFragmentManager()
                .findFragmentById(R.id.homeFragmentContainerView);

        if (navHostFragment != null) {
            SectionContentFragment sectionContentFragment =
                    (SectionContentFragment) navHostFragment
                            .getChildFragmentManager().getFragments().get(0);
            sectionContentFragment.openDialog();
        }
    }

    /**
     * Scrolls the content within the {@link SectionContentFragment} displayed in the current {@link NavHostFragment} to the top.
     * This method is responsible for invoking the {@link SectionContentFragment#scrollOnTop()} method
     * to ensure the content is scrolled to the top of the view.
     */
    private void scrollOnTop() {
        NavHostFragment navHostFragment = (NavHostFragment) getChildFragmentManager()
                .findFragmentById(R.id.homeFragmentContainerView);

        if (navHostFragment != null) {
            SectionContentFragment sectionContentFragment =
                    (SectionContentFragment) navHostFragment
                            .getChildFragmentManager().getFragments().get(0);
            sectionContentFragment.scrollOnTop();
        }
    }

}