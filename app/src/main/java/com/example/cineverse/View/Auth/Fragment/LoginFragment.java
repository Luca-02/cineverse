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

public class LoginFragment extends Fragment {

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

    private void setViewModel() {
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        viewModel.getUserLiveData().observe(getViewLifecycleOwner(), this::handleUser);
        viewModel.getErrorLiveData().observe(getViewLifecycleOwner(), this::handleError);
    }

    private void setListeners() {
        binding.materialToolbar.setNavigationOnClickListener(view ->
                Navigation.findNavController(requireView()).popBackStack());

        MyTextWatcher myTextWatcher = new MyTextWatcher();
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

    private void disableErrorMessage() {
        binding.emailInputLayout.setErrorEnabled(false);
        binding.passwordInputLayout.setErrorEnabled(false);
    }

    private void handleButton() {
        disableErrorMessage();
        String email = Objects.requireNonNull(binding.emailEditText.getText()).toString().trim();
        String password = Objects.requireNonNull(binding.passwordEditText.getText()).toString().trim();
        binding.loginButton.setEnabled(!email.isEmpty() && !password.isEmpty());
    }

    private void handleUser(FirebaseUser firebaseUser) {
        if (firebaseUser != null) {
            ((MainActivity) requireActivity()).openHomeActivity();
        }
        VisibilityHandler.setGoneView(binding.progressIndicator.getRoot());
    }

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