package com.example.cineverse.View.Auth.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.cineverse.Handler.UI.VisibilityHandler;
import com.example.cineverse.R;
import com.example.cineverse.Repository.Auth.LoginRepository;
import com.example.cineverse.View.Auth.MainActivity;
import com.example.cineverse.ViewModel.Auth.AuthViewModel;
import com.example.cineverse.databinding.FragmentAuthBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseUser;

/**
 * The AuthFragment class represents the user authentication screen of the application.
 * Users can log in with their email and password, register a new account, or sign in using
 * their Google account. This fragment handles user input and interacts with the AuthViewModel
 * to perform authentication operations. It also provides navigation to the registration and
 * login screens as well as handling Google sign-in operations.
 */
public class AuthFragment extends Fragment {

    private FragmentAuthBinding binding;
    private AuthViewModel viewModel;
    private GoogleSignInClient googleSignInClient;
    private ActivityResultLauncher<Intent> googleSignInLauncher;

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
        createGoogleRequest();
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    /**
     * Initializes the AuthViewModel for this fragment.
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
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso);
        googleSignInClient.signOut();

        googleSignInLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        viewModel.authWithGoogle(data);
                        VisibilityHandler.setVisibleView(binding.progressIndicator.getRoot());
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
     * @param firebaseUser The authenticated FirebaseUser object. If null, user is not authenticated.
     */
    private void handleUser(FirebaseUser firebaseUser) {
        if (firebaseUser != null) {
            ((MainActivity) requireActivity()).openHomeActivity();
        }
        VisibilityHandler.setGoneView(binding.progressIndicator.getRoot());
    }

    /**
     * Handles network error states. Navigates the user to the network error screen when there
     * is a network error.
     *
     * @param bool True if there is a network error, false otherwise.
     */
    private void handleNetworkError(Boolean bool) {
        if (bool) {
            ((MainActivity) requireActivity()).openNetworkErrorActivity();
        }
        VisibilityHandler.setGoneView(binding.progressIndicator.getRoot());
    }

    /**
     * Handles authentication errors. Displays a Snackbar with the error message.
     *
     * @param error The type of authentication error that occurred.
     */
    private void handleError(LoginRepository.Error error) {
        String errorString = getString(error.getError());
        Snackbar.make(binding.getRoot(),
                errorString, Snackbar.LENGTH_SHORT).show();
        VisibilityHandler.setGoneView(binding.progressIndicator.getRoot());
    }

}