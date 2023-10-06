package com.example.cineverse.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cineverse.R;
import com.example.cineverse.databinding.FragmentLoggedBinding;
import com.example.cineverse.databinding.FragmentLoginRegisterBinding;
import com.example.cineverse.viewmodel.LoggedInViewModel;
import com.google.firebase.auth.FirebaseUser;

public class LoggedFragment extends Fragment {

    private FragmentLoggedBinding binding;
    private LoggedInViewModel loggedInViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLoggedBinding.inflate(inflater, container, false);
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

    public void setupViewModel() {
        loggedInViewModel = new ViewModelProvider(this).get(LoggedInViewModel.class);

        loggedInViewModel.getUserLiveData().observe(getViewLifecycleOwner(), firebaseUser -> {
            if (firebaseUser != null) {
                Log.d("TEST", "" + firebaseUser.getUid());
                Log.d("TEST", "" + firebaseUser.getDisplayName());
                binding.userEmail.setText(String.format("Logged In User: %s", firebaseUser.getEmail()));
                binding.logoutButton.setEnabled(true);
            } else {
                binding.logoutButton.setEnabled(false);
            }
        });

        loggedInViewModel.getLoggedOutLiveData().observe(getViewLifecycleOwner(), loggedOut -> {
            if (loggedOut) {
                Toast.makeText(getContext(), R.string.user_logout, Toast.LENGTH_SHORT).show();
                Navigation.findNavController(requireView()).navigate(R.id.action_logout);
            }
        });
    }

    public void setupListeners() {
        binding.logoutButton.setOnClickListener(view -> loggedInViewModel.logOut());
    }

}