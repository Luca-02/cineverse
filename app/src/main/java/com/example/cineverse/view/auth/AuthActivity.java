package com.example.cineverse.view.auth;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.cineverse.handler.callback.BackPressedHandler;
import com.example.cineverse.R;
import com.example.cineverse.repository.AbstractAuthRepository;
import com.example.cineverse.repository.AbstractAuthServiceRepository;
import com.example.cineverse.viewmodel.AbstractAuthServicesViewModel;
import com.example.cineverse.databinding.ActivityAuthBinding;

/**
 * The AuthActivity class serves as the entry point of the application for authentication-related flows.
 * It checks if a user is already logged in. If so, it navigates the user to the home screen (HomeActivity).
 * If not, it displays the main authentication screen. This activity also handles network error scenarios by
 * redirecting users to the NetworkErrorActivity.
 */
public class AuthActivity extends AppCompatActivity {

    private ActivityAuthBinding binding;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAuthBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setActionBar();
        setNavController();
        handleOnDestinationChangedListener();
        BackPressedHandler.handleOnBackPressedCallback(this, navController);
        binding.materialToolbar.setNavigationOnClickListener(
                view -> getOnBackPressedDispatcher().onBackPressed());
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
     * Handles the OnDestinationChangedListener to apply animations based on destination changes.
     */
    private void handleOnDestinationChangedListener() {
        final Animation enterAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        final Animation exitAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_out);

        navController.addOnDestinationChangedListener(
                (controller, destination, arguments) -> {
                    boolean hideAppBar = false;
                    if (arguments != null) {
                        hideAppBar = arguments.getBoolean("HideAppBar", false);
                    }
                    if(hideAppBar) {
                        if (binding.appBarLayout.getVisibility() != View.GONE) {
                            // Hide the app bar with animation
                            binding.appBarLayout.setAnimation(exitAnimation);
                            binding.appBarLayout.setVisibility(View.GONE);
                            binding.appBarLayout.startAnimation(exitAnimation);
                        }
                    } else {
                        if (binding.appBarLayout.getVisibility() != View.VISIBLE) {
                            // Show the app bar with animation
                            binding.appBarLayout.setAnimation(enterAnimation);
                            binding.appBarLayout.setVisibility(View.VISIBLE);
                            binding.appBarLayout.startAnimation(enterAnimation);
                        }
                    }
                }
        );
    }

    /**
     * Opens the EmailVerifiedActivity or VerifyEmailActivity based on the email verification status.
     * If the email is verified, it navigates the user to the verified email screen (EmailVerifiedActivity).
     * If not, it navigates to the email verification screen (VerifyEmailActivity).
     */
    public void openLoggedActivity() {
        if (navController != null) {
            boolean isEmailVerified = AbstractAuthRepository.isEmailVerified();
            if (isEmailVerified) {
                navController.navigate(R.id.action_global_emailVerifiedActivity);
            } else {
                navController.navigate(R.id.action_global_verifyEmailActivity);
            }
            finish();
        }
    }

    /**
     * Opens the network error activity (NetworkErrorActivity) and clears network error LiveData in the
     * passed AbstractAuthViewModel to avoid re-opening NetworkErrorActivity when a fragment that
     * contains network error LiveData is recreated.
     *
     * @param viewModel The AbstractAuthViewModel associated with the current authentication context.
     */
    public <T extends AbstractAuthServiceRepository> void openNetworkErrorActivity(
            AbstractAuthServicesViewModel<T> viewModel) {
        if (navController != null) {
            viewModel.clearNetworkErrorLiveData();
            navController.navigate(R.id.action_global_networkErrorActivity);
        }
    }

}