package com.example.cineverse.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cineverse.data.model.user.User;
import com.example.cineverse.repository.classes.AbstractAuthRepository;
import com.example.cineverse.view.auth.AuthActivity;
import com.example.cineverse.view.email_verified.EmailVerifiedActivity;
import com.example.cineverse.view.verify_email.VerifyEmailActivity;

/**
 * The MainActivity class serves as the entry point of the application. It checks the user's
 * authentication status and redirects them to the appropriate activity, either (AuthActivity)
 * for authentication or (VerifyEmailActivity or EmailVerifiedActivity) based on email verification status.
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
        User currentUser = AbstractAuthRepository.getCurrentUser(getBaseContext());
        Intent intent;
        if (currentUser != null) {
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
     * @return Intent for the required activity (VerifyEmailActivity or EmailVerifiedActivity).
     */
    private Intent requireLoggedActivity() {
        boolean isEmailVerified = AbstractAuthRepository.isEmailVerified();
        if (isEmailVerified) {
            return new Intent(this, EmailVerifiedActivity.class);
        } else {
            return new Intent(this, VerifyEmailActivity.class);
        }
    }

}