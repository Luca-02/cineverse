package com.example.cineverse.View.Start.Fragment.Auth;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.cineverse.R;
import com.example.cineverse.ViewModel.Auth.LoginViewModel;
import com.example.cineverse.databinding.FragmentLoginBinding;

import java.util.Objects;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;
    private LoginViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        setViewModel();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setListeners();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void setViewModel() {
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        viewModel.getUserLiveData().observe(getViewLifecycleOwner(), firebaseUser -> {
            if (firebaseUser != null)
                Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_homeFragment);
        });

        viewModel.getInvalidEmailLiveData().observe(getViewLifecycleOwner(), bool -> {
            if (bool) {
                binding.emailInputLayout.setError(getString(R.string.invalid_email));
            }
        });

        viewModel.getWrongPasswordLiveData().observe(getViewLifecycleOwner(), bool -> {
            if (bool) {
                binding.passwordInputLayout.setError(getString(R.string.wrong_password));
            }
        });

        viewModel.getUserNotFoundLiveData().observe(getViewLifecycleOwner(), bool -> {
            if (bool) {
                binding.passwordInputLayout.setError(getString(R.string.user_not_found));
            }
        });

        viewModel.getUserDisabledLiveData().observe(getViewLifecycleOwner(), bool -> {
            if (bool) {
                binding.passwordInputLayout.setError(getString(R.string.user_disabled));
            }
        });
    }

    private void setListeners() {
        binding.emailEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.emailInputLayout.setErrorEnabled(false);
                binding.passwordInputLayout.setErrorEnabled(false);
                handleLoginButton();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        binding.passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.emailInputLayout.setErrorEnabled(false);
                binding.passwordInputLayout.setErrorEnabled(false);
                handleLoginButton();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        binding.loginButton.setOnClickListener(view -> {
            String email = Objects.requireNonNull(binding.emailEditText.getText()).toString().trim();
            String password = Objects.requireNonNull(binding.passwordEditText.getText()).toString().trim();
            if (!email.isEmpty() && !password.isEmpty()) {
                viewModel.login(email, password);
            } else {
                showToastInvalidCredentials();
            }
        });

        binding.registerText.setOnClickListener(view -> {
            Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_registerFragment);
        });
    }

    private void showToastInvalidCredentials() {
        Toast.makeText(getContext(), R.string.invalid_credential, Toast.LENGTH_SHORT).show();
    }

    private void handleLoginButton() {
        String email = Objects.requireNonNull(binding.emailEditText.getText()).toString().trim();
        String password = Objects.requireNonNull(binding.passwordEditText.getText()).toString().trim();
        if (!email.isEmpty() && !password.isEmpty()) {
            binding.loginButton.setEnabled(true);
        } else {
            binding.loginButton.setEnabled(false);
        }
    }

}