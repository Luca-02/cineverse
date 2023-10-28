package com.example.cineverse.Repository.Auth;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.example.cineverse.Repository.AuthRepository;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

import org.apache.commons.validator.routines.EmailValidator;

public class RegisterRepository extends AuthRepository {

    private final MutableLiveData<Error> errorLiveData;

    public RegisterRepository(Application application) {
        super(application);
        errorLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<Error> getErrorLiveData() {
        return errorLiveData;
    }

    public void register(String email, String password) {
        if (!EmailValidator.getInstance().isValid(email)) {
            postError(Error.ERROR_INVALID_EMAIL_FORMAT);
        } else {
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnSuccessListener(this::handleSuccess)
                    .addOnFailureListener(this::handleFailure);
        }
    }

    private void handleSuccess(AuthResult authResult) {
        setUserLiveData(authResult.getUser());
    }

    private void handleFailure(Exception exception) {
        if (exception instanceof FirebaseAuthWeakPasswordException) {
            postError(Error.ERROR_WEAK_PASSWORD);
        } else if (exception instanceof FirebaseAuthInvalidCredentialsException) {
            postError(Error.ERROR_INVALID_EMAIL);
        } else if (exception instanceof FirebaseAuthUserCollisionException) {
            postError(Error.ERROR_ALREADY_EXISTS);
        } else if (exception instanceof FirebaseNetworkException) {
            setNetworkErrorLiveData(true);
        } else {
            postError(Error.ERROR_INVALID_CREDENTIAL);
        }
    }

    private void postError(Error error) {
        errorLiveData.postValue(error);
    }

}
