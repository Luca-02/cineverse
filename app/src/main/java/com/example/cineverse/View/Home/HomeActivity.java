package com.example.cineverse.View.Home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.cineverse.View.Auth.MainActivity;
import com.example.cineverse.ViewModel.Home.HomeViewModel;
import com.example.cineverse.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;
    private HomeViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setupViewModel();
        setListeners();
    }

    public void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        viewModel.getUserLiveData().observe(this, firebaseUser -> {
            if (firebaseUser != null) {
                binding.userEmail.setText(String.format("Logged In User: %s", firebaseUser.getEmail()));
                binding.logoutButton.setEnabled(true);
            } else {
                binding.logoutButton.setEnabled(false);
            }
        });

        viewModel.getLoggedOutLiveData().observe(this, loggedOut -> {
            if (loggedOut) {
                Intent intent = new Intent(this, MainActivity.class);
                // Close all previews activity
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
    }

    public void setListeners() {
        binding.logoutButton.setOnClickListener(view -> viewModel.logOut());
    }

}