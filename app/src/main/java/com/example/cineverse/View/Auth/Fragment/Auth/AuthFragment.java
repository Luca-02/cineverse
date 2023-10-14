package com.example.cineverse.View.Auth.Fragment.Auth;

import android.content.Intent;
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

import com.example.cineverse.R;
import com.example.cineverse.View.Home.HomeActivity;
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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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

    private void showProgressIndicator() {
        binding.progressIndicator.getRoot().setVisibility(View.VISIBLE);
    }

    private void hideProgressIndicator() {
        binding.progressIndicator.getRoot().setVisibility(View.INVISIBLE);
    }

    public void handleUser(FirebaseUser firebaseUser) {
        if (firebaseUser != null) {
            Intent intent = new Intent(requireActivity(), HomeActivity.class);
            // Close all previews activity
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            requireActivity().finish();
        }
        hideProgressIndicator();
    }

}