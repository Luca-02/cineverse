package com.example.cineverse.data.source.user;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.cineverse.data.model.user.User;
import com.example.cineverse.data.service.firebase.UserFirebaseDatabaseServices;
import com.example.cineverse.utils.network.NetworkUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

/**
 * The {@link UserFirebaseSource} class provides methods to interact with the Firebase Realtime Database
 * for user-related operations. It extends the functionality of {@link UserFirebaseDatabaseServices}.
 * It includes methods for saving a user, checking if a user is saved, and handling the transaction to
 * save a user's information.
 */
public class UserFirebaseSource extends UserFirebaseDatabaseServices {

    private final Context context;

    /**
     * Constructs a {@code UserFirebaseSource} object with the given context.
     *
     * @param context The context of the calling component.
     */
    public UserFirebaseSource(Context context) {
        this.context = context;
    }

    /**
     * Saves a user in the Firebase Realtime Database if the user is not already saved. It creates a
     * username if necessary and performs a transaction to save the user's information.
     *
     * @param user     The user to save.
     * @param callback The callback to handle the result.
     */
    public void saveUser(User user, Callback<Boolean> callback) {
        if (NetworkUtils.isNetworkAvailable(context)) {
            checkIfUserSaved(user, callback);
        } else {
            callback.onNetworkUnavailable();
        }
    }

    private void checkIfUserSaved(User user, Callback<Boolean> callback) {
        isUserSaved(user.getUid(), context, new Callback<Boolean>() {
            @Override
            public void onCallback(Boolean isUserSaved) {
                handleUserSavedResult(isUserSaved, user, callback);
            }

            @Override
            public void onNetworkUnavailable() {
                callback.onNetworkUnavailable();
            }
        });
    }

    private void handleUserSavedResult(Boolean isUserSaved, User user, Callback<Boolean> callback) {
        if (isUserSaved == null) {
            callback.onCallback(null);
        } else {
            if (!isUserSaved) {
                saveUserNotSaved(user, callback);
            } else {
                callback.onCallback(false);
            }
        }
    }

    /**
     * Saves a user in the Firebase Realtime Database if the user is not already saved. It creates a
     * username if necessary and performs a transaction to save the user's information.
     *
     * @param user     The user to save.
     * @param callback The callback to handle the result.
     */
    private void saveUserNotSaved(User user, Callback<Boolean> callback) {
        boolean usernameIsNull = (user.getUsername() == null);
        final boolean[] saved = {false};

        if (usernameIsNull) {
            user.createUsername();
        }

        usernamesDatabase.child(user.getUsername()).runTransaction(new Transaction.Handler() {
            @NonNull
            @Override
            public Transaction.Result doTransaction(@NonNull MutableData currentData) {
                // If username was null and already exist, it means that
                // username with this username already exist
                if (usernameIsNull && currentData.getValue() != null) {
                    return Transaction.abort();
                }

                // Check if username do not exists
                if (currentData.getValue() == null) {
                    currentData.setValue(user.getUid());
                    usersDatabase.child(user.getUid()).setValue(user);
                    saved[0] = true;
                }
                return Transaction.success(currentData);
            }

            @Override
            public void onComplete(@Nullable DatabaseError error,
                                   boolean committed,
                                   @Nullable DataSnapshot currentData) {
                if (committed && error == null) {
                    callback.onCallback(saved[0]);
                } else {
                    callback.onCallback(null);
                }
            }
        });
    }

    /**
     * Checks if a username is saved in the usernames database.
     *
     * @param username The username to check.
     * @param context The context used to check network availability.
     * @param callback The callback to handle the result.
     */
    public void isUsernameSaved(String username, Context context, final Callback<Boolean> callback) {
        if (NetworkUtils.isNetworkAvailable(context)) {
            Query query = usernamesDatabase.child(username);

            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    boolean exists = dataSnapshot.exists();
                    callback.onCallback(exists);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    callback.onCallback(null);
                }
            });
        } else {
            callback.onNetworkUnavailable();
        }
    }

    /**
     * Checks if a user with a specific UID is saved in the users database.
     *
     * @param uid The UID to check.
     * @param context The context used to check network availability.
     * @param callback The callback to handle the result.
     */
    public void isUserSaved(String uid, Context context, final Callback<Boolean> callback) {
        if (NetworkUtils.isNetworkAvailable(context)) {
            Query query = usersDatabase.child(uid);

            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    boolean exists = dataSnapshot.exists();
                    callback.onCallback(exists);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    callback.onCallback(null);
                }
            });
        } else {
            callback.onNetworkUnavailable();
        }
    }

    /**
     * Retrieves a {@code User} object from the users database using the UID.
     *
     * @param uid The UID of the user.
     * @param context The context used to check network availability.
     * @param callback The callback to handle the result.
     */
    public void getUserFromUid(String uid, Context context, final Callback<User> callback) {
        if (NetworkUtils.isNetworkAvailable(context)) {
            Query query = usersDatabase.child(uid);

            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        User user = dataSnapshot.getValue(User.class);
                        callback.onCallback(user);
                    } else {
                        callback.onCallback(null);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    callback.onCallback(null);
                }
            });
        } else {
            callback.onNetworkUnavailable();
        }

    }

    /**
     * Retrieves the email associated with a username from the usernames database.
     *
     * @param username The username to retrieve the email for.
     * @param context The context used to check network availability.
     * @param callback The callback to handle the result.
     */
    public void getEmailFromUsername(String username, Context context, final Callback<String> callback) {
        if (NetworkUtils.isNetworkAvailable(context)) {
            Query uidQuery = usernamesDatabase.child(username);

            uidQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String uid = dataSnapshot.getValue(String.class);
                        if (uid != null) {
                            getEmailFromUid(uid, context, callback);
                        } else {
                            callback.onCallback(null);
                        }
                    } else {
                        callback.onCallback(null);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    callback.onCallback(null);
                }
            });
        } else {
            callback.onNetworkUnavailable();
        }

    }

    /**
     * Retrieves the email associated with a UID from the users database.
     *
     * @param uid The UID to retrieve the email for.
     * @param context The context used to check network availability.
     * @param callback The callback to handle the result.
     */
    public void getEmailFromUid(String uid, Context context, final Callback<String> callback) {
        if (NetworkUtils.isNetworkAvailable(context)) {
            Query emailQuery = usersDatabase.child(uid).child("email");

            emailQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String email = dataSnapshot.getValue(String.class);
                        callback.onCallback(email);
                    } else {
                        callback.onCallback(null);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    callback.onCallback(null);
                }
            });
        } else {
            callback.onNetworkUnavailable();
        }
    }

}
