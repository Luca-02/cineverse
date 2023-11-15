package com.example.cineverse.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.cineverse.Repository.AbstractAuthRepository;
import com.example.cineverse.View.Auth.AuthActivity;
import com.example.cineverse.View.Home.HomeActivity;
import com.example.cineverse.View.VerifyEmail.VerifyEmailActivity;

/**
 * The MainActivity class serves as the entry point of the application. It checks the user's
 * authentication status and redirects them to the appropriate activity, either (AuthActivity)
 * for authentication or (HomeActivity)/(VerifyEmailActivity) based on email verification status.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeUI();
    }

    /**
     * Initializes the UI by determining whether the user is logged in or not.
     * Redirects the user to the appropriate activity and finishes this activity.
     */
    private void initializeUI() {
        Intent intent;
        if (AbstractAuthRepository.getCurrentUser() != null) {
            intent = requireLoggedActivity();
        } else {
            intent = new Intent(this, AuthActivity.class);
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    /**
     * Determines the appropriate activity based on the user's email verification status.
     *
     * @return Intent for the required activity (HomeActivity or VerifyEmailActivity).
     */
    private Intent requireLoggedActivity() {
        boolean isEmailVerified = AbstractAuthRepository.isEmailVerified();
        if (isEmailVerified) {
            return new Intent(this, HomeActivity.class);
        } else {
            return new Intent(this, VerifyEmailActivity.class);
        }
    }

}