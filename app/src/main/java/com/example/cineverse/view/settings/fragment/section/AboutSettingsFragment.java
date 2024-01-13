package com.example.cineverse.view.settings.fragment.section;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

import com.example.cineverse.R;
import com.example.cineverse.databinding.FragmentAboutSettingsBinding;
import com.example.cineverse.view.settings.SettingsActivity;

public class AboutSettingsFragment extends Fragment {

    private FragmentAboutSettingsBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAboutSettingsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setActionBar();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((SettingsActivity) requireActivity()).setActionBarStyle();
        binding = null;
    }

    private void setActionBar(){
        ActionBar actionBar = ((SettingsActivity) requireActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.about);
        }
    }

}