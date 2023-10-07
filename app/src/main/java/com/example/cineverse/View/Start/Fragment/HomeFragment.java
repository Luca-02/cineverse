package com.example.cineverse.View.Start.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.cineverse.R;
import com.example.cineverse.databinding.FragmentHomeBinding;
import com.example.cineverse.ViewModel.HomeViewModel;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private HomeViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        setupViewModel();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setListeners();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    /* - */

    public void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        viewModel.getUserLiveData().observe(getViewLifecycleOwner(), firebaseUser -> {
            if (firebaseUser != null) {
                binding.userEmail.setText(String.format("Logged In User: %s", firebaseUser.getEmail()));
                binding.logoutButton.setEnabled(true);
            } else {
                binding.logoutButton.setEnabled(false);
            }
        });

        viewModel.getLoggedOutLiveData().observe(getViewLifecycleOwner(), loggedOut -> {
            if (loggedOut) {
                Navigation.findNavController(requireView()).navigate(R.id.action_homeFragment_to_loginFragment);
            }
        });
    }

    public void setListeners() {
        binding.logoutButton.setOnClickListener(view -> viewModel.logOut());
    }

}