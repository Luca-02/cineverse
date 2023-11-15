package com.example.cineverse.View.Home.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.cineverse.View.Home.HomeActivity;
import com.example.cineverse.ViewModel.Home.HomeViewModel;
import com.example.cineverse.databinding.FragmentHomeBinding;
import com.google.firebase.auth.FirebaseUser;

/**
 * A fragment representing the home screen of the application, where the logged-in user's information is displayed.
 */
public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private HomeViewModel viewModel;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment HomeFragment.
     */
    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                requireActivity().finish();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
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
        setupViewModel();
        setListeners();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    /**
     * Initializes the ViewModel associated with this fragment.
     */
    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        viewModel.getUserLiveData().observe(getViewLifecycleOwner(), this::handleUser);
        viewModel.getLoggedOutLiveData().observe(getViewLifecycleOwner(), this::handleLoggedOut);
    }

    /**
     * Sets up click listeners for UI elements in the fragment.
     */
    private void setListeners() {
        binding.logoutButton.setOnClickListener(view -> viewModel.logOut());
    }

    /**
     * Handles the user's authentication status and updates the UI accordingly.
     *
     * @param firebaseUser The current Firebase user object representing the logged-in user.
     */
    private void handleUser(FirebaseUser firebaseUser) {
        if (firebaseUser != null) {
            binding.userEmail.setText(String.format("Logged In User: %s", firebaseUser.getEmail()));
            binding.logoutButton.setEnabled(true);
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
            ((HomeActivity) requireActivity()).openAuthActivity();
        }
    }

}