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
import android.widget.Toast;

import com.example.cineverse.Handler.UI.VisibilityHandler;
import com.example.cineverse.R;
import com.example.cineverse.ViewModel.Auth.ForgotPasswordViewModel;
import com.example.cineverse.databinding.FragmentForgotPasswordBinding;

import java.util.Objects;

public class ForgotPasswordFragment extends Fragment {

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

        viewModel.getErrorLiveData().observe(getViewLifecycleOwner(), result -> {
            if (result.isSuccess()) {
                handleSuccess();
            } else {
                handleError(result.getError());
            }
            VisibilityHandler.setGoneView(binding.progressIndicator.getRoot());
        });
    }

    private void setListeners() {
        binding.materialToolbar.setNavigationOnClickListener(view ->
                Navigation.findNavController(requireView()).popBackStack());

        MyTextWatcher myTextWatcher = new MyTextWatcher();
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

    private void handleSuccess() {
        Toast.makeText(getContext(), R.string.email_sent, Toast.LENGTH_LONG).show();
        Navigation.findNavController(requireView())
                .navigate(R.id.action_forgotPasswordFragment_to_loginFragment);
    }

    private void handleError(int errorMessage) {
        binding.emailInputLayout.setError(getString(errorMessage));
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