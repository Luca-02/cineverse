package com.example.cineverse.view.settings_account.fragment.option_settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.example.cineverse.R;
import com.example.cineverse.databinding.FragmentChangeThemeSettingBinding;
import com.example.cineverse.databinding.FragmentOptionSettingsBinding;
import com.example.cineverse.view.settings_account.AccountSettingsActivity;
import com.google.android.material.appbar.MaterialToolbar;


public class ChangeThemeSettingsFragment extends Fragment {

    private FragmentChangeThemeSettingBinding binding;
    private ActionBar actionBar;
    private SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentChangeThemeSettingBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setNightModeFragment();
        setActionBar();

        if (actionBar != null)
        {
            actionBar.setTitle(R.string.theme_settings_name);
        }

    }


    private void setNightModeFragment(){
       binding.switchMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               if (isChecked) {
                   AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
               } else {
                   AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
               }
           }
       });

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