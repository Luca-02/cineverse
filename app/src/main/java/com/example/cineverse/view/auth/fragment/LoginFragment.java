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
import androidx.navigation.Navigation;

import com.example.cineverse.data.model.user.User;
import com.example.cineverse.R;
import com.example.cineverse.repository.auth.service.LoginRepository;
import com.example.cineverse.view.auth.AuthActivity;
import com.example.cineverse.viewmodel.auth.service.LoginViewModel;
import com.example.cineverse.databinding.FragmentLoginBinding;

import java.util.Objects;
import java.util.Properties;

/**
 * The {@link LoginFragment} class represents the login screen of the application.
 * Users can enter their email and password to log into their account.
 * This fragment handles user input validation, login button functionality, and error handling.
 */
public class LoginFragment extends Fragment {

    private final MyTextWatcher myTextWatcher = new MyTextWatcher();

    private FragmentLoginBinding binding;
    private LoginViewModel viewModel;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
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
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        viewModel.getUserLiveData().observe(getViewLifecycleOwner(), this::handleUser);
        viewModel.getNetworkErrorLiveData().observe(getViewLifecycleOwner(), this::handleNetworkError);
        viewModel.getErrorLiveData().observe(getViewLifecycleOwner(), this::handleError);
    }

    /**
     * Sets up the UI button listeners for navigation, text input, and login functionality.
     */
    private void setListeners() {
        binding.accountEditText.addTextChangedListener(myTextWatcher);
        binding.passwordEditText.addTextChangedListener(myTextWatcher);
        binding.loginButton.setOnClickListener(view -> {
            String email = Objects.requireNonNull(binding.accountEditText.getText()).toString().trim();
            String password = Objects.requireNonNull(binding.passwordEditText.getText()).toString().trim();
            viewModel.login(email, password);
            binding.progressIndicator.getRoot().setVisibility(View.VISIBLE);
        });
        binding.forgotPasswordText.setOnClickListener(view ->
                Navigation.findNavController(requireView())
                        .navigate(R.id.action_loginFragment_to_forgotPasswordFragment));
    }

    /**
     * Disables error messages for email and password input fields.
     */
    private void disableErrorMessage() {
        binding.accountInputLayout.setErrorEnabled(false);
        binding.passwordInputLayout.setErrorEnabled(false);
    }

    /**
     * Handles the enabling/disabling state of the login button based on the input fields' content.
     */
    private void handleButton() {
        disableErrorMessage();
        String email = Objects.requireNonNull(binding.accountEditText.getText()).toString().trim();
        String password = Objects.requireNonNull(binding.passwordEditText.getText()).toString().trim();
        binding.loginButton.setEnabled(!email.isEmpty() && !password.isEmpty());
    }

    /**
     * Handles the authentication state changes for the user. Navigates the user to the home
     * screen when authenticated.
     *
     * @param user The authenticated {@link User} object. If {@code null}, user is not authenticated.
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
     * is a network error. Resets the password input field.
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
                binding.accountInputLayout.setError(errorString);
                break;
            case ERROR_WRONG_PASSWORD:
                binding.passwordInputLayout.setError(errorString);
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
    }

}