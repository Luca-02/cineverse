package com.example.cineverse.Repository;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.example.cineverse.R;
import com.example.cineverse.Utils.EmailValidator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class AuthRepository {

    public enum Error {
        SUCCESS(null),
        ERROR_INVALID_EMAIL(R.string.invalid_email),
        ERROR_INVALID_EMAIL_FORMAT(R.string.invalid_email_format),
        ERROR_NOT_FOUND_DISABLED(R.string.user_not_found_disabled),
        ERROR_ALREADY_EXISTS(R.string.user_already_exist),
        ERROR_WEAK_PASSWORD(R.string.weak_password),
        ERROR_WRONG_PASSWORD(R.string.wrong_password),
        ERROR_INVALID_CREDENTIAL(R.string.invalid_credentials);

        private final Integer stringId;

        Error(Integer stringId) {
            this.stringId = stringId;
        }

        public boolean isSuccess() {
            return stringId == null;
        }

        public int getError() {
            return stringId;
        }
    }

    protected static final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    protected final Application application;
    private final MutableLiveData<FirebaseUser> userLiveData;

    public AuthRepository(Application application) {
        this.application = application;
        userLiveData = new MutableLiveData<>();

        if (firebaseAuth.getCurrentUser() != null) {
            userLiveData.postValue(firebaseAuth.getCurrentUser());
        }
    }

    public MutableLiveData<FirebaseUser> getUserLiveData() {
        return userLiveData;
    }

    public void setUserLiveData(FirebaseUser firebaseUser) {
        userLiveData.postValue(firebaseUser);
    }

}
