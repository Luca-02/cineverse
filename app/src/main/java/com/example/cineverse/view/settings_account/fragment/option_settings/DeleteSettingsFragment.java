package com.example.cineverse.view.settings_account.fragment.option_settings;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cineverse.R;
import com.example.cineverse.databinding.FragmentDeleteSettingsBinding;
import com.example.cineverse.view.settings_account.AccountSettingsActivity;


public class DeleteSettingsFragment extends Fragment {

    private FragmentDeleteSettingsBinding binding;
    private ActionBar actionBar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDeleteSettingsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setActionBar();
        if (actionBar != null)
        {
            actionBar.setTitle(R.string.delete_settings_name);
        }
    }

    private void setActionBar(){
        actionBar = ((AccountSettingsActivity)requireActivity()).getSupportActionBar();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((AccountSettingsActivity)requireActivity()).setActionBarStyle();
        binding = null;
    }
}