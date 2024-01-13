package com.example.cineverse.view.user_reviews;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.cineverse.R;
import com.example.cineverse.data.model.ui.BaseActivity;
import com.example.cineverse.databinding.ActivityUserReviewBinding;
import com.example.cineverse.view.network_error.NetworkErrorActivity;

public class UserReviewsActivity extends BaseActivity {

    private ActivityUserReviewBinding binding;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(this);
        super.onCreate(savedInstanceState);
        binding = ActivityUserReviewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setActionBar();
        setNavController();
        binding.materialToolbar.setNavigationOnClickListener(
                view -> getOnBackPressedDispatcher().onBackPressed());
    }

    /**
     * Called when the activity is about to be destroyed.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    /**
     * Sets the ActionBar for the activity and its style.
     */
    private void setActionBar() {
        setSupportActionBar(binding.materialToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.your_reviews2);
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
     * Opens the network error activity ({@link NetworkErrorActivity}).
     */
    public void openNetworkErrorActivity() {
        if (navController != null) {
            navController.navigate(R.id.action_global_networkErrorActivity);
        }
    }

    public NavController getNavController() {
        return navController;
    }

}