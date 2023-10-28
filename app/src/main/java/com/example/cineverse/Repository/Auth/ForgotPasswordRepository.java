package com.example.cineverse.Repository.Auth;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.example.cineverse.Repository.AuthRepository;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

import org.apache.commons.validator.routines.EmailValidator;

public class ForgotPasswordRepository extends AuthRepository {

    private final MutableLiveData<Error> errorLiveData;

    public ForgotPasswordRepository(Application application) {
        super(application);
        errorLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<Error> getErrorLiveData() {
        return errorLiveData;
    }

    public void forgotPassword(String email) {
        if (!EmailValidator.getInstance().isValid(email)) {
            postError(Error.ERROR_INVALID_EMAIL_FORMAT);
        } else {
            firebaseAuth.sendPasswordResetEmail(email)
                    .addOnSuccessListener(authResult -> handleSuccess())
                    .addOnFailureListener(this::handleFailure);
        }
    }

    private void handleSuccess() {
        postError(Error.SUCCESS);
    }

    private void handleFailure(Exception exception) {
        if (exception instanceof FirebaseAuthInvalidUserException) {
            postError(Error.ERROR_NOT_FOUND_DISABLED);
        } else if (exception instanceof FirebaseNetworkException) {
            setNetworkErrorLiveData(true);
        } else {
            postError(Error.ERROR_INVALID_CREDENTIAL);
        }
    }

    private void postError(Error result) {
        errorLiveData.postValue(result);
    }

}
