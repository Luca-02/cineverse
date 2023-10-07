package com.example.cineverse.View.Start.Fragment.Auth;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.cineverse.R;
import com.example.cineverse.ViewModel.Auth.RegisterViewModel;
import com.example.cineverse.databinding.FragmentRegisterBinding;

import java.util.Objects;

public class RegisterFragment extends Fragment {

    private FragmentRegisterBinding binding;
    private RegisterViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        setViewModel();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setListeners();
    }

    private void setViewModel() {
        viewModel = new ViewModelProvider(this).get(RegisterViewModel.class);

        viewModel.getUserLiveData().observe(getViewLifecycleOwner(), firebaseUser -> {
            if (firebaseUser != null)
                Navigation.findNavController(requireView()).navigate(R.id.action_registerFragment_to_homeFragment);
        });
    }

    private void setListeners() {
        binding.registerButton.setOnClickListener(view -> {
            String email = Objects.requireNonNull(binding.emailEditText.getText()).toString().trim();
            String password = Objects.requireNonNull(binding.passwordEditText.getText()).toString().trim();
            if (!email.isEmpty() && !password.isEmpty()) {
                viewModel.register(email, password);
            } else {
                showToastInvalidCredentials();
            }
        });

        binding.loginText.setOnClickListener(view -> {
            Navigation.findNavController(requireView()).navigate(R.id.action_registerFragment_to_loginFragment);
        });
    }

    private void showToastInvalidCredentials() {
        Toast.makeText(getContext(), R.string.invalid_credential, Toast.LENGTH_SHORT).show();
    }

}