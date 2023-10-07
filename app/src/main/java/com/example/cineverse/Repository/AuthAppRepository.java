package com.example.cineverse.Repository;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class AuthAppRepository {

    private final Application application;
    private final FirebaseAuth firebaseAuth;
    private final MutableLiveData<FirebaseUser> userLiveData;
    private final MutableLiveData<Boolean> loggedOutLiveData;
    private final MutableLiveData<Boolean> invalidEmailLiveData;
    private final MutableLiveData<Boolean> wrongPasswordLiveData;
    private final MutableLiveData<Boolean> userNotFoundLiveData;
    private final MutableLiveData<Boolean> userDisabledLiveData;

    public AuthAppRepository(Application application) {
        this.application = application;
        firebaseAuth = FirebaseAuth.getInstance();
        userLiveData = new MutableLiveData<>();
        loggedOutLiveData = new MutableLiveData<>();
        invalidEmailLiveData = new MutableLiveData<>();
        wrongPasswordLiveData = new MutableLiveData<>();
        userNotFoundLiveData = new MutableLiveData<>();
        userDisabledLiveData = new MutableLiveData<>();

        if (firebaseAuth.getCurrentUser() != null) {
            userLiveData.postValue(firebaseAuth.getCurrentUser());
            loggedOutLiveData.postValue(false);
            invalidEmailLiveData.postValue(false);
            wrongPasswordLiveData.postValue(false);
            userNotFoundLiveData.postValue(false);
            userDisabledLiveData.postValue(false);
        }
    }

    public MutableLiveData<FirebaseUser> getUserLiveData() {
        return userLiveData;
    }

    public MutableLiveData<Boolean> getLoggedOutLiveData() {
        return loggedOutLiveData;
    }

    public MutableLiveData<Boolean> getInvalidEmailLiveData() {
        return invalidEmailLiveData;
    }

    public MutableLiveData<Boolean> getWrongPasswordLiveData() {
        return wrongPasswordLiveData;
    }

    public MutableLiveData<Boolean> getUserNotFoundLiveData() {
        return userNotFoundLiveData;
    }

    public MutableLiveData<Boolean> getUserDisabledLiveData() {
        return userDisabledLiveData;
    }

    /* - */

    public void register(String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        userLiveData.postValue(firebaseAuth.getCurrentUser());
                    } else {
                        Toast.makeText(application,
                                        "Registration Failure: " + Objects.requireNonNull(task.getException()).getMessage(),
                                        Toast.LENGTH_SHORT)
                                .show();
                    }
                });
    }

    public void login(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    // Receive response from Firebase Console
                })
                .addOnSuccessListener(authResult -> {
                    // User has signed in successfully
                    userLiveData.postValue(authResult.getUser());
                })
                .addOnFailureListener(exception -> {
                    // User cannot sign in and an exception is thrown
                    Log.d("MY_TAG", "login: " + exception.getMessage());
                    Log.d("MY_TAG", "login: " + exception.getClass());

                    if (exception instanceof FirebaseAuthInvalidCredentialsException) {
                        String errorCode = ((FirebaseAuthInvalidCredentialsException) exception).getErrorCode();
                        Log.d("MY_TAG", "login: " + errorCode);

                        if (errorCode.equals("ERROR_WRONG_PASSWORD")) {
                            wrongPasswordLiveData.postValue(true);
                        } else if (errorCode.equals("ERROR_INVALID_EMAIL")) {
                            invalidEmailLiveData.setValue(true);
                        } else {
                            Toast.makeText(application, "Invalid credentials", Toast.LENGTH_SHORT).show();
                        }
                    } else if (exception instanceof FirebaseAuthInvalidUserException) {
                        String errorCode = ((FirebaseAuthInvalidUserException) exception).getErrorCode();
                        Log.d("MY_TAG", "login: " + errorCode);

                        if (errorCode.equals("ERROR_USER_NOT_FOUND")) {
                            userNotFoundLiveData.postValue(true);
                        } else if (errorCode.equals("ERROR_USER_DISABLED")) {
                            userDisabledLiveData.postValue(true);
                        } else {
                            Toast.makeText(application, "Invalid credentials", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(application,
                                        Objects.requireNonNull(exception).getMessage(),
                                        Toast.LENGTH_SHORT)
                                .show();
                    }
                });
    }

    public void logOut() {
        firebaseAuth.signOut();
        loggedOutLiveData.postValue(true);
    }

}
