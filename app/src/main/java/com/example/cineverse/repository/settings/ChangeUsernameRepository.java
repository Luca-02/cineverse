package com.example.cineverse.repository.settings;

import android.content.Context;
import android.util.Log;

import com.example.cineverse.data.model.User;
import com.example.cineverse.repository.auth.logged.VerifiedAccountRepository;
import com.example.cineverse.service.NetworkCallback;
import com.google.firebase.Firebase;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChangeUsernameRepository extends VerifiedAccountRepository {
    private String uid;
    private UsernameChangeCallback usernameChangeCallback;
    private DatabaseReference userRef;

    private DatabaseReference usernamesRef = FirebaseDatabase.getInstance()
            .getReference().child("usernames");

    /**
     * Constructs a {@link VerifiedAccountRepository} object with the given application {@link Context}.
     *
     * @param context The application {@link Context} of the calling component.
     */
    public ChangeUsernameRepository(Context context) {
        super(context);
    }

    public void setUsernameChangeCallback(UsernameChangeCallback usernameChangeCallback){
        this.usernameChangeCallback = usernameChangeCallback;
    }

    public void changeUserReferences(String newUsername){
        if (getCurrentFirebaseUser() != null) {
            uid = getCurrentFirebaseUser().getUid();
            userRef = FirebaseDatabase.getInstance().getReference().child("users")
                    .child(uid).child("username");

            userRef.setValue(newUsername)
                    .addOnSuccessListener(aVoid -> usernameChangeCallback.isSuccess(true))
                    .addOnFailureListener(this::handleFailureChanging);
        }
    }

    public void changeUsernamesReferences(String last_username, String newUsername){
        if(getCurrentFirebaseUser() != null){
            usernamesRef.child(last_username).removeValue()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            usernamesRef.child(newUsername).setValue(uid)
                                    .addOnSuccessListener(aVoid -> usernameChangeCallback.isSuccess(true))
                                    .addOnFailureListener(this::handleFailureChanging);
                        } else {
                           usernameChangeCallback.isSuccess(false);
                        }
                    });
        }
    }

    /**
     * Manage the Failure on Changing Username
     * @param exception
     */
    private void handleFailureChanging (Exception exception) {
        if (exception instanceof FirebaseNetworkException) {
            usernameChangeCallback.onNetworkUnavailable();
        } else {
            usernameChangeCallback.isSuccess(null);
        }
    }

    @Override
    protected FirebaseUser getCurrentFirebaseUser() {
        return super.getCurrentFirebaseUser();
    }

    public interface UsernameChangeCallback extends NetworkCallback {
        /**
         * Invoked when the change is success.
         * @param isChanged
         */
        void isSuccess(Boolean isChanged);
    }
}
