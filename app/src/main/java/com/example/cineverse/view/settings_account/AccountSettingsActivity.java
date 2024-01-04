package com.example.cineverse.view.settings_account;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;

import com.example.cineverse.R;
import com.example.cineverse.databinding.ActivityAccountSettingsBinding;
import com.example.cineverse.viewmodel.settings.theme_app.ThemeViewModel;

/**
 * The {@link AccountSettingsActivity} : manages the other Settings Fragments
 * and listen to Theme Changes
*/
public class AccountSettingsActivity extends AppCompatActivity {

    private ActivityAccountSettingsBinding binding;
    private NavController navController;
    private ThemeViewModel themeViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAccountSettingsBinding.inflate(getLayoutInflater());
        themeViewModel = new ViewModelProvider(this).get(ThemeViewModel.class);
        setContentView(binding.getRoot());
        setActionBar();
        observeThemeChanges();
        getWindow().setNavigationBarColor(android.R.attr.colorBackground);
        binding.toolbarSettings.setNavigationOnClickListener(
                view -> getOnBackPressedDispatcher().onBackPressed());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void setActionBar() {
        setSupportActionBar(binding.toolbarSettings);
        setActionBarStyle();
    }

    public void setActionBarStyle(){
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.setting_menu);
        }
    }

    private void observeThemeChanges() {
        themeViewModel.getNightMode(this).observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isNightMode) {
                if (isNightMode) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
            }
        });
    }

    private void setNavController() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragmentContainerView);
        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();
        }
    }

}