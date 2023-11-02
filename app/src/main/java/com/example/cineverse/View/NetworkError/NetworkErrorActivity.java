package com.example.cineverse.View.NetworkError;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cineverse.databinding.ActivityNetworkErrorBinding;

/**
 * The NetworkErrorActivity class represents the activity shown to the user when there is a network error.
 */
public class NetworkErrorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityNetworkErrorBinding binding = ActivityNetworkErrorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.materialToolbar.setNavigationOnClickListener(view -> finish());
        binding.tryAgainButton.setOnClickListener(view -> finish());
    }

}