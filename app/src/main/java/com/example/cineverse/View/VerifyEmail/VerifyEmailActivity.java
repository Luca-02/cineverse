package com.example.cineverse.View.VerifyEmail;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.cineverse.Handler.Callback.BackPressedHandler;
import com.example.cineverse.R;
import com.example.cineverse.ViewModel.Logged.VerifyEmailViewModel;
import com.example.cineverse.databinding.ActivityVerifyEmailBinding;

/**
 * The VerifyEmailActivity class represents the activity for email verification.
 * It allows users to verify their email address, resent verification emails, and navigate
 * to the home screen upon successful verification.
 */
public class VerifyEmailActivity extends AppCompatActivity {

    private ActivityVerifyEmailBinding binding;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVerifyEmailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setActionBar();
        setNavController();
        BackPressedHandler.handleOnBackPressedCallback(this, navController);
    }

    /**
     * Sets up the action bar with the provided Toolbar.
     */
    private void setActionBar() {
        setSupportActionBar(binding.materialToolbar);
        ActionBar actionBar =  getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(null);
        }
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
     * Opens the authentication activity (AuthActivity) and closes all previous activities in the stack.
     */
    public void openAuthActivity() {
        if (navController != null) {
            navController.navigate(R.id.action_global_authActivity);
            finish();
        }
    }

    /**
     * Opens the email verified activity (EmailVerifiedActivity) and closes all previous
     * activities in the stack.
     */
    public void openEmailVerifiedActivity() {
        if (navController != null) {
            navController.navigate(R.id.action_global_emailVerifiedActivity);
            finish();
        }
    }

    /**
     * Opens the network error activity (NetworkErrorActivity) and clears network error LiveData in the
     * passed AbstractAuthViewModel to avoid re-opening NetworkErrorActivity when a fragment that
     * contains network error LiveData is recreated.
     *
     * @param viewModel The VerifyEmailViewModel associated with the current email verification context.
     */
    public void openNetworkErrorActivity(VerifyEmailViewModel viewModel) {
        if (navController != null) {
            viewModel.clearNetworkErrorLiveData();
            navController.navigate(R.id.action_global_networkErrorActivity);
        }
    }

}