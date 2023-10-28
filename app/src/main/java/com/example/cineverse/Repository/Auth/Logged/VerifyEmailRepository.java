package com.example.cineverse.Repository.Auth.Logged;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.cineverse.Repository.Auth.LoggedRepository;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.FirebaseUser;

public class VerifyEmailRepository extends LoggedRepository {

    private final MutableLiveData<Boolean> emailSentLiveData;
    private final MutableLiveData<Boolean> emailVerifiedLiveData;

    public VerifyEmailRepository(Application application) {
        super(application);
        emailSentLiveData = new MutableLiveData<>();
        emailVerifiedLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<Boolean> getEmailSentLiveData() {
        return emailSentLiveData;
    }

    public MutableLiveData<Boolean> getEmailVerifiedLiveData() {
        return emailVerifiedLiveData;
    }

    public void sendEmailVerification() {
        FirebaseUser user = getCurrentUser();
        if (user != null) {
            user.reload()
                    .addOnSuccessListener(unused -> {
                        FirebaseUser reloadedUser = getCurrentUser();
                        if (reloadedUser != null) {
                            reloadedUser.sendEmailVerification()
                                    .addOnSuccessListener(unused1 -> emailSentLiveData.postValue(true))
                                    .addOnFailureListener(e -> emailSentLiveData.postValue(false));
                        } else {
                            emailSentLiveData.postValue(null);
                        }
                    })
                    .addOnFailureListener(e -> handleReloadFailure(e, emailSentLiveData));
        } else {
            emailSentLiveData.postValue(null);
        }
    }

    public void reloadUser() {
        final FirebaseUser user = getCurrentUser();
        if (user != null) {
            user.reload()
                    .addOnSuccessListener(unused -> {
                        emailVerifiedLiveData.postValue(isEmailVerified());
                    })
                    .addOnFailureListener(e -> handleReloadFailure(e, emailVerifiedLiveData));
        } else {
            emailVerifiedLiveData.postValue(null);
        }
    }

    private void handleReloadFailure(Exception exception, MutableLiveData<Boolean> liveData) {
        if (exception instanceof FirebaseNetworkException) {
            setNetworkErrorLiveData(true);
        } else {
            liveData.postValue(null);
        }
    }

}
