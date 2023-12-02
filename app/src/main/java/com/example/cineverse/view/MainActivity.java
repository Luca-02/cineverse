package com.example.cineverse.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.cineverse.data.model.user.User;
import com.example.cineverse.view.auth.AuthActivity;
import com.example.cineverse.view.verified_account.VerifiedAccountActivity;
import com.example.cineverse.view.verify_account.VerifyAccountActivity;
import com.example.cineverse.viewmodel.ConcreteUserViewModel;

/**
 * The {@link MainActivity} class serves as the entry point of the application. It checks the user's
 * authentication status and redirects them to the appropriate activity, either ({@link AuthActivity})
 * for authentication or ({@link VerifyAccountActivity} or {@link VerifiedAccountActivity}) based on email verification status.
 */
public class MainActivity extends AppCompatActivity {

    private ConcreteUserViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setViewModel();
        initializeUI();
    }

    /**
     * Sets up the ViewModel for the fragment.
     */
    private void setViewModel() {
        viewModel = new ViewModelProvider(this).get(ConcreteUserViewModel.class);
    }

    /**
     * Initializes the UI by determining whether the user is logged in or not.
     * Redirects the user to the appropriate activity and finishes this activity.
     */
    private void initializeUI() {
        User currentUser = viewModel.getCurrentUser();
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
     * @return {@link Intent} for the required activity ({@link VerifyAccountActivity} or {@link VerifiedAccountActivity}).
     */
    private Intent requireLoggedActivity() {
        boolean isEmailVerified = viewModel.isEmailVerified();
        if (isEmailVerified) {
            return new Intent(this, VerifiedAccountActivity.class);
        } else {
            return new Intent(this, VerifyAccountActivity.class);
        }
    }

}