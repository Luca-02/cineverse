package com.example.cineverse.view.verified_account.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.cineverse.R;
import com.example.cineverse.databinding.FragmentDashboardBinding;
import com.example.cineverse.view.verified_account.VerifiedAccountActivity;

/**
 * The {@link DashboardFragment} class represents the dashboard of the application.
 * This fragment displays relevant information and provides navigation options through a BottomNavigationView.
 */
public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setActionBar();
        setNavController();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    /**
     * Sets up the {@link ActionBar} with the provided Toolbar.
     */
    private void setActionBar() {
        ((VerifiedAccountActivity) requireActivity()).setSupportActionBar(binding.materialToolbar);
        ActionBar actionBar = ((VerifiedAccountActivity) requireActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(null);
        }
    }

    /**
     * Sets up the {@link NavController} and connects it with the BottomNavigationView for navigation.
     * This method finds the {@link NavHostFragment} within the child {@link FragmentManager} and links it to
     * the BottomNavigationView using NavigationUI.
     * This is essential for proper navigation between destinations in this fragment.
     */
    private void setNavController() {
        NavHostFragment navHostFragment = (NavHostFragment) getChildFragmentManager()
                .findFragmentById(R.id.dashboardFragmentContainerView);
        if (navHostFragment != null) {
            NavController navController = navHostFragment.getNavController();
            NavigationUI.setupWithNavController(binding.bottomNavigation, navController);
        }
    }

}