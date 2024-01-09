package com.example.cineverse.view.verified_account.fragment.search;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.cineverse.R;
import com.example.cineverse.databinding.ActivityAuthBinding;

public class searchResultActivity extends AppCompatActivity {
    private ActivityAuthBinding binding;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(this);
        super.onCreate(savedInstanceState);
        binding = ActivityAuthBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setActionBar();
        setNavController();
        handleOnDestinationChangedListener();
        getWindow().setNavigationBarColor(android.R.attr.colorBackground);
        binding.materialToolbar.setNavigationOnClickListener(
                view -> getOnBackPressedDispatcher().onBackPressed());
    }

    private void setActionBar() {
        setSupportActionBar(binding.materialToolbar);
        ActionBar actionBar =  getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(query);
        }
    }
    
    private void setNavController() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragmentContainerView);
        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();
        }
    }

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
}
