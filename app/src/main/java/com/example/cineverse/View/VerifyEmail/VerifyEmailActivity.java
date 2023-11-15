package com.example.cineverse.View.VerifyEmail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.example.cineverse.Handler.UI.VisibilityHandler;
import com.example.cineverse.R;
import com.example.cineverse.View.Auth.AuthActivity;
import com.example.cineverse.View.Home.HomeActivity;
import com.example.cineverse.View.NetworkError.NetworkErrorActivity;
import com.example.cineverse.ViewModel.AbstractAuthViewModel;
import com.example.cineverse.ViewModel.VerifyEmail.VerifyEmailViewModel;
import com.example.cineverse.databinding.ActivityVerifyEmailBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseUser;

/**
 * The VerifyEmailActivity class represents the activity for email verification.
 * It allows users to verify their email address, resent verification emails, and navigate
 * to the home screen upon successful verification.
 */
public class VerifyEmailActivity extends AppCompatActivity {

    private static final int WAIT_COUNT_DOWN = 60;

    private final VerifyEmailListener verifyEmailListener = new VerifyEmailListener();
    private final Handler handler = new Handler();

    private ActivityVerifyEmailBinding binding;
    private VerifyEmailViewModel viewModel;
    private boolean isCountdownRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVerifyEmailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setupViewModel();
        setListeners();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isCountdownRunning) {
            binding.resentEmailButton.setEnabled(false);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }

    /**
     * Sets up the ViewModel for the fragment.
     */
    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(VerifyEmailViewModel.class);
        viewModel.getUserLiveData().observe(this, this::handleUser);
        viewModel.getLoggedOutLiveData().observe(this, this::handleLoggedOutUser);
        viewModel.getEmailSentLiveData().observe(this, this::handleEmailSent);
        viewModel.getEmailVerifiedLiveData().observe(this, this::handleEmailVerified);
        viewModel.getNetworkErrorLiveData().observe(this, this::handleNetworkError);
    }

    /**
     * Sets listeners for UI elements.
     */
    private void setListeners() {
        binding.materialToolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.signout) {
                viewModel.logOut();
                VisibilityHandler.setVisibleView(binding.progressIndicator.getRoot());
                return true;
            }
            return false;
        });
        binding.resentEmailButton.setOnClickListener(view -> handleSendEmail());
        binding.verifiedButton.setOnClickListener(verifyEmailListener);
    }

    /**
     * Handles the user's state after email verification.
     *
     * @param firebaseUser The FirebaseUser object representing the current user.
     */
    private void handleUser(FirebaseUser firebaseUser) {
        if (firebaseUser != null) {
            setEmailText(firebaseUser);
            handleSendEmail();
        } else {
            viewModel.logOut();
        }
    }

    /**
     * Handles the state after the user is logged out.
     *
     * @param loggedOut A boolean indicating whether the user has been logged out.
     */
    private void handleLoggedOutUser(Boolean loggedOut) {
        if (loggedOut) {
            openAuthActivity();
        }
        VisibilityHandler.setGoneView(binding.progressIndicator.getRoot());
    }

    /**
     * Handles the state after the verification email is sent.
     *
     * @param isSent A boolean indicating whether the email is sent successfully.
     */
    private void handleEmailSent(Boolean isSent) {
        viewModel.clearEmailSentLiveData();
        if (isSent == null) {
            unexpectedError();
            binding.resentEmailButton.setEnabled(true);
        } else if (isSent) {
            Snackbar.make(binding.getRoot(),
                    R.string.email_sent, Snackbar.LENGTH_SHORT).show();
            startCountdown();
        } else {
            Snackbar.make(binding.getRoot(),
                    R.string.email_not_sent_wait, Snackbar.LENGTH_SHORT).show();
            binding.resentEmailButton.setEnabled(true);
        }
        VisibilityHandler.setGoneView(binding.progressIndicator.getRoot());
    }

    /**
     * Handles the state after the email is verified.
     *
     * @param isVerified A boolean indicating whether the email is verified.
     */
    private void handleEmailVerified(Boolean isVerified) {
        viewModel.clearEmailVerifiedLiveData();
        if (isVerified == null) {
            unexpectedError();
        } else if (isVerified) {
            openHomeActivity();
        } else {
            Snackbar.make(binding.getRoot(),
                            R.string.email_not_verified, Snackbar.LENGTH_SHORT)
                    .setAction(R.string.retry, verifyEmailListener)
                    .show();
        }
        VisibilityHandler.setGoneView(binding.progressIndicator.getRoot());
    }

    /**
     * Handles network errors.
     *
     * @param bool A boolean indicating whether a network error has occurred.
     */
    private void handleNetworkError(Boolean bool) {
        if (bool) {
            openNetworkErrorActivity(viewModel);
        }
        binding.resentEmailButton.setEnabled(true);
        VisibilityHandler.setGoneView(binding.progressIndicator.getRoot());
    }

    /**
     * Sets the email text with formatting to show in the UI.
     *
     * @param firebaseUser The FirebaseUser object representing the current user.
     */
    private void setEmailText(FirebaseUser firebaseUser) {
        String email = firebaseUser.getEmail();
        String formattedString = getString(R.string.we_have_sent_email, "<b>" + email + "</b>");
        CharSequence styledText = HtmlCompat.fromHtml(formattedString, HtmlCompat.FROM_HTML_MODE_LEGACY);
        binding.sentMailTextView.setText(styledText);
    }

    /**
     * Handles the action of sending the verification email.
     */
    private void handleSendEmail() {
        viewModel.sendEmailVerification();
        VisibilityHandler.setVisibleView(binding.progressIndicator.getRoot());
        binding.resentEmailButton.setEnabled(false);
    }

    /**
     * Handles the action of verifying the email after the user clicks the verify button.
     */
    private void handleVerifyEmail() {
        viewModel.reloadUser();
        VisibilityHandler.setVisibleView(binding.progressIndicator.getRoot());
    }

    /**
     * Starts the countdown for resent email button enabling.
     */
    private void startCountdown() {
        final int[] countDown = {WAIT_COUNT_DOWN};
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                isCountdownRunning = true;
                if (countDown[0] > 0) {
                    String buttonText = getResources().getString(
                            countDown[0] == 1 ? R.string.wait_second : R.string.wait_seconds,
                            String.valueOf(countDown[0])
                    );
                    binding.resentEmailButton.setText(buttonText);
                    countDown[0]--;
                    handler.postDelayed(this, 1000);
                } else {
                    isCountdownRunning = false;
                    binding.resentEmailButton.setEnabled(true);
                    binding.resentEmailButton.setText(R.string.resend_email);
                }
            }
        }, 1000);
    }

    /**
     * Shows an unexpected error message to the user.
     */
    private void unexpectedError() {
        Snackbar.make(binding.getRoot(),
                R.string.unexpected_error, Snackbar.LENGTH_SHORT).show();
    }

    /**
     * Opens the authentication activity (MainActivity) and closes all previous activities in the stack.
     */
    public void openAuthActivity() {
        Intent intent = new Intent(this, AuthActivity.class);
        // Close all previews activity
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    /**
     * Opens the home activity (HomeActivity) and closes all previous activities in the stack.
     */
    public void openHomeActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
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

    /**
     * Inner class representing the click listener for the verify email button.
     */
    public class VerifyEmailListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            handleVerifyEmail();
        }
    }

}