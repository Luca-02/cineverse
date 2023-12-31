package com.example.cineverse.view.settings_account.fragment.option_settings;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.cineverse.R;
import com.example.cineverse.data.model.User;
import com.example.cineverse.databinding.FragmentOptionSettingsBinding;
import com.example.cineverse.databinding.FragmentUsernameSettingsBinding;
import com.example.cineverse.view.settings_account.AccountSettingsActivity;
import com.example.cineverse.viewmodel.verified_account.VerifiedAccountViewModel;
import com.google.android.material.appbar.MaterialToolbar;

public class UsernameSettingsFragment extends Fragment {

    private FragmentUsernameSettingsBinding binding;
    private VerifiedAccountViewModel viewModel;
    private ActionBar actionBar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentUsernameSettingsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setViewModel();
        setActionBar();
        if (actionBar != null)
        {
            actionBar.setTitle(R.string.account_settings_name);
        }
    }

    private void setViewModel(){
        viewModel = new ViewModelProvider(this).get(VerifiedAccountViewModel.class);
        viewModel.getUserLiveData().observe(getViewLifecycleOwner(), this::getUserName);
    }

    private void setActionBar(){
        actionBar = ((AccountSettingsActivity)requireActivity()).getSupportActionBar();
    }

    private void getUserName(User user){
        if (user != null) {
            binding.nameUsernameChange.setText(String.format("%s", user.getUsername()));
            binding.nameUsernameChange.setHint(null);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((AccountSettingsActivity)requireActivity()).setActionBarStyle();
        binding = null;
    }

}