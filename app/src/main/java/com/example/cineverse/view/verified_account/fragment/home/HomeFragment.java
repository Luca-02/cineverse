package com.example.cineverse.view.verified_account.fragment.home;

import static com.example.cineverse.view.view_all_content.ViewAllContentActivity.TITLE_STRING_ID_TAG;
import static com.example.cineverse.view.view_all_content.ViewAllContentActivity.VIEW_MODEL_CLASS_NAME_TAG;

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
import com.example.cineverse.databinding.FragmentHomeBinding;
import com.example.cineverse.viewmodel.logged.verified_account.section.home.AbstractSectionViewModel;
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
                navController.navigate(R.id.action_global_movieContentFragment);
            } else if (selectedChip == binding.tvChip) {
                navController.navigate(R.id.action_global_tvContentFragment);
            }
        });
    }

    public void openViewAllContentActivity(@IdRes int sectionTitleStringId,
                                           Class<? extends AbstractSectionViewModel> viewModelClass) {
        Bundle bundle = new Bundle();
        bundle.putInt(TITLE_STRING_ID_TAG, sectionTitleStringId);
        bundle.putString(VIEW_MODEL_CLASS_NAME_TAG, viewModelClass.getCanonicalName());
        navController.navigate(R.id.action_global_viewAllContentActivity, bundle);
    }

}