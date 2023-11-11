package com.example.cineverse.View.Home;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cineverse.View.Auth.MainActivity;
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
     * Opens the authentication activity (MainActivity) and closes all previous activities in the stack.
     */
    public void openAuthActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        // Close all previews activity
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

}