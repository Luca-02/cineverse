package com.example.cineverse.View.Home.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cineverse.View.Home.LoggedActivity;
import com.example.cineverse.ViewModel.Home.Logged.HomeViewModel;
import com.example.cineverse.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private HomeViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        setupViewModel();
        setListeners();
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        viewModel.getUserLiveData().observe(getViewLifecycleOwner(), firebaseUser -> {
            if (firebaseUser != null) {
                // logged user
                binding.userEmail.setText(String.format("Logged In User: %s", firebaseUser.getEmail()));
                binding.logoutButton.setEnabled(true);
            } else {
                // TODO create a LoggedUserFailureException
            }
        });

        viewModel.getLoggedOutLiveData().observe(getViewLifecycleOwner(), loggedOut -> {
            if (loggedOut) {
                ((LoggedActivity) requireActivity()).openAuthActivity();
            }
        });
    }

        private void setListeners() {
        binding.logoutButton.setOnClickListener(view -> viewModel.logOut());
    }

}