package com.example.cineverse.view.settings_account;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;

import com.example.cineverse.R;
import com.example.cineverse.databinding.ActivityAccountSettingsBinding;
import com.example.cineverse.databinding.ActivityAuthBinding;
import com.example.cineverse.handler.BackPressedHandler;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;

public class AccountSettingsActivity extends AppCompatActivity {

    private ActivityAccountSettingsBinding binding;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAccountSettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setActionBar();
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

    private void setNavController() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragmentContainerView);
        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();
        }
    }

}