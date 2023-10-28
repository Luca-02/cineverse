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
import com.example.cineverse.Repository.Auth.RegisterRepository;
import com.example.cineverse.View.Auth.MainActivity;
import com.example.cineverse.ViewModel.Auth.RegisterViewModel;
import com.example.cineverse.databinding.FragmentRegisterBinding;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class RegisterFragment extends Fragment {

    private final MyTextWatcher myTextWatcher = new MyTextWatcher();

    private FragmentRegisterBinding binding;
    private RegisterViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater, container, false);
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
        viewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
        viewModel.getUserLiveData().observe(getViewLifecycleOwner(), this::handleUser);
        viewModel.getNetworkErrorLiveData().observe(getViewLifecycleOwner(), this::handleNetworkError);
        viewModel.getErrorLiveData().observe(getViewLifecycleOwner(), this::handleError);
    }

    private void setListeners() {
        binding.materialToolbar.setNavigationOnClickListener(view ->
                Navigation.findNavController(requireView()).popBackStack());

        binding.emailEditText.addTextChangedListener(myTextWatcher);
        binding.passwordEditText.addTextChangedListener(myTextWatcher);

        binding.registerButton.setOnClickListener(view -> {
            String email = Objects.requireNonNull(binding.emailEditText.getText()).toString().trim();
            String password = Objects.requireNonNull(binding.passwordEditText.getText()).toString().trim();
            viewModel.register(email, password);
            VisibilityHandler.setVisibleView(binding.progressIndicator.getRoot());
        });
    }

    private void disableErrorMessage() {
        binding.emailInputLayout.setErrorEnabled(false);
        binding.passwordInputLayout.setErrorEnabled(false);
    }

    private void handleButton() {
        disableErrorMessage();
        String email = Objects.requireNonNull(binding.emailEditText.getText()).toString().trim();
        String password = Objects.requireNonNull(binding.passwordEditText.getText()).toString().trim();
        binding.registerButton.setEnabled(!email.isEmpty() && !password.isEmpty());
    }

    public void handleUser(FirebaseUser firebaseUser) {
        if (firebaseUser != null) {
            ((MainActivity) requireActivity()).openHomeActivity();
        }
        VisibilityHandler.setGoneView(binding.progressIndicator.getRoot());
    }

    private void handleNetworkError(Boolean bool) {
        if (bool) {
            ((MainActivity) requireActivity()).openNetworkErrorActivity();
        }
        binding.passwordEditText.setText(null);
        VisibilityHandler.setGoneView(binding.progressIndicator.getRoot());
    }

    public void handleError(RegisterRepository.Error error) {
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