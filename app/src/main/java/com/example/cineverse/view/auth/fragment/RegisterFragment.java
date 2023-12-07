package com.example.cineverse.view.auth.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.cineverse.data.model.user.User;
import com.example.cineverse.repository.auth.service.RegisterRepository;
import com.example.cineverse.view.auth.AuthActivity;
import com.example.cineverse.viewmodel.auth.service.RegisterViewModel;
import com.example.cineverse.databinding.FragmentRegisterBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

/**
 * The {@link RegisterFragment} class represents the screen for user registration.
 * Users can enter their email and password to create an account.
 * This fragment handles user input validation, button functionality, and error handling for the
 * registration process.
 */
public class RegisterFragment extends Fragment {

    private final MyTextWatcher myTextWatcher = new MyTextWatcher();

    private FragmentRegisterBinding binding;
    private RegisterViewModel viewModel;

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater, container, false);
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
        viewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
        viewModel.getUserLiveData().observe(getViewLifecycleOwner(), this::handleUser);
        viewModel.getNetworkErrorLiveData().observe(getViewLifecycleOwner(), this::handleNetworkError);
        viewModel.getErrorLiveData().observe(getViewLifecycleOwner(), this::handleError);
    }

    /**
     * Sets up the UI button listeners for navigation, text input, and registration functionality.
     */
    private void setListeners() {
        binding.usernameEditText.addTextChangedListener(myTextWatcher);
        binding.emailEditText.addTextChangedListener(myTextWatcher);
        binding.passwordEditText.addTextChangedListener(myTextWatcher);
        binding.registerButton.setOnClickListener(view -> {
            String username = Objects.requireNonNull(binding.usernameEditText.getText()).toString().trim();
            String email = Objects.requireNonNull(binding.emailEditText.getText()).toString().trim();
            String password = Objects.requireNonNull(binding.passwordEditText.getText()).toString().trim();
            viewModel.register(username, email, password);
            binding.progressIndicator.getRoot().setVisibility(View.VISIBLE);
        });
    }

    /**
     * Disables error message for username, email and password input fields.
     */
    private void disableErrorMessage() {
        binding.usernameInputLayout.setErrorEnabled(false);
        binding.emailInputLayout.setErrorEnabled(false);
        binding.passwordInputLayout.setErrorEnabled(false);
    }

    /**
     * Handles the user state after registration. Navigates the user to the home screen upon successful registration.
     * Hides the progress indicator after handling the user state.
     *
     * @param user The {@link User} object representing the registered user.
     */
    private void handleUser(User user) {
        if (user != null) {
            boolean isEmailVerified = viewModel.isEmailVerified();
            ((AuthActivity) requireActivity()).openLoggedActivity(isEmailVerified);
        }
        binding.progressIndicator.getRoot().setVisibility(View.GONE);
    }

    /**
     * Handles network error states. Navigates the user to the network error screen when there
     * is a network error. Resets the password input field and hides the progress indicator.
     *
     * @param bool {@code true} if there is a network error, {@code false} otherwise.
     */
    private void handleNetworkError(Boolean bool) {
        if (bool) {
            ((AuthActivity) requireActivity()).openNetworkErrorActivity();
        }
        binding.passwordEditText.setText(null);
        binding.progressIndicator.getRoot().setVisibility(View.GONE);
    }

    /**
     * Handles registration errors. Displays error messages for the username, email and password input
     * fields based on the error type.
     * Hides the progress indicator after handling the error state.
     *
     * @param error The type of registration error.
     */
    private void handleError(RegisterRepository.Error error) {
        binding.passwordEditText.setText(null);
        String errorString = getString(error.getError());
        switch (error) {
            case ERROR_INVALID_USERNAME_FORMAT:
            case ERROR_USERNAME_ALREADY_EXISTS:
                binding.usernameInputLayout.setError(errorString);
                break;
            case ERROR_INVALID_EMAIL_FORMAT:
            case ERROR_INVALID_EMAIL:
            case ERROR_EMAIL_ALREADY_EXISTS:
                binding.emailInputLayout.setError(errorString);
                break;
            case ERROR_WEAK_PASSWORD:
                binding.passwordInputLayout.setError(errorString);
                break;
            case ERROR_INVALID_CREDENTIAL:
                binding.emailInputLayout.setError(errorString);
                binding.passwordInputLayout.setError(errorString);
                break;
            case ERROR_AUTHENTICATION_FAILED:
                Snackbar.make(binding.getRoot(),
                        errorString, Snackbar.LENGTH_SHORT).show();
                break;
        }
        binding.progressIndicator.getRoot().setVisibility(View.GONE);
    }

    /**
     * Custom {@link TextWatcher} class to handle text changes in the input fields.
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

        /**
         * Handles the enabling/disabling state of the registration button based on the username, email
         * and password input fields' content.
         */
        private void handleButton() {
            disableErrorMessage();
            String username = Objects.requireNonNull(binding.usernameEditText.getText()).toString().trim();
            String email = Objects.requireNonNull(binding.emailEditText.getText()).toString().trim();
            String password = Objects.requireNonNull(binding.passwordEditText.getText()).toString().trim();
            binding.registerButton.setEnabled(!username.isEmpty() &&!email.isEmpty() && !password.isEmpty());
        }

    }

}