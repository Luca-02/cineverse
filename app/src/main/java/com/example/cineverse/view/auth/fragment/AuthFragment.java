package com.example.cineverse.view.auth.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.cineverse.data.model.user.User;
import com.example.cineverse.R;
import com.example.cineverse.repository.auth.service.LoginRepository;
import com.example.cineverse.view.auth.AuthActivity;
import com.example.cineverse.viewmodel.auth.service.AuthViewModel;
import com.example.cineverse.databinding.FragmentAuthBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.material.snackbar.Snackbar;

/**
 * The {@link AuthFragment} class represents the user authentication screen of the application.
 * Users can log in with their email and password, register a new account, or sign in using
 * their Google account. This fragment handles user input and interacts with the {@link AuthViewModel}
 * to perform authentication operations. It also provides navigation to the registration and
 * login screens as well as handling Google sign-in operations.
 */
public class AuthFragment extends Fragment {

    // Fullscreen flag
    private static final int UI_OPTIONS = WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;

    private FragmentAuthBinding binding;
    private AuthViewModel viewModel;
    private GoogleSignInClient googleSignInClient;
    private ActivityResultLauncher<Intent> googleSignInLauncher;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAuthBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setViewModel();
        setListeners();
        createGoogleRequest();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        requireActivity().getWindow().addFlags(UI_OPTIONS);
    }

    @Override
    public void onPause() {
        super.onPause();
        requireActivity().getWindow().clearFlags(UI_OPTIONS);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        requireActivity().getWindow().clearFlags(UI_OPTIONS);
    }

    /**
     * Sets up the ViewModel for the fragment.
     */
    private void setViewModel() {
        viewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        viewModel.getUserLiveData().observe(getViewLifecycleOwner(), this::handleUser);
        viewModel.getNetworkErrorLiveData().observe(getViewLifecycleOwner(), this::handleNetworkError);
        viewModel.getErrorLiveData().observe(getViewLifecycleOwner(), this::handleError);
    }

    /**
     * Sets up the UI button listeners for registration, Google sign-in, and login.
     */
    private void setListeners() {
        binding.registerButton.setOnClickListener(view ->
                Navigation.findNavController(requireView())
                        .navigate(R.id.action_authFragment_to_registerFragment));
        binding.googleButton.setOnClickListener(view ->
                googleSignIn());
        binding.loginButton.setOnClickListener(view ->
                Navigation.findNavController(requireView())
                        .navigate(R.id.action_authFragment_to_loginFragment));
    }

    /**
     * Configures the Google sign-in request and launches the Google sign-in flow.
     */
    private void createGoogleRequest() {
        googleSignInClient = GoogleSignIn.getClient(
                requireActivity(), viewModel.getGoogleSignInOptions());
        googleSignInClient.signOut();

        googleSignInLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        viewModel.authWithGoogle(data);
                        binding.progressIndicator.getRoot().setVisibility(View.VISIBLE);
                    }
                });
    }

    /**
     * Initiates the Google sign-in flow by launching the sign-in intent.
     */
    private void googleSignIn() {
        googleSignInClient.getApplicationContext();
        Intent intent = googleSignInClient.getSignInIntent();
        googleSignInLauncher.launch(intent);
    }

    /**
     * Handles the authentication state changes for the user. Navigates the user to the home
     * screen when authenticated.
     *
     * @param user The authenticated {@link User} object. If {@code null}, user is not authenticated.
     */
    private void handleUser(User user) {
        if (user != null) {
            boolean isEmailVerified = viewModel.isEmailVerified();
            ((AuthActivity) requireActivity()).openLoggedActivity(isEmailVerified);
        }
        binding.progressIndicator.getRoot().setVisibility(View.GONE);
    }

    /**
     * Handles network error states. Navigates the user to the network error screen when there
     * is a network error.
     *
     * @param bool {@code true} if there is a network error, {@code false} otherwise.
     */
    private void handleNetworkError(Boolean bool) {
        if (bool) {
            ((AuthActivity) requireActivity()).openNetworkErrorActivity();
        }
        binding.progressIndicator.getRoot().setVisibility(View.GONE);
    }

    /**
     * Handles authentication errors. Displays a {@link Snackbar} with the error message.
     *
     * @param error The type of authentication error that occurred.
     */
    private void handleError(LoginRepository.Error error) {
        String errorString = getString(error.getError());
        Snackbar.make(binding.getRoot(),
                errorString, Snackbar.LENGTH_SHORT).show();
        binding.progressIndicator.getRoot().setVisibility(View.GONE);
    }

}