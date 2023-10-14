package com.example.cineverse.View.Auth.Fragment.Auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.cineverse.R;
import com.example.cineverse.Repository.Auth.LoginRepository;
import com.example.cineverse.View.Home.HomeActivity;
import com.example.cineverse.ViewModel.Auth.LoginViewModel;
import com.example.cineverse.databinding.FragmentLoginBinding;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;
    private LoginViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Navigation.findNavController(requireView())
                        .navigate(R.id.action_loginFragment_to_authFragment);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
            showProgressIndicator();
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

    private void showProgressIndicator() {
        binding.progressIndicator.getRoot().setVisibility(View.VISIBLE);
    }

    private void hideProgressIndicator() {
        binding.progressIndicator.getRoot().setVisibility(View.INVISIBLE);
    }

    private void handleUser(FirebaseUser firebaseUser) {
        if (firebaseUser != null) {
            Intent intent = new Intent(requireActivity(), HomeActivity.class);
            // Close all previews activity
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            requireActivity().finish();
        }
        hideProgressIndicator();
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
        hideProgressIndicator();
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