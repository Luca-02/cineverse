package com.example.cineverse.View.Home.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.cineverse.Handler.UI.VisibilityHandler;
import com.example.cineverse.R;
import com.example.cineverse.View.Home.LoggedActivity;
import com.example.cineverse.ViewModel.Home.Logged.VerifyEmailViewModel;
import com.example.cineverse.databinding.FragmentVerifyEmailBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseUser;

public class VerifyEmailFragment extends Fragment {

    private static final int WAIT_COUNT_DOWN = 60;

    private final VerifyEmailListener verifyEmailListener = new VerifyEmailListener();

    private FragmentVerifyEmailBinding binding;
    private VerifyEmailViewModel viewModel;

    private final Handler mHandler = new Handler();;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentVerifyEmailBinding.inflate(inflater, container, false);
        setupViewModel();
        setListeners();
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        mHandler.removeCallbacksAndMessages(null);
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(VerifyEmailViewModel.class);

        viewModel.getUserLiveData().observe(getViewLifecycleOwner(), this::handleUser);
        viewModel.getLoggedOutLiveData().observe(getViewLifecycleOwner(), loggedOut -> {
            if (loggedOut) {
                ((LoggedActivity) requireActivity()).openAuthActivity();
            }
            VisibilityHandler.setGoneView(binding.progressIndicator.getRoot());
        });
        viewModel.getGetEmailSentLiveData().observe(getViewLifecycleOwner(), isSent -> {
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
        });
        viewModel.getEmailVerifiedLiveData().observe(getViewLifecycleOwner(), isVerified -> {
            if (isVerified == null) {
                unexpectedError();
            } else if (isVerified) {
                Navigation.findNavController(requireView())
                        .navigate(R.id.action_verifyEmailFragment_to_homeFragment);
            } else {
                Snackbar.make(binding.getRoot(),
                        R.string.email_not_verified, Snackbar.LENGTH_SHORT)
                        .setAction(R.string.retry, verifyEmailListener)
                        .show();
            }
            VisibilityHandler.setGoneView(binding.progressIndicator.getRoot());
        });
        viewModel.getNetworkErrorLiveData().observe(getViewLifecycleOwner(), this::handleNetworkError);
    }

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

    private void handleUser(FirebaseUser firebaseUser) {
        if (firebaseUser != null) {
            setEmailText(firebaseUser);
            handleSendEmail();
        } else {
            // TODO create a LoggedUserFailureException
        }
    }

    private void setEmailText(FirebaseUser firebaseUser) {
        String email = firebaseUser.getEmail();
        String formattedString = getString(R.string.we_have_sent_email, "<b>" + email + "</b>");
        CharSequence styledText = HtmlCompat.fromHtml(formattedString, HtmlCompat.FROM_HTML_MODE_LEGACY);
        binding.sentMailTextView.setText(styledText);
    }

    private void handleNetworkError(Boolean bool) {
        if (bool) {
            ((LoggedActivity) requireActivity()).openNetworkErrorActivity();
        }
        binding.resentEmailButton.setEnabled(true);
        VisibilityHandler.setGoneView(binding.progressIndicator.getRoot());
    }

    private void handleSendEmail() {
        viewModel.sendEmailVerification();
        VisibilityHandler.setVisibleView(binding.progressIndicator.getRoot());
        binding.resentEmailButton.setEnabled(false);
    }

    private void handleVerifyEmail() {
        viewModel.reloadUser();
        VisibilityHandler.setVisibleView(binding.progressIndicator.getRoot());
    }

    private void startCountdown() {
        final int[] countDown = {WAIT_COUNT_DOWN};
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (countDown[0] > 0) {
                    String buttonText = getResources().getString(
                            countDown[0] == 1 ? R.string.wait_second : R.string.wait_seconds,
                            String.valueOf(countDown[0])
                    );
                    binding.resentEmailButton.setText(buttonText);
                    countDown[0]--;
                    mHandler.postDelayed(this, 1000);
                } else {
                    binding.resentEmailButton.setEnabled(true);
                    binding.resentEmailButton.setText(R.string.resend_email);
                }
            }
        }, 1000);
    }

    private void unexpectedError() {
        Snackbar.make(binding.getRoot(),
                R.string.unexpected_error, Snackbar.LENGTH_SHORT).show();
    }

    public class VerifyEmailListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            handleVerifyEmail();
        }
    }

}