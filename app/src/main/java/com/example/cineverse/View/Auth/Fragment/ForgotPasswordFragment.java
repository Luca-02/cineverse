package com.example.cineverse.View.Auth.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cineverse.Handler.UI.VisibilityHandler;
import com.example.cineverse.R;
import com.example.cineverse.Repository.AbstractAuthRepository;
import com.example.cineverse.View.Auth.AuthActivity;
import com.example.cineverse.ViewModel.Auth.ForgotPasswordViewModel;
import com.example.cineverse.databinding.FragmentForgotPasswordBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

/**
 * The ForgotPasswordFragment class represents the screen for users to reset their password.
 * Users can enter their email to receive a password reset email.
 * This fragment handles user input validation, button functionality, and error handling for the
 * password reset process.
 */
public class ForgotPasswordFragment extends Fragment {

    private final MyTextWatcher myTextWatcher = new MyTextWatcher();

    private FragmentForgotPasswordBinding binding;
    private ForgotPasswordViewModel viewModel;

    public ForgotPasswordFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentForgotPasswordBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setViewModel();
        setListeners();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    /**
     * Sets up the ViewModel for the fragment.
     */
    private void setViewModel() {
        viewModel = new ViewModelProvider(this).get(ForgotPasswordViewModel.class);
        viewModel.getNetworkErrorLiveData().observe(getViewLifecycleOwner(), this::handleNetworkError);
        viewModel.getErrorLiveData().observe(getViewLifecycleOwner(), this::handleError);
    }

    /**
     * Sets up the UI button listeners for navigation, text input, and password reset functionality.
     */
    private void setListeners() {
        binding.emailEditText.addTextChangedListener(myTextWatcher);
        binding.resetPasswordButton.setOnClickListener(view -> {
            String email = Objects.requireNonNull(
                    binding.emailEditText.getText()).toString().trim();
            viewModel.forgotPassword(email);
            VisibilityHandler.setVisibleView(binding.progressIndicator.getRoot());
        });
    }

    /**
     * Disables error message for email input field.
     */
    private void disableErrorMessage() {
        binding.emailInputLayout.setErrorEnabled(false);
    }

    /**
     * Handles the enabling/disabling state of the password reset button based on the email input field's content.
     */
    private void handleButton() {
        disableErrorMessage();
        String email = Objects.requireNonNull(
                binding.emailEditText.getText()).toString().trim();
        binding.resetPasswordButton.setEnabled(!email.isEmpty());
    }

    /**
     * Handles network error states. Navigates the user to the network error screen when there
     * is a network error. Resets the email input field and hides the progress indicator.
     *
     * @param bool True if there is a network error, false otherwise.
     */
    private void handleNetworkError(Boolean bool) {
        if (bool) {
            ((AuthActivity) requireActivity()).openNetworkErrorActivity(viewModel);
        }
        VisibilityHandler.setGoneView(binding.progressIndicator.getRoot());
    }

    /**
     * Handles the success state after sending a password reset email. Displays a success message,
     * navigates the user to the login screen, and hides the progress indicator.
     */
    private void handleSuccess() {
        Snackbar.make(binding.getRoot(),
                R.string.email_sent, Snackbar.LENGTH_LONG).show();
        Navigation.findNavController(requireView()).popBackStack();
    }

    /**
     * Handles password reset errors. If the operation is successful, calls handleSuccess().
     * If there is an error, displays an error message for the email input field and hides the progress indicator.
     *
     * @param result The type of password reset operation result.
     */
    private void handleError(AbstractAuthRepository.Error result) {
        viewModel.clearErrorLiveData();
        if (result.isSuccess()) {
            handleSuccess();
        } else {
            binding.emailInputLayout.setError(getString(result.getError()));
        }
        VisibilityHandler.setGoneView(binding.progressIndicator.getRoot());
    }

    /**
     * Custom TextWatcher class to handle text changes in the input field.
     */
    public class MyTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            handleButton();
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    }

}