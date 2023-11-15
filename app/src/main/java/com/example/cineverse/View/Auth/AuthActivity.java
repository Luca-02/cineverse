package com.example.cineverse.View.Auth;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.cineverse.R;
import com.example.cineverse.Repository.AbstractAuthRepository;
import com.example.cineverse.ViewModel.AbstractAuthViewModel;
import com.example.cineverse.databinding.ActivityAuthBinding;
import com.google.android.material.color.DynamicColors;

/**
 * The MainActivity class serves as the entry point of the application. It checks if a user is
 * already logged in. If so, it navigates the user to the home screen (LoggedActivity). If not, it
 * displays the main authentication screen. This activity also handles network error scenarios by
 * redirecting users to the NetworkErrorActivity.
 */
public class AuthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityAuthBinding binding = ActivityAuthBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    /**
     * Opens the LoggedActivity based on the email verification status. If the email is verified,
     * it navigates the user to the home screen (HomeActivity). If not, it navigates to the email
     * verification screen (VerifyEmailActivity).
     */
    public void openLoggedActivity() {
        NavController navController =
                Navigation.findNavController(this, R.id.fragmentContainerView);
        boolean isEmailVerified = AbstractAuthRepository.isEmailVerified();
        if (isEmailVerified) {
            navController.navigate(R.id.action_global_homeActivity);
        } else {
            navController.navigate(R.id.action_global_verifyEmailActivity);
        }
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
        NavController navController =
                Navigation.findNavController(this, R.id.fragmentContainerView);
        navController.navigate(R.id.action_global_networkErrorActivity);
    }

}