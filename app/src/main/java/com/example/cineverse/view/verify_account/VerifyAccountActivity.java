package com.example.cineverse.view.verify_account;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.cineverse.R;
import com.example.cineverse.databinding.ActivityVerifyAccountBinding;
import com.example.cineverse.view.auth.AuthActivity;
import com.example.cineverse.view.network_error.NetworkErrorActivity;
import com.example.cineverse.view.verified_account.VerifiedAccountActivity;

/**
 * The {@link VerifyAccountActivity} class represents the activity for email verification.
 * It allows users to verify their email address, resent verification emails, and navigate
 * to the home screen upon successful verification.
 */
public class VerifyAccountActivity extends AppCompatActivity {

    private ActivityVerifyAccountBinding binding;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVerifyAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setActionBar();
        setNavController();
        getWindow().setNavigationBarColor(android.R.attr.colorBackground);
    }

    /**
     * Sets up the {@link ActionBar} with the provided Toolbar.
     */
    private void setActionBar() {
        setSupportActionBar(binding.materialToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(null);
        }
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
     * Opens the authentication activity ({@link AuthActivity}) and closes all previous activities in the stack.
     */
    public void openAuthActivity() {
        if (navController != null) {
            navController.navigate(R.id.action_global_authActivity);
            finish();
        }
    }

    /**
     * Opens the email verified activity ({@link VerifiedAccountActivity}) and closes all previous
     * activities in the stack.
     */
    public void openEmailVerifiedActivity() {
        if (navController != null) {
            navController.navigate(R.id.action_global_verifiedAccountActivity);
            finish();
        }
    }

    /**
     * Opens the network error activity ({@link NetworkErrorActivity}).
     */
    public void openNetworkErrorActivity() {
        if (navController != null) {
            navController.navigate(R.id.action_global_networkErrorActivity);
        }
    }

}