package com.example.cineverse.view.verified_account.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.cineverse.R;
import com.example.cineverse.data.model.user.User;
import com.example.cineverse.databinding.CircularLogoLayoutBinding;
import com.example.cineverse.databinding.FragmentDashboardBinding;
import com.example.cineverse.view.verified_account.VerifiedAccountActivity;
import com.example.cineverse.viewmodel.logged.verified_account.VerifiedAccountViewModel;
import com.example.cineverse.viewmodel.logged.verified_account.section.home.HomeViewModel;

/**
 * The {@link DashboardFragment} class represents the dashboard of the application.
 * This fragment displays relevant information and provides navigation options through a BottomNavigationView.
 */
public class DashboardFragment extends Fragment {

    private VerifiedAccountViewModel viewModel;
    private FragmentDashboardBinding binding;
    private CircularLogoLayoutBinding logoBinding;

    public DashboardFragment() {
        // Required empty public constructor
    }

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
        setViewModel();
    }

    /**
     * Sets up the ActionBar of the activity related to the fragment.
     */
    private void setActionBar() {
        ActionBar actionBar =
                ((VerifiedAccountActivity) requireActivity()).getSupportActionBar();
        if (actionBar != null) {
            LayoutInflater inflater = LayoutInflater.from(requireContext());
            logoBinding =
                    CircularLogoLayoutBinding.inflate(inflater);
            actionBar.setCustomView(logoBinding.getRoot());
            actionBar.setDisplayShowCustomEnabled(true);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    /**
     * Sets up the {@link NavController} and connects it with the BottomNavigationView for navigation.
     * This method finds the {@link NavHostFragment} within the child {@link FragmentManager} and links it to
     * the BottomNavigationView using NavigationUI.
     * This is essential for proper navigation between destinations in this fragment.
     */
    private void setNavController() {
        NavHostFragment navHostFragment = (NavHostFragment) getChildFragmentManager()
                .findFragmentById(R.id.navHostFragment);
        if (navHostFragment != null) {
            NavController navController = navHostFragment.getNavController();
            NavigationUI.setupWithNavController(binding.bottomNav, navController);
        }
    }

    /**
     * Sets up the ViewModel for the fragment.
     */
    private void setViewModel() {
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        viewModel.getUserLiveData().observe(getViewLifecycleOwner(), this::handleUser);
    }

    /**
     * Handles the user's authentication status and updates the UI accordingly.
     *
     * @param user The current {@link User} user object representing the logged-in user.
     */
    private void handleUser(User user) {
        if (user != null) {
            String photoUrl = user.getPhotoUrl();
            if (photoUrl != null) {
                Glide.with(requireContext())
                        .load(user.getPhotoUrl())
                        .into(logoBinding.circularImageView);
            }
        } else {
            viewModel.logOut();
        }
    }

}