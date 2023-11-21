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

import com.example.cineverse.handler.ui.VisibilityHandler;
import com.example.cineverse.repository.auth.RegisterRepository;
import com.example.cineverse.view.auth.AuthActivity;
import com.example.cineverse.viewmodel.auth.RegisterViewModel;
import com.example.cineverse.databinding.FragmentRegisterBinding;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

/**
 * The RegisterFragment class represents the screen for user registration.
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        binding.emailEditText.addTextChangedListener(myTextWatcher);
        binding.passwordEditText.addTextChangedListener(myTextWatcher);
        binding.registerButton.setOnClickListener(view -> {
            String email = Objects.requireNonNull(binding.emailEditText.getText()).toString().trim();
            String password = Objects.requireNonNull(binding.passwordEditText.getText()).toString().trim();
            viewModel.register(email, password);
            VisibilityHandler.setVisibleView(binding.progressIndicator.getRoot());
        });
    }

    /**
     * Disables error message for email and password input fields.
     */
    private void disableErrorMessage() {
        binding.emailInputLayout.setErrorEnabled(false);
        binding.passwordInputLayout.setErrorEnabled(false);
    }

    /**
     * Handles the enabling/disabling state of the registration button based on the email and password input fields' content.
     */
    private void handleButton() {
        disableErrorMessage();
        String email = Objects.requireNonNull(binding.emailEditText.getText()).toString().trim();
        String password = Objects.requireNonNull(binding.passwordEditText.getText()).toString().trim();
        binding.registerButton.setEnabled(!email.isEmpty() && !password.isEmpty());
    }

    /**
     * Handles the user state after registration. Navigates the user to the home screen upon successful registration.
     * Hides the progress indicator after handling the user state.
     *
     * @param firebaseUser The FirebaseUser object representing the registered user.
     */
    private void handleUser(FirebaseUser firebaseUser) {
        if (firebaseUser != null) {
            ((AuthActivity) requireActivity()).openLoggedActivity();
        }
        VisibilityHandler.setGoneView(binding.progressIndicator.getRoot());
    }

    /**
     * Handles network error states. Navigates the user to the network error screen when there
     * is a network error. Resets the password input field and hides the progress indicator.
     *
     * @param bool True if there is a network error, false otherwise.
     */
    private void handleNetworkError(Boolean bool) {
        if (bool) {
            ((AuthActivity) requireActivity()).openNetworkErrorActivity(viewModel);
        }
        binding.passwordEditText.setText(null);
        VisibilityHandler.setGoneView(binding.progressIndicator.getRoot());
    }

    /**
     * Handles registration errors. Displays error messages for the email and password input fields based on the error type.
     * Hides the progress indicator after handling the error state.
     *
     * @param error The type of registration error.
     */
    private void handleError(RegisterRepository.Error error) {
        viewModel.clearErrorLiveData();
        binding.passwordEditText.setText(null);
        String errorString = getString(error.getError());
        switch (error) {
            case ERROR_INVALID_EMAIL_FORMAT:
            case ERROR_INVALID_EMAIL:
            case ERROR_ALREADY_EXISTS:
                binding.emailInputLayout.setError(errorString);
                break;
            case ERROR_WEAK_PASSWORD:
                binding.passwordInputLayout.setError(errorString);
                break;
            case ERROR_INVALID_CREDENTIAL:
                binding.emailInputLayout.setError(errorString);
                binding.passwordInputLayout.setError(errorString);
                break;
        }
        VisibilityHandler.setGoneView(binding.progressIndicator.getRoot());
    }

    /**
     * Custom TextWatcher class to handle text changes in the input fields.
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