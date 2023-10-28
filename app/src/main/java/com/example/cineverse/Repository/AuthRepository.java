package com.example.cineverse.Repository;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.example.cineverse.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
    private final MutableLiveData<Boolean> networkErrorLiveData;

    public AuthRepository(Application application) {
        this.application = application;
        userLiveData = new MutableLiveData<>();
        networkErrorLiveData = new MutableLiveData<>();

        FirebaseUser currentUser = getCurrentUser();
        if (currentUser != null) {
            userLiveData.postValue(currentUser);
        }
    }

    public MutableLiveData<FirebaseUser> getUserLiveData() {
        return userLiveData;
    }

    public void setUserLiveData(FirebaseUser firebaseUser) {
        userLiveData.postValue(firebaseUser);
    }

    public MutableLiveData<Boolean> getNetworkErrorLiveData() {
        return networkErrorLiveData;
    }

    public void setNetworkErrorLiveData(boolean bool) {
        networkErrorLiveData.postValue(bool);
    }

    public static FirebaseUser getCurrentUser() {
        return firebaseAuth.getCurrentUser();
    }

    public static boolean isEmailVerified() {
        FirebaseUser user = getCurrentUser();
        return user != null && user.isEmailVerified();
    }

}
