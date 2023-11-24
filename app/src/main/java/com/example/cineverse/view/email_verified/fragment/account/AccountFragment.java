package com.example.cineverse.view.email_verified.fragment.account;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.cineverse.databinding.FragmentAccountBinding;
import com.example.cineverse.view.email_verified.EmailVerifiedActivity;
import com.example.cineverse.viewmodel.logged.email_verified.AccountViewModel;
import com.google.firebase.auth.FirebaseUser;

/**
 * Then AccountFragment class representing the user account section of the application.
 * This fragment serves as one of the tabs in the BottomNavigationView.
 */
public class AccountFragment extends Fragment {

    private FragmentAccountBinding binding;
    private AccountViewModel viewModel;

    public AccountFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAccountBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setViewModel();
        setListeners();
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
        viewModel = new ViewModelProvider(this).get(AccountViewModel.class);
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
            ((EmailVerifiedActivity) requireActivity()).openAuthActivity();
        }
    }

}