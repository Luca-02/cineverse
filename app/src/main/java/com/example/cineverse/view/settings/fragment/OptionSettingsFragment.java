package com.example.cineverse.view.settings.fragment;

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
import com.example.cineverse.databinding.FragmentOptionSettingsBinding;
import com.example.cineverse.view.settings.SettingsActivity;
import com.example.cineverse.viewmodel.verified_account.VerifiedAccountViewModel;

public class OptionSettingsFragment extends Fragment {

    private FragmentOptionSettingsBinding binding;
    private VerifiedAccountViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOptionSettingsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setViewModel();
        setListener();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    private void setViewModel() {
        viewModel = new ViewModelProvider(this).get(VerifiedAccountViewModel.class);
        viewModel.getLoggedOutLiveData().observe(getViewLifecycleOwner(), this::handleLoggedOutUser);
    }

    private void setListener() {
        binding.buttonChangeUsername.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(
                        R.id.action_optionSettingsFragment_to_usernameSettingsFragment2));
        binding.buttonChangeTheme.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(
                        R.id.action_optionSettingsFragment_to_changeThemeSettingsFragment));
        binding.buttonAbout.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(
                        R.id.action_optionSettingsFragment_to_aboutSettingsFragment));
        binding.buttonLogout.setOnClickListener(v ->
                viewModel.logOut());
    }

    private void handleLoggedOutUser(Boolean loggedOut) {
        if (loggedOut != null && loggedOut) {
            ((SettingsActivity) requireActivity()).openAuthActivity();
        }
    }

}