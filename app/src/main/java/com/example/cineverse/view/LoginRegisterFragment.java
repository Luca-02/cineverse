package com.example.cineverse.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.cineverse.R;
import com.example.cineverse.databinding.FragmentLoginRegisterBinding;
import com.example.cineverse.viewmodel.LoginRegisterViewModel;

public class LoginRegisterFragment extends Fragment {

    private FragmentLoginRegisterBinding binding;
    private LoginRegisterViewModel loginRegisterViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLoginRegisterBinding.inflate(inflater, container, false);
        setupViewModel();
        setupListeners();
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    /* - */

    private void setupViewModel() {
        loginRegisterViewModel = new ViewModelProvider(this).get(LoginRegisterViewModel.class);

        loginRegisterViewModel.getUserLiveData().observe(getViewLifecycleOwner(), firebaseUser -> {
            if (firebaseUser != null)
                Navigation.findNavController(requireView()).navigate(R.id.action_login);
        });
    }

    private void setupListeners() {
        binding.loginButton.setOnClickListener(view -> {
            String email = binding.emailEditText.getText().toString().trim();
            String password = binding.passwordEditText.getText().toString().trim();
            if (!email.isEmpty() && !password.isEmpty()) {
                loginRegisterViewModel.login(email, password);
            } else {
                showToastInvalidCredentials();
            }
        });

        binding.registerButton.setOnClickListener(view -> {
            String email = binding.emailEditText.getText().toString().trim();
            String password = binding.passwordEditText.getText().toString().trim();
            if (!email.isEmpty() && !password.isEmpty()) {
                loginRegisterViewModel.register(email, password);
            } else {
                showToastInvalidCredentials();
            }
        });
    }
    private void showToastInvalidCredentials() {
        Toast.makeText(getContext(), R.string.invalid_credential, Toast.LENGTH_SHORT).show();
    }

}