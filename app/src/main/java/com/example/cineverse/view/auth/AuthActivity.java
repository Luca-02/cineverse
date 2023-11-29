package com.example.cineverse.view.auth;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.cineverse.R;
import com.example.cineverse.databinding.ActivityAuthBinding;
import com.example.cineverse.handler.callback.BackPressedHandler;
import com.example.cineverse.repository.classes.auth.AbstractAuthRepository;
import com.example.cineverse.view.network_error.NetworkErrorActivity;
import com.example.cineverse.view.verified_account.VerifiedAccountActivity;
import com.example.cineverse.view.verify_account.VerifyAccountActivity;
import com.example.cineverse.viewmodel.auth.AbstractAuthServicesViewModel;

/**
 * The {@link AuthActivity} class serves as the entry point of the application for authentication-related flows.
 * It checks if a user is already logged in. If so, it navigates the user to {@link VerifiedAccountActivity}.
 * If not, it displays the main authentication screen. This activity also handles network error scenarios by
 * redirecting users to the {@link NetworkErrorActivity}.
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
     * Sets up the {@link ActionBar} with the provided Toolbar.
     */
    private void setActionBar() {
        setSupportActionBar(binding.materialToolbar);
        ActionBar actionBar =  getSupportActionBar();
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
     * Opens the {@link VerifiedAccountActivity} or {@link VerifyAccountActivity} based on the email verification status.
     * If the email is verified, it navigates the user to the verified email screen ({@link VerifiedAccountActivity}).
     * If not, it navigates to the email verification screen ({@link VerifyAccountActivity}).
     *
     * @param isEmailVerified A boolean indicating whether the user's email is verified.
     *                        {@code true} if the email is verified, {@code false} otherwise.
     */
    public void openLoggedActivity(boolean isEmailVerified) {
        if (navController != null) {
            if (isEmailVerified) {
                navController.navigate(R.id.action_global_verifiedAccountActivity);
            } else {
                navController.navigate(R.id.action_global_verifyAccountActivity);
            }
            finish();
        }
    }

    /**
     * Opens the network error activity ({@link NetworkErrorActivity}) and clears network error {@link LiveData} in the
     * passed {@link AbstractAuthServicesViewModel} to avoid re-opening {@link NetworkErrorActivity} when a fragment that
     * contains network error {@link LiveData} is recreated.
     *
     * @param viewModel The {@link AbstractAuthServicesViewModel} associated with the current authentication context.
     */
    public <T extends AbstractAuthRepository> void openNetworkErrorActivity(
            AbstractAuthServicesViewModel<T> viewModel) {
        if (navController != null) {
            viewModel.clearNetworkErrorLiveData();
            navController.navigate(R.id.action_global_networkErrorActivity);
        }
    }

}