package com.example.cineverse.view.verified_account;

import android.content.SharedPreferences;
import static com.example.cineverse.view.details.ContentDetailsActivity.CONTENT_ID_TAG;
import static com.example.cineverse.view.details.ContentDetailsActivity.CONTENT_TYPE_STRING_TAG;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.cineverse.R;
import com.example.cineverse.data.model.content.AbstractContent;
import com.example.cineverse.data.model.content.section.Movie;
import com.example.cineverse.data.model.content.section.Tv;
import com.example.cineverse.databinding.ActivityVerifiedAccountBinding;
import com.example.cineverse.utils.NetworkUtils;
import com.example.cineverse.utils.mapper.ContentTypeMappingManager;
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
    private SharedPreferences sharedPreferences;
    private boolean isNightMode;
    private static final String THEME_PREFERENCES = "theme_prefs";
    private static final String THEME_NIGHT_MODE = "isNightMode";

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
        loadDataPreferences();
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

    /*
    ** Retrieve the saved theme mode from SharedPreferences
    *  and apply the appropriate theme based on the saved mode
     */
    private void loadDataPreferences(){
        sharedPreferences = getSharedPreferences(THEME_PREFERENCES, MODE_PRIVATE);
        isNightMode = sharedPreferences.getBoolean(THEME_NIGHT_MODE, false);

        if (isNightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
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

    public void openContentDetailsActivity(AbstractContent content) {
        if (navController != null) {
            if (!NetworkUtils.isNetworkAvailable(getApplicationContext())) {
                navController.navigate(R.id.action_global_networkErrorActivity);
            } else {
                if (content.getClass().isAssignableFrom(Movie.class) || content.getClass().isAssignableFrom(Tv.class)) {
                    Bundle bundle = new Bundle();
                    bundle.putString(CONTENT_TYPE_STRING_TAG, ContentTypeMappingManager.getContentType(content.getClass()));
                    bundle.putInt(CONTENT_ID_TAG, content.getId());
                    navController.navigate(R.id.action_global_contentDetailsActivity, bundle);
                }
            }
        }
    }

    public void openAccountSettingsActivity() {
        if (navController != null) {
            navController.navigate(R.id.action_global_accountSettingsActivity);
        }
    }

    public void openViewAllRecentToWatchActivity() {
        if (navController != null) {
            navController.navigate(R.id.action_global_accountRecentToWatch);
        }
    }

    public void openViewAllRecentReviewsActivity() {
        if (navController != null) {
            navController.navigate(R.id.action_global_accountRecentReviews);
        }
    }

    public void openResultSearchActivity(String query) {
        // ...
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