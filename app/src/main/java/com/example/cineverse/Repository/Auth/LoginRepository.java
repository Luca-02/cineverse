package com.example.cineverse.Repository.Auth;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.example.cineverse.Interface.Auth.ILogin;
import com.example.cineverse.Repository.AbstractAuthServiceRepository;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

import org.apache.commons.validator.routines.EmailValidator;

/**
 * The LoginRepository class extends AbstractAuthServiceRepository and provides authentication functionality
 * for user login. It validates the user input, performs login operations using Firebase
 * authentication services, and handles success and failure scenarios. Errors and authentication
 * status are communicated via MutableLiveData for observation and user feedback.
 */
public class LoginRepository
        extends AbstractAuthServiceRepository
        implements ILogin {

    /**
     * Constructs a LoginRepository object with the given Application context.
     *
     * @param application The Application context of the calling component.
     */
    public LoginRepository(Application application) {
        super(application);
    }

    /**
     * Performs user login with the provided email and password. Validates the email format,
     * initiates the login operation, and communicates errors or success through errorLiveData.
     *
     * @param email    User's email address for login.
     * @param password User's password for login.
     */
    @Override
    public void login(String email, String password) {
        if (!EmailValidator.getInstance().isValid(email)) {
            errorLiveData.postValue(Error.ERROR_INVALID_EMAIL_FORMAT);
        } else {
            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnSuccessListener(this::handleSuccess)
                    .addOnFailureListener(this::handleFailure);
        }
    }

    /**
     * Handles successful authentication by updating the user live data.
     *
     * @param authResult The successful authentication result containing user information.
     */
    private void handleSuccess(AuthResult authResult) {
        setUserLiveData(authResult.getUser());
    }

    /**
     * Handles authentication failure scenarios by identifying the type of exception and
     * posting appropriate errors for user feedback through errorLiveData.
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
