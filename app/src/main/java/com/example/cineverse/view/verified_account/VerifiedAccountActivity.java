package com.example.cineverse.view.verified_account;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.EdgeToEdge;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.cineverse.R;
import com.example.cineverse.data.model.User;
import com.example.cineverse.data.model.ui.BaseActivity;
import com.example.cineverse.databinding.ActivityVerifiedAccountBinding;
import com.example.cineverse.utils.constant.GlobalConstant;
import com.example.cineverse.view.auth.AuthActivity;
import com.example.cineverse.view.search_result.SearchResultActivity;
import com.example.cineverse.view.settings.SettingsActivity;
import com.example.cineverse.viewmodel.verified_account.VerifiedAccountViewModel;

/**
 * The {@link VerifiedAccountActivity} class represents the main activity of the application after the user has logged in
 * and their email is verified. It provides the main interface for the user to interact with the app's
 * features.
 */
public class VerifiedAccountActivity extends BaseActivity {

    private ActivityVerifiedAccountBinding binding;
    private NavController navController;
    private VerifiedAccountViewModel viewModel;
    private DrawerHeaderManager drawerHeaderManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(this);
        super.onCreate(savedInstanceState);
        binding = ActivityVerifiedAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setNavController();
        setDrawerHeader();
        setViewModel();
        setBlurView();
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
     * Sets up the ViewModel for the fragment.
     */
    private void setViewModel() {
        viewModel = new ViewModelProvider(this).get(VerifiedAccountViewModel.class);
        viewModel.getUserLiveData().observe(this, this::handleUser);
        viewModel.getLoggedOutLiveData().observe(this, this::handleLoggedOutUser);
    }

    /**
     * Sets up the Navigation Drawer header.
     */
    private void setDrawerHeader() {
        drawerHeaderManager = new DrawerHeaderManager(this);
        binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        binding.navigationView.addHeaderView(drawerHeaderManager.getHeaderBinding());
        binding.navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.user_watchlist) {
                openUserWatchlistActivity();
                return true;
            } else if (id == R.id.user_reviews) {
                openUserReviewsActivity();
                return true;
            } else if (id == R.id.settings) {
                openSettingsActivity();
                return true;
            } else if (id == R.id.signout) {
                viewModel.logOut();
                return true;
            }
            return false;
        });
    }

    /**
     * Sets up the Blur View
     */
    private void setBlurView() {
        float radius = 16f;

        View decorView = getWindow().getDecorView();
        ViewGroup rootView = decorView.findViewById(android.R.id.content);

        Drawable windowBackground = decorView.getBackground();

        binding.blurView.setupWith(rootView)
                .setFrameClearDrawable(windowBackground)
                .setBlurRadius(radius);
        binding.blurView.setBlurEnabled(false);
    }

    private void handleUser(User user){
        if (user != null) {
            drawerHeaderManager.setDrawerUserUi(user);
        }
    }

    private void handleLoggedOutUser(Boolean loggedOut) {
        if (loggedOut != null && loggedOut) {
            openAuthActivity();
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

    public void openSettingsActivity() {
        if (navController != null) {
            navController.navigate(R.id.action_global_settingsActivity);
        }
    }

    public void openUserWatchlistActivity() {
        if (navController != null) {
            navController.navigate(R.id.action_global_userWatchlistActivity);
        }
    }

    public void openUserReviewsActivity() {
        if (navController != null) {
            navController.navigate(R.id.action_global_userReviewsActivity);
        }
    }

    public void openResultSearchActivity(String query) {
        Bundle bundle = new Bundle();
        bundle.putString(SearchResultActivity.QUERY_TAG, query);
        navController.navigate(R.id.action_global_searchResultActivity, bundle);
    }

    public NavController getNavController() {
        return navController;
    }

    /**
     * Open Navigation Drawer.
     */
    public void openDrawer() {
        binding.drawerLayout.open();
    }

    /**
     * Enable blur effect.
     */
    public void enableBlur(boolean enable) {
        binding.blurView.setBlurEnabled(enable);
    }

}