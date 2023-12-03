package com.example.cineverse.view.verified_account.fragment.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.example.cineverse.adapter.HomeContentSectionAdapter;
import com.example.cineverse.data.model.ui.HomeContentSection;
import com.example.cineverse.data.model.user.User;
import com.example.cineverse.databinding.CircularLogoLayoutBinding;
import com.example.cineverse.databinding.FragmentHomeBinding;
import com.example.cineverse.view.verified_account.VerifiedAccountActivity;
import com.example.cineverse.viewmodel.logged.verified_account.section.home.HomeViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@link HomeFragment} class representing the home section of the application.
 * This fragment serves as one of the tabs in the BottomNavigationView.
 */
public class HomeFragment extends Fragment {

    private ActionBar actionBar;
    private FragmentHomeBinding binding;
    private CircularLogoLayoutBinding logoBinding;
    private HomeViewModel viewModel;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setActionBarMenu();
        setViewModel();
        initContentSection();
    }

    @Override
    public void onPause() {
        super.onPause();
        actionBar.setCustomView(null);
        actionBar.setDisplayShowCustomEnabled(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    /**
     * Sets up the ActionBarMenu of the activity related to the fragment.
     */
    private void setActionBarMenu() {
        actionBar =
                ((VerifiedAccountActivity) requireActivity()).getSupportActionBar();
        if (actionBar != null) {
            LayoutInflater inflater = LayoutInflater.from(requireContext());
            logoBinding =
                    CircularLogoLayoutBinding.inflate(inflater);
            actionBar.setCustomView(logoBinding.getRoot());
            actionBar.setDisplayShowCustomEnabled(true);
        }
    }

    /**
     * Sets up the ViewModel for the fragment.
     */
    private void setViewModel() {
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        viewModel.getUserLiveData().observe(getViewLifecycleOwner(), this::handleUser);
        viewModel.getLoggedOutLiveData().observe(getViewLifecycleOwner(), this::handleLoggedOut);
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

    /**
     * Handles the event when the user is logged out.
     *
     * @param loggedOut A boolean indicating whether the user has been successfully logged out.
     */
    private void handleLoggedOut(Boolean loggedOut) {
        if (loggedOut) {
            ((VerifiedAccountActivity) requireActivity()).openAuthActivity();
        }
    }

    private void initContentSection() {
        List<HomeContentSection> sectionList =
                new ArrayList<>(viewModel.getHomeContentSection());

        HomeContentSectionAdapter sectionAdapter = new HomeContentSectionAdapter(
                this,
                requireActivity().getApplication(),
                getViewLifecycleOwner(),
                sectionList
        );

        binding.sectionRecyclerView.setLayoutManager(new LinearLayoutManager(
                requireContext(), LinearLayoutManager.VERTICAL, false));
        binding.sectionRecyclerView.setAdapter(sectionAdapter);
    }

}