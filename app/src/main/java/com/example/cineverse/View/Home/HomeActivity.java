package com.example.cineverse.View.Home;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.cineverse.R;
import com.example.cineverse.View.Auth.AuthActivity;
import com.example.cineverse.databinding.ActivityHomeBinding;

/**
 * The HomeActivity class represents the main activity of the application after the user has logged in
 * and their email is verified. It provides the main interface for the user to interact with the app's
 * features.
 */
public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityHomeBinding binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    /**
     * Opens the authentication activity (MainActivity).
     */
    public void openAuthActivity() {
        NavController navController =
                Navigation.findNavController(this, R.id.fragmentContainerView);
        navController.navigate(R.id.action_global_authActivity);
        finish();
    }

}