package com.example.cineverse.view.email_verified;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.cineverse.handler.callback.BackPressedHandler;
import com.example.cineverse.R;
import com.example.cineverse.databinding.ActivityEmailVerifiedBinding;

/**
 * The EmailVerifiedActivity class represents the main activity of the application after the user has logged in
 * and their email is verified. It provides the main interface for the user to interact with the app's
 * features.
 */
public class EmailVerifiedActivity extends AppCompatActivity {

    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityEmailVerifiedBinding binding = ActivityEmailVerifiedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setNavController();
        BackPressedHandler.handleOnBackPressedCallback(this, navController);
    }

    /**
     * Sets up the NavController for navigating between destinations.
     * This method finds the NavHostFragment and initializes the NavController.
     */
    private void setNavController() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragmentContainerView);
        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();
        }
    }

    /**
     * Opens the authentication activity (MainActivity).
     */
    public void openAuthActivity() {
        if (navController != null) {
            navController.navigate(R.id.action_global_authActivity);
            finish();
        }
    }

}