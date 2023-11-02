package com.example.cineverse.View.Auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cineverse.Repository.AuthRepository;
import com.example.cineverse.View.Home.LoggedActivity;
import com.example.cineverse.View.NetworkError.NetworkErrorActivity;
import com.example.cineverse.ViewModel.AbstractAuthViewModel;
import com.example.cineverse.databinding.ActivityMainBinding;

/**
 * The MainActivity class serves as the entry point of the application. It checks if a user is
 * already logged in. If so, it navigates the user to the home screen (LoggedActivity). If not, it
 * displays the main authentication screen. This activity also handles network error scenarios by
 * redirecting users to the NetworkErrorActivity.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().getDecorView()
                .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        alreadyLogged();
    }

    /**
     * Checks if a user is already logged in. If so, navigates the user to the home screen
     * (LoggedActivity).
     */
    public void alreadyLogged() {
        if (AuthRepository.getCurrentUser() != null) {
            openHomeActivity();
        }
    }

    /**
     * Opens the home screen of the application (LoggedActivity). Closes all previous activities
     * to maintain a clear navigation flow.
     */
    public void openHomeActivity() {
        Intent intent = new Intent(this, LoggedActivity.class);
        // Close all previews activity
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    /**
     * Opens the network error activity (NetworkErrorActivity) and clears network error LiveData in the
     * passed AbstractAuthViewModel to avoid re-opening NetworkErrorActivity when a fragment that
     * contains network error LiveData is recreated.
     *
     * @param viewModel The AbstractAuthViewModel associated with the current authentication context.
     */
    public void openNetworkErrorActivity(AbstractAuthViewModel viewModel) {
        viewModel.clearNetworkErrorLiveData();
        Intent intent = new Intent(this, NetworkErrorActivity.class);
        startActivity(intent);
    }

}