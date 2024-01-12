package com.example.cineverse.view.settings.fragment.section;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.cineverse.R;
import com.example.cineverse.data.model.User;
import com.example.cineverse.databinding.FragmentUsernameSettingsBinding;
import com.example.cineverse.repository.auth.logged.VerifiedAccountRepository;
import com.example.cineverse.view.settings.SettingsActivity;
import com.example.cineverse.viewmodel.verified_account.VerifiedAccountViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

/**
 * The {@link UsernameSettingsFragment} : display actual username and let the user
 * change his Username
 */
public class UsernameSettingsFragment extends Fragment {

    private FragmentUsernameSettingsBinding binding;
    private VerifiedAccountViewModel viewModel;
    private String currentUsername;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUsernameSettingsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setActionBar();
        setViewModel();
        setListeners();
    }

    private void setActionBar(){
        ActionBar actionBar = ((SettingsActivity) requireActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.account_settings_name);
        }
    }

    private void setViewModel(){
        viewModel = new ViewModelProvider(this).get(VerifiedAccountViewModel.class);
        viewModel.getUserLiveData().observe(getViewLifecycleOwner(), this::handleUser);
        viewModel.getUsernameChangedLiveData().observe(getViewLifecycleOwner(), this::handleUsernameChanged);
        viewModel.getChangeUsernameErrorLiveData().observe(getViewLifecycleOwner(), this::handleUserFormatError);
        viewModel.getNetworkErrorLiveData().observe(getViewLifecycleOwner(), this::handleNetworkError);
    }

    private void setListeners() {
        binding.usernameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                handleButton();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.confirmChangeButton.setOnClickListener(v -> {
            String newUsername = Objects.requireNonNull(binding.usernameEditText.getText()).toString().trim();
            viewModel.changeUsername(newUsername);
            binding.progressIndicator.getRoot().setVisibility(View.VISIBLE);
        });
    }

    private void handleButton() {
        disableErrorMessage();
        String newUsername = Objects.requireNonNull(binding.usernameEditText.getText()).toString().trim();
        binding.confirmChangeButton.setEnabled(!newUsername.isEmpty() && !newUsername.equals(currentUsername));
    }

    private void disableErrorMessage() {
        binding.usernameInputLayout.setErrorEnabled(false);
        viewModel.getChangeUsernameErrorLiveData().setValue(null);
    }

    private void handleUser(User user){
        if (user != null) {
            currentUsername = user.getUsername();
            binding.usernameEditText.setText(user.getUsername());
            handleButton();
        }
    }

    private void handleUsernameChanged(Boolean changed) {
        if (changed != null && changed) {
            Snackbar.make(binding.getRoot(),
                    R.string.username_changed, Snackbar.LENGTH_SHORT).show();
            requireActivity().getOnBackPressedDispatcher().onBackPressed();
        } else {
            Snackbar.make(binding.getRoot(),
                    R.string.unexpected_error, Snackbar.LENGTH_SHORT).show();
        }
        binding.progressIndicator.getRoot().setVisibility(View.GONE);
    }

    private void handleUserFormatError(VerifiedAccountRepository.Error error) {
        if (error != null) {
            binding.usernameEditText.setText(null);
            String errorString = getString(error.getError());
            binding.usernameInputLayout.setError(errorString);
        }
        binding.progressIndicator.getRoot().setVisibility(View.GONE);
    }

    private void handleNetworkError(Boolean bool) {
        if (bool != null && bool) {
            ((SettingsActivity) requireActivity()).openNetworkErrorActivity();
            viewModel.getNetworkErrorLiveData().setValue(null);
        }
        binding.progressIndicator.getRoot().setVisibility(View.GONE);
    }

    /**
     * Called when the fragment is no longer in use.
     * This method ensures the ActionBar style is reset and releases the binding.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        ((SettingsActivity)requireActivity()).setActionBarStyle();
        binding = null;
    }

}