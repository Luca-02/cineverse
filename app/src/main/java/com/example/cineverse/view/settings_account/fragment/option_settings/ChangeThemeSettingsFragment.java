package com.example.cineverse.view.settings_account.fragment.option_settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.example.cineverse.R;
import com.example.cineverse.databinding.FragmentChangeThemeSettingsBinding;
import com.example.cineverse.view.settings_account.AccountSettingsActivity;


public class ChangeThemeSettingsFragment extends Fragment {

    private FragmentChangeThemeSettingsBinding binding;
    private ActionBar actionBar;

    private int currentNightMode;
    private boolean nightModeResult;
    private SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentChangeThemeSettingsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        nightModeResult = verificaThemeCorrente();
        if (nightModeResult) {
            binding.switchMode.setChecked(true);
        } else {
            binding.switchMode.setChecked(false);
        }
        setNightModeFragment();
        setActionBar();

        if (actionBar != null)
        {
            actionBar.setTitle(R.string.theme_settings_name);
        }

    }

    private boolean verificaThemeCorrente(){
        currentNightMode = getContext().getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        boolean isNightMode = currentNightMode == Configuration.UI_MODE_NIGHT_YES;

        if (isNightMode)
        {
            Log.d("night", "Night Mode On");
        }else {
            Log.d("day", "Night Mode Off");
        }
        return isNightMode;
    }

    private void setNightModeFragment(){
       binding.switchMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               if (isChecked) {
                   binding.switchMode.setChecked(true);
                   AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

               } else {
                   binding.switchMode.setChecked(false);
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