package com.example.cineverse.View.Auth.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
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
import com.example.cineverse.Repository.AuthRepository;
import com.example.cineverse.View.Auth.MainActivity;
import com.example.cineverse.ViewModel.Auth.ForgotPasswordViewModel;
import com.example.cineverse.databinding.FragmentForgotPasswordBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

public class ForgotPasswordFragment extends Fragment {

    private final MyTextWatcher myTextWatcher = new MyTextWatcher();

    private FragmentForgotPasswordBinding binding;
    private ForgotPasswordViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentForgotPasswordBinding.inflate(inflater, container, false);
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
        viewModel = new ViewModelProvider(this).get(ForgotPasswordViewModel.class);
        viewModel.getNetworkErrorLiveData().observe(getViewLifecycleOwner(), this::handleNetworkError);
        viewModel.getErrorLiveData().observe(getViewLifecycleOwner(), this::handleError);
    }

    private void setListeners() {
        binding.materialToolbar.setNavigationOnClickListener(view ->
                Navigation.findNavController(requireView()).popBackStack());

        binding.emailEditText.addTextChangedListener(myTextWatcher);

        binding.resetPasswordButton.setOnClickListener(view -> {
            String email = Objects.requireNonNull(
                    binding.emailEditText.getText()).toString().trim();
            viewModel.forgotPassword(email);
            VisibilityHandler.setVisibleView(binding.progressIndicator.getRoot());
        });
    }

    private void disableErrorMessage() {
        binding.emailInputLayout.setErrorEnabled(false);
    }

    private void handleButton() {
        disableErrorMessage();
        String email = Objects.requireNonNull(binding.emailEditText.getText()).toString().trim();
        binding.resetPasswordButton.setEnabled(!email.isEmpty());
    }

    private void handleNetworkError(Boolean bool) {
        if (bool) {
            ((MainActivity) requireActivity()).openNetworkErrorActivity();
        }
        VisibilityHandler.setGoneView(binding.progressIndicator.getRoot());
    }

    private void handleSuccess() {
        Snackbar.make(binding.getRoot(),
                R.string.email_sent, Snackbar.LENGTH_LONG).show();
        Navigation.findNavController(requireView())
                .navigate(R.id.action_forgotPasswordFragment_to_loginFragment);
    }

    private void handleError(AuthRepository.Error result) {
        if (result.isSuccess()) {
            handleSuccess();
        } else {
            binding.emailInputLayout.setError(getString(result.getError()));
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