package com.example.cineverse.Repository.Auth;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.example.cineverse.Repository.AuthRepository;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

import org.apache.commons.validator.routines.EmailValidator;

public class LoginRepository extends AuthRepository {

    private final MutableLiveData<Error> errorLiveData;

    public LoginRepository(Application application) {
        super(application);
        errorLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<Error> getErrorLiveData() {
        return errorLiveData;
    }

    public void login(String email, String password) {
        if (!EmailValidator.getInstance().isValid(email)) {
            postError(Error.ERROR_INVALID_EMAIL_FORMAT);
        } else {
            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnSuccessListener(this::handleSuccess)
                    .addOnFailureListener(this::handleFailure);
        }
    }

    private void handleSuccess(AuthResult authResult) {
        setUserLiveData(authResult.getUser());
    }

    private void handleFailure(Exception exception) {
        if (exception instanceof FirebaseAuthInvalidUserException) {
            postError(Error.ERROR_NOT_FOUND_DISABLED);
        } else if (exception instanceof FirebaseNetworkException) {
            setNetworkErrorLiveData(true);
        } else {
            postError(Error.ERROR_WRONG_PASSWORD);
        }
    }

    private void postError(Error error) {
        errorLiveData.postValue(error);
    }

}
