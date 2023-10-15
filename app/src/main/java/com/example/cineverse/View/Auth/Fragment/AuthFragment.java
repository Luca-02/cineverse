package com.example.cineverse.View.Auth.Fragment;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cineverse.Handler.UI.VisibilityHandler;
import com.example.cineverse.R;
import com.example.cineverse.View.Auth.MainActivity;
import com.example.cineverse.ViewModel.Auth.AuthViewModel;
import com.example.cineverse.databinding.FragmentAuthBinding;
import com.google.firebase.auth.FirebaseUser;

public class AuthFragment extends Fragment {

    private FragmentAuthBinding binding;
    private AuthViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                requireActivity().finish();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAuthBinding.inflate(inflater, container, false);
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
        viewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        viewModel.getUserLiveData().observe(getViewLifecycleOwner(), this::handleUser);
    }

    private void setListeners() {
        binding.registerButton.setOnClickListener(view ->
                Navigation.findNavController(requireView()).navigate(R.id.action_authFragment_to_registerFragment));

        binding.loginButton.setOnClickListener(view ->
                Navigation.findNavController(requireView()).navigate(R.id.action_authFragment_to_loginFragment));
    }

    public void handleUser(FirebaseUser firebaseUser) {
        if (firebaseUser != null) {
            ((MainActivity) requireActivity()).openHomeActivity();
        }
        VisibilityHandler.setGoneView(binding.progressIndicator.getRoot());
    }

}