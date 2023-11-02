package com.example.cineverse.View.Home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.cineverse.R;
import com.example.cineverse.Repository.AuthRepository;
import com.example.cineverse.View.Auth.MainActivity;
import com.example.cineverse.View.NetworkError.NetworkErrorActivity;
import com.example.cineverse.databinding.ActivityLoggedBinding;

/**
 * The LoggedActivity class represents the main activity for logged-in users.
 * It manages the navigation flow for authenticated users, directing them to appropriate fragments based on their email verification status.
 */
public class LoggedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityLoggedBinding binding = ActivityLoggedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setupNavGraph();
    }

    /**
     * Sets up the navigation graph based on the user's email verification status.
     * Redirects the user to the appropriate start destination fragment.
     */
    private void setupNavGraph() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragmentContainerView);

        if (navHostFragment != null) {
            NavController navController = navHostFragment.getNavController();
            NavGraph navGraph = navController.getNavInflater().inflate(R.navigation.logged_nav_graph);

            boolean isEmailVerified = AuthRepository.isEmailVerified();
            int startDestination = isEmailVerified ? R.id.homeFragment : R.id.verifyEmailFragment;
            navGraph.setStartDestination(startDestination);

            navController.setGraph(navGraph);
        }
    }

    /**
     * Opens the authentication activity (MainActivity) and closes all previous activities in the stack.
     */
    public void openAuthActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        // Close all previews activity
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    /**
     * Opens the network error activity, allowing the user to handle network issues separately.
     */
    public void openNetworkErrorActivity() {
        Intent intent = new Intent(this, NetworkErrorActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}