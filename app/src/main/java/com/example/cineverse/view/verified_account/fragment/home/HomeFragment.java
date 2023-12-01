package com.example.cineverse.view.verified_account.fragment.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.cineverse.adapter.PosterMovieAdapter;
import com.example.cineverse.data.model.content.PosterMovie;
import com.example.cineverse.data.model.user.User;
import com.example.cineverse.databinding.FragmentHomeBinding;
import com.example.cineverse.view.verified_account.VerifiedAccountActivity;
import com.example.cineverse.viewmodel.logged.verified_account.section.HomeViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@link HomeFragment} class representing the home section of the application.
 * This fragment serves as one of the tabs in the BottomNavigationView.
 */
public class HomeFragment extends Fragment {

    // every page in 20 item with TMDB Api
    public static final int MAX_PAGE_TO_LOAD = 4;

    private FragmentHomeBinding binding;
    private HomeViewModel viewModel;

    private PosterMovieAdapter popularMovieAdapter;

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
        initUi();
        setViewModel();
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
        if (viewModel == null) {
            viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
            viewModel.getUserLiveData().observe(getViewLifecycleOwner(), this::handleUser);
            viewModel.getLoggedOutLiveData().observe(getViewLifecycleOwner(), this::handleLoggedOut);
            viewModel.getPopularMovieLiveData().observe(getViewLifecycleOwner(), this::handlePopularMovie);
        }
    }

    /**
     * Handles the user's authentication status and updates the UI accordingly.
     *
     * @param user The current {@link User} user object representing the logged-in user.
     */
    private void handleUser(User user) {
        if (user != null) {
            binding.usernameTextView.setText(user.getUsername());
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

    @SuppressLint("NotifyDataSetChanged")
    private void handlePopularMovie(List<PosterMovie> posterMovies) {
        popularMovieAdapter.setData(posterMovies);
    }

    private void initUi() {
        initPopularMovieUi();
    }

    private void initPopularMovieUi() {
        List<PosterMovie> posterMovies = new ArrayList<>();
        if (viewModel != null) {
            posterMovies = viewModel.getPopularMovieLiveData().getValue();
        }

        popularMovieAdapter =
                new PosterMovieAdapter(requireContext(), posterMovies);

        binding.popularMovieRecyclerView.setLayoutManager(new LinearLayoutManager(
                requireContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.popularMovieRecyclerView.setAdapter(popularMovieAdapter);
    }

}