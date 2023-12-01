package com.example.cineverse.repository.classes.auth.service;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.example.cineverse.data.model.user.User;
import com.example.cineverse.service.firebase.UserFirebaseDatabaseServices;
import com.example.cineverse.repository.classes.auth.AbstractAuthRepository;
import com.example.cineverse.repository.interfaces.auth.service.ILogin;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

import org.apache.commons.validator.routines.EmailValidator;

/**
 * The {@link LoginRepository} class extends {@link AbstractAuthRepository} and provides authentication functionality
 * for user login. It validates the user input, performs login operations using Firebase
 * authentication services, and handles success and failure scenarios. Errors and authentication
 * status are communicated via {@link MutableLiveData} for observation and user feedback.
 */
public class LoginRepository
        extends AbstractAuthRepository
        implements ILogin {

    /**
     * Constructs a {@link LoginRepository} object with the given application {@link Context}.
     *
     * @param context The Application context of the calling component.
     */
    public LoginRepository(Context context) {
        super(context);
    }

    /**
     * Performs user login with the provided account and password. Validates the account format,
     * initiates the login operation, and communicates errors or success through {@code errorLiveData}.
     *
     * @param account   User's account (username or email) for login.
     * @param password  User's password for login.
     */
    @Override
    public void login(String account, String password) {
        if (!EmailValidator.getInstance().isValid(account)) {
            if (account.contains("@")) {
                errorLiveData.postValue(Error.ERROR_INVALID_EMAIL_FORMAT);
            } else {
                userStorage.getFirebaseSource().getEmailFromUsername(account, context,
                        new UserFirebaseDatabaseServices.Callback<String>() {
                            @Override
                            public void onCallback(String email) {
                                if (email != null) {
                                    signInWithCredentials(email, password);
                                } else {
                                    errorLiveData.postValue(Error.ERROR_NOT_FOUND_DISABLED);
                                }
                            }

                            @Override
                            public void onNetworkUnavailable() {
                                setNetworkErrorLiveData(true);
                            }
                        });
            }
        } else {
            signInWithCredentials(account, password);
        }
    }

    private void signInWithCredentials(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(this::handleSuccess)
                .addOnFailureListener(this::handleFailure);
    }

    /**
     * Handles successful authentication by updating the user live data.
     *
     * @param authResult The successful authentication result containing user information.
     */
    private void handleSuccess(AuthResult authResult) {
        FirebaseUser firebaseUser = authResult.getUser();
        if (firebaseUser != null) {
            userStorage.loginUser(firebaseUser.getUid(),
                    new UserFirebaseDatabaseServices.Callback<User>() {
                        @Override
                        public void onCallback(User user) {
                            handleUserAuthentication(user);
                        }

                        @Override
                        public void onNetworkUnavailable() {
                            handleAuthenticationNetworkFailure();
                        }
                    });
        } else {
            handleAuthenticationFailure();
        }

    }

    /**
     * Handles authentication failure scenarios by identifying the type of exception and
     * posting appropriate errors for user feedback through {@code errorLiveData}.
     *
     * @param exception The exception occurred during the authentication process.
     */
    private void handleFailure(Exception exception) {
        if (exception instanceof FirebaseAuthInvalidUserException) {
            errorLiveData.postValue(Error.ERROR_NOT_FOUND_DISABLED);
        } else if (exception instanceof FirebaseNetworkException) {
            setNetworkErrorLiveData(true);
        } else {
            errorLiveData.postValue(Error.ERROR_WRONG_PASSWORD);
        }
    }

}
