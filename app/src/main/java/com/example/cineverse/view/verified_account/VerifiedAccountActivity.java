package com.example.cineverse.view.verified_account;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.cineverse.R;
import com.example.cineverse.databinding.ActivityVerifiedAccountBinding;
import com.example.cineverse.databinding.DrawerMenuHeaderLayoutBinding;
import com.example.cineverse.handler.BackPressedHandler;
import com.example.cineverse.view.auth.AuthActivity;
import com.google.android.material.elevation.SurfaceColors;

/**
 * The {@link VerifiedAccountActivity} class represents the main activity of the application after the user has logged in
 * and their email is verified. It provides the main interface for the user to interact with the app's
 * features.
 */
public class VerifiedAccountActivity extends AppCompatActivity {

    private ActivityVerifiedAccountBinding binding;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVerifiedAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setNavController();
        BackPressedHandler.handleOnBackPressedCallback(this, navController);
        getWindow().setNavigationBarColor(SurfaceColors.SURFACE_2.getColor(this));

        DrawerMenuHeaderLayoutBinding drawerBinding =
                DrawerMenuHeaderLayoutBinding.inflate(getLayoutInflater());
        binding.navigationView.addHeaderView(drawerBinding.getRoot());
    }

    /**
     * Sets up the {@link NavController} for navigating between destinations.
     * This method finds the {@link NavHostFragment} and initializes the {@link NavController}.
     */
    private void setNavController() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragmentContainerView);
        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();
        }
    }

    /**
     * Opens the authentication activity ({@link AuthActivity}).
     */
    public void openAuthActivity() {
        if (navController != null) {
            navController.navigate(R.id.action_global_authActivity);
            finish();
        }
    }

    public void openDrawer() {
        binding.drawerLayout.open();
    }

}