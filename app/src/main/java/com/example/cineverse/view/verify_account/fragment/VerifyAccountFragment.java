package com.example.cineverse.view.verify_account.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.text.HtmlCompat;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.cineverse.data.model.user.User;
import com.example.cineverse.R;
import com.example.cineverse.databinding.FragmentVerifyAccountBinding;
import com.example.cineverse.view.verify_account.VerifyAccountActivity;
import com.example.cineverse.viewmodel.logged.verify_account.VerifyAccountViewModel;
import com.google.android.material.snackbar.Snackbar;

/**
 * The {@link VerifyAccountFragment} class represents the fragment responsible for handling
 * email verification in the Cineverse application. It provides functionality to verify
 * user emails, handle UI interactions, and respond to various user states during the
 * verification process.
 */
public class VerifyAccountFragment extends Fragment {

    /**
     * Countdown time in seconds
     */
    private static final int WAIT_COUNT_DOWN = 60;

    private final VerifyEmailListener verifyEmailListener = new VerifyEmailListener();
    private final Handler handler = new Handler();

    private FragmentVerifyAccountBinding binding;
    private VerifyAccountViewModel viewModel;
    private boolean isCountdownRunning = false;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentVerifyAccountBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setActionBarMenu();
        setViewModel();
        setListeners();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isCountdownRunning) {
            binding.resentEmailButton.setEnabled(false);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }

    /**
     * Sets up the ActionBarMenu of the activity related to the fragment.
     */
    private void setActionBarMenu() {
        requireActivity().addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.verify_email_app_bar, menu);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.signout) {
                    viewModel.logOut();
                    binding.progressIndicator.getRoot().setVisibility(View.VISIBLE);
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * Sets up the ViewModel for the fragment.
     */
    private void setViewModel() {
        viewModel = new ViewModelProvider(this).get(VerifyAccountViewModel.class);
        viewModel.getUserLiveData().observe(getViewLifecycleOwner(), this::handleUser);
        viewModel.getLoggedOutLiveData().observe(getViewLifecycleOwner(), this::handleLoggedOutUser);
        viewModel.getEmailSentLiveData().observe(getViewLifecycleOwner(), this::handleEmailSent);
        viewModel.getEmailVerifiedLiveData().observe(getViewLifecycleOwner(), this::handleEmailVerified);
        viewModel.getNetworkErrorLiveData().observe(getViewLifecycleOwner(), this::handleNetworkError);
    }

    /**
     * Sets listeners for UI elements.
     */
    private void setListeners() {
        binding.resentEmailButton.setOnClickListener(view -> handleSendEmail());
        binding.verifiedButton.setOnClickListener(verifyEmailListener);
    }

    /**
     * Handles the user's state after email verification.
     *
     * @param user The {@link User} object representing the current user.
     */
    private void handleUser(User user) {
        if (user != null) {
            setEmailText(user);
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
            ((VerifyAccountActivity) requireActivity()).openAuthActivity();
        }
        binding.progressIndicator.getRoot().setVisibility(View.GONE);
    }

    /**
     * Handles the state after the verification email is sent.
     *
     * @param isSent A boolean indicating whether the email is sent successfully.
     */
    private void handleEmailSent(Boolean isSent) {
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
        binding.progressIndicator.getRoot().setVisibility(View.GONE);
    }

    /**
     * Handles the state after the email is verified.
     *
     * @param isVerified A boolean indicating whether the email is verified.
     */
    private void handleEmailVerified(Boolean isVerified) {
        if (isVerified == null) {
            unexpectedError();
        } else if (isVerified) {
            ((VerifyAccountActivity) requireActivity()).openEmailVerifiedActivity();
        } else {
            Snackbar.make(binding.getRoot(),
                            R.string.email_not_verified, Snackbar.LENGTH_SHORT)
                    .setAction(R.string.retry, verifyEmailListener)
                    .show();
        }
        binding.progressIndicator.getRoot().setVisibility(View.GONE);
    }

    /**
     * Handles network errors.
     *
     * @param bool A boolean indicating whether a network error has occurred.
     */
    private void handleNetworkError(Boolean bool) {
        if (bool) {
            ((VerifyAccountActivity) requireActivity()).openNetworkErrorActivity();
        }
        binding.resentEmailButton.setEnabled(true);
        binding.progressIndicator.getRoot().setVisibility(View.GONE);
    }

    /**
     * Sets the email text with formatting to show in the UI.
     *
     * @param user The {@link User} object representing the current user.
     */
    private void setEmailText(User user) {
        String email = user.getEmail();
        String formattedString = getString(R.string.we_have_sent_email, "<b>" + email + "</b>");
        CharSequence styledText = HtmlCompat.fromHtml(formattedString, HtmlCompat.FROM_HTML_MODE_LEGACY);
        binding.sentMailTextView.setText(styledText);
    }

    /**
     * Handles the action of sending the verification email.
     */
    private void handleSendEmail() {
        viewModel.sendEmailVerification();
        binding.progressIndicator.getRoot().setVisibility(View.VISIBLE);
        binding.resentEmailButton.setEnabled(false);
    }

    /**
     * Handles the action of verifying the email after the user clicks the verify button.
     */
    private void handleVerifyEmail() {
        viewModel.reloadUser();
        binding.progressIndicator.getRoot().setVisibility(View.VISIBLE);
    }

    /**
     * Starts the countdown for resent email button enabling.
     */
    private void startCountdown() {
        final int[] countDown = {WAIT_COUNT_DOWN};
        handler.postDelayed(() -> updateCountdown(countDown), 1000);
    }

    /**
     * Update the countdown for resent email button enabling.
     */
    private void updateCountdown(final int[] countDown) {
        isCountdownRunning = true;
        if (countDown[0] > 0) {
            String buttonText = getResources().getString(
                    countDown[0] == 1 ? R.string.wait_second : R.string.wait_seconds,
                    String.valueOf(countDown[0])
            );
            binding.resentEmailButton.setText(buttonText);
            countDown[0]--;
            handler.postDelayed(() -> updateCountdown(countDown), 1000);
        } else {
            isCountdownRunning = false;
            binding.resentEmailButton.setEnabled(true);
            binding.resentEmailButton.setText(R.string.resend_email);
        }
    }

    /**
     * Shows an unexpected error message to the user.
     */
    private void unexpectedError() {
        Snackbar.make(binding.getRoot(),
                R.string.unexpected_error, Snackbar.LENGTH_SHORT).show();
    }

    /**
     * {@link VerifyEmailListener} is an inner class representing the click listener for the verify email button.
     */
    public class VerifyEmailListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            handleVerifyEmail();
        }
    }

}