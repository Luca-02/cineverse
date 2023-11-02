package com.example.cineverse.View.Auth.Fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.cineverse.Handler.UI.VisibilityHandler;
import com.example.cineverse.R;
import com.example.cineverse.Repository.Auth.LoginRepository;
import com.example.cineverse.View.Auth.MainActivity;
import com.example.cineverse.ViewModel.Auth.LoginViewModel;
import com.example.cineverse.databinding.FragmentLoginBinding;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

/**
 * The LoginFragment class represents the login screen of the application.
 * Users can enter their email and password to log into their account.
 * This fragment handles user input validation, login button functionality, and error handling.
 */
public class LoginFragment extends Fragment {

    private final MyTextWatcher myTextWatcher = new MyTextWatcher();

    private FragmentLoginBinding binding;
    private LoginViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        setViewModel();
        setListeners();
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    /**
     * Initializes the LoginViewModel for this fragment.
     */
    private void setViewModel() {
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        viewModel.getUserLiveData().observe(getViewLifecycleOwner(), this::handleUser);
        viewModel.getNetworkErrorLiveData().observe(getViewLifecycleOwner(), this::handleNetworkError);
        viewModel.getErrorLiveData().observe(getViewLifecycleOwner(), this::handleError);
    }

    /**
     * Sets up the UI button listeners for navigation, text input, and login functionality.
     */
    private void setListeners() {
        binding.materialToolbar.setNavigationOnClickListener(view ->
                Navigation.findNavController(requireView()).popBackStack());

        binding.emailEditText.addTextChangedListener(myTextWatcher);
        binding.passwordEditText.addTextChangedListener(myTextWatcher);

        binding.loginButton.setOnClickListener(view -> {
            String email = Objects.requireNonNull(binding.emailEditText.getText()).toString().trim();
            String password = Objects.requireNonNull(binding.passwordEditText.getText()).toString().trim();
            viewModel.login(email, password);
            VisibilityHandler.setVisibleView(binding.progressIndicator.getRoot());
        });

        binding.forgotPasswordText.setOnClickListener(view ->
                Navigation.findNavController(requireView())
                        .navigate(R.id.action_loginFragment_to_forgotPasswordFragment));
    }

    /**
     * Disables error messages for email and password input fields.
     */
    private void disableErrorMessage() {
        binding.emailInputLayout.setErrorEnabled(false);
        binding.passwordInputLayout.setErrorEnabled(false);
    }

    /**
     * Handles the enabling/disabling state of the login button based on the input fields' content.
     */
    private void handleButton() {
        disableErrorMessage();
        String email = Objects.requireNonNull(binding.emailEditText.getText()).toString().trim();
        String password = Objects.requireNonNull(binding.passwordEditText.getText()).toString().trim();
        binding.loginButton.setEnabled(!email.isEmpty() && !password.isEmpty());
    }

    /**
     * Handles the authentication state changes for the user. Navigates the user to the home
     * screen when authenticated.
     *
     * @param firebaseUser The authenticated FirebaseUser object. If null, user is not authenticated.
     */
    private void handleUser(FirebaseUser firebaseUser) {
        if (firebaseUser != null) {
            ((MainActivity) requireActivity()).openHomeActivity();
        }
        VisibilityHandler.setGoneView(binding.progressIndicator.getRoot());
    }

    /**
     * Handles network error states. Navigates the user to the network error screen when there
     * is a network error. Resets the password input field.
     *
     * @param bool True if there is a network error, false otherwise.
     */
    private void handleNetworkError(Boolean bool) {
        if (bool) {
            ((MainActivity) requireActivity()).openNetworkErrorActivity(viewModel);
        }
        binding.passwordEditText.setText(null);
        VisibilityHandler.setGoneView(binding.progressIndicator.getRoot());
    }

    /**
     * Handles authentication errors. Displays error messages for invalid email, password, or user not found.
     *
     * @param error The type of authentication error that occurred.
     */
    private void handleError(LoginRepository.Error error) {
        binding.passwordEditText.setText(null);
        String errorString = getString(error.getError());
        switch (error) {
            case ERROR_INVALID_EMAIL_FORMAT:
            case ERROR_NOT_FOUND_DISABLED:
                binding.emailInputLayout.setError(errorString);
                break;
            case ERROR_WRONG_PASSWORD:
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