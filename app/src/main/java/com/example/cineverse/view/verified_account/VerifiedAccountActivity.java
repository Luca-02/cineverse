package com.example.cineverse.view.verified_account;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.cineverse.R;
import com.example.cineverse.databinding.ActivityVerifiedAccountBinding;
import com.example.cineverse.view.auth.AuthActivity;
import com.example.cineverse.viewmodel.verified_account.VerifiedAccountViewModel;

/**
 * The {@link VerifiedAccountActivity} class represents the main activity of the application after the user has logged in
 * and their email is verified. It provides the main interface for the user to interact with the app's
 * features.
 */
public class VerifiedAccountActivity extends AppCompatActivity {

    private ActivityVerifiedAccountBinding binding;
    private NavController navController;
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
        VerifiedAccountViewModel viewModel = new ViewModelProvider(this)
                .get(VerifiedAccountViewModel.class);
        viewModel.getUserLiveData().observe(this, user -> {
            if (user != null) {
                drawerHeaderManager.setDrawerUserUi(user);
            } else {
                viewModel.logOut();
            }
        });
    }

    /**
     * Sets up the Navigation Drawer header.
     */
    private void setDrawerHeader() {
        drawerHeaderManager = new DrawerHeaderManager(this);
        binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        binding.navigationView.addHeaderView(drawerHeaderManager.getHeaderBinding());
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

    /**
     * Opens the authentication activity ({@link AuthActivity}).
     */
    public void openAuthActivity() {
        if (navController != null) {
            navController.navigate(R.id.action_global_authActivity);
            finish();
        }
    }

    public void openResultSearchActivity(String query) {
        navController.navigate(R.id.action_global_searchResultActivity);
        finish();
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