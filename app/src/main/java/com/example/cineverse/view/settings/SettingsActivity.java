package com.example.cineverse.view.settings;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;

import com.example.cineverse.R;
import com.example.cineverse.data.model.ui.BaseActivity;
import com.example.cineverse.databinding.ActivitySettingsBinding;
import com.example.cineverse.view.auth.AuthActivity;
import com.example.cineverse.view.network_error.NetworkErrorActivity;

/**
 * The {@link SettingsActivity} : manages the other Settings Fragments
 * and listen to Theme Changes
*/
public class SettingsActivity extends BaseActivity {

    private ActivitySettingsBinding binding;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(this);
        super.onCreate(savedInstanceState);
        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setActionBar();
        setNavController();
        binding.toolbarSettings.setNavigationOnClickListener(
                view -> getOnBackPressedDispatcher().onBackPressed());
    }

    private void setActionBar() {
        setSupportActionBar(binding.toolbarSettings);
        setActionBarStyle();
    }

    public void setActionBarStyle(){
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.settings);
        }
    }

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

    /**
     * Opens the authentication activity ({@link AuthActivity}) and closes all previous activities in the stack.
     */
    public void openAuthActivity() {
        if (navController != null) {
            navController.navigate(R.id.action_global_authActivity);
            finish();
        }
    }

}