package com.example.cineverse.View.NetworkError;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.cineverse.Handler.Callback.BackPressedHandler;
import com.example.cineverse.R;
import com.example.cineverse.databinding.ActivityNetworkErrorBinding;

/**
 * The NetworkErrorActivity class represents the activity shown to the user when there is a network error.
 */
public class NetworkErrorActivity extends AppCompatActivity {

    private ActivityNetworkErrorBinding binding;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNetworkErrorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setActionBar();
        setNavController();
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

}