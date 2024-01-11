package com.example.cineverse.data.source.user;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.cineverse.data.model.User;
import com.example.cineverse.service.firebase.UserFirebaseDatabaseService;
import com.example.cineverse.utils.NetworkUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

/**
 * The {@link UserFirebaseSource} class provides methods to interact with the Firebase Realtime Database
 * for user-related operations. It extends the functionality of {@link UserFirebaseDatabaseService}.
 * It includes methods for saving a user, checking if a user is saved, and handling the transaction to
 * save a user's information.
 */
public class UserFirebaseSource extends UserFirebaseDatabaseService {

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
     * @param userCallback The callback to handle the result.
     */
    public void saveUser(User user, UserCallback<Boolean> userCallback) {
        if (NetworkUtils.isNetworkAvailable(context)) {
            checkIfUserSaved(user, userCallback);
        } else {
            userCallback.onNetworkUnavailable();
        }
    }

    private void checkIfUserSaved(User user, UserCallback<Boolean> userCallback) {
        isUserSaved(context, user.getUid(), new UserCallback<Boolean>() {
            @Override
            public void onCallback(Boolean isUserSaved) {
                handleUserSavedResult(isUserSaved, user, userCallback);
            }

            @Override
            public void onNetworkUnavailable() {
                userCallback.onNetworkUnavailable();
            }
        });
    }

    private void handleUserSavedResult(Boolean isUserSaved, User user, UserCallback<Boolean> userCallback) {
        if (isUserSaved == null) {
            userCallback.onCallback(null);
        } else {
            if (!isUserSaved) {
                saveUserNotSaved(user, userCallback);
            } else {
                userCallback.onCallback(false);
            }
        }
    }

    /**
     * Saves a user in the Firebase Realtime Database if the user is not already saved. It creates a
     * username if necessary and performs a transaction to save the user's information.
     *
     * @param user     The user to save.
     * @param userCallback The callback to handle the result.
     */
    private void saveUserNotSaved(User user, UserCallback<Boolean> userCallback) {
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
                    userCallback.onCallback(saved[0]);
                } else {
                    userCallback.onCallback(null);
                }
            }
        });
    }

    /**
     * Checks if a username is saved in the usernames database.
     *
     * @param context The context used to check network availability.
     * @param username The username to check.
     * @param userCallback The callback to handle the result.
     */
    public void isUsernameSaved(Context context, String username, final UserCallback<Boolean> userCallback) {
        if (NetworkUtils.isNetworkAvailable(context)) {
            Query query = usernamesDatabase.child(username);

            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    boolean exists = dataSnapshot.exists();
                    userCallback.onCallback(exists);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    userCallback.onCallback(null);
                }
            });
        } else {
            userCallback.onNetworkUnavailable();
        }
    }

    /**
     * Checks if a user with a specific UID is saved in the users database.
     *
     * @param context The context used to check network availability.
     * @param uid The UID to check.
     * @param userCallback The callback to handle the result.
     */
    public void isUserSaved(Context context, String uid, final UserCallback<Boolean> userCallback) {
        if (NetworkUtils.isNetworkAvailable(context)) {
            Query query = usersDatabase.child(uid);

            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    boolean exists = dataSnapshot.exists();
                    userCallback.onCallback(exists);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    userCallback.onCallback(null);
                }
            });
        } else {
            userCallback.onNetworkUnavailable();
        }
    }

    /**
     * Retrieves a {@code User} object from the users database using the UID.
     *
     * @param context The context used to check network availability.
     * @param uid The UID of the user.
     * @param userCallback The callback to handle the result.
     */
    public void getUserFromUid(Context context, String uid, final UserCallback<User> userCallback) {
        if (NetworkUtils.isNetworkAvailable(context)) {
            Query query = usersDatabase.child(uid);

            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        User user = dataSnapshot.getValue(User.class);
                        userCallback.onCallback(user);
                    } else {
                        userCallback.onCallback(null);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    userCallback.onCallback(null);
                }
            });
        } else {
            userCallback.onNetworkUnavailable();
        }
    }

    /**
     * Retrieves the email associated with a username from the usernames database.
     *
     * @param context The context used to check network availability.
     * @param username The username to retrieve the email for.
     * @param userCallback The callback to handle the result.
     */
    public void getEmailFromUsername(Context context, String username, final UserCallback<String> userCallback) {
        if (NetworkUtils.isNetworkAvailable(context)) {
            Query uidQuery = usernamesDatabase.child(username);

            uidQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String uid = dataSnapshot.getValue(String.class);
                        if (uid != null) {
                            getEmailFromUid(context, uid, userCallback);
                        } else {
                            userCallback.onCallback(null);
                        }
                    } else {
                        userCallback.onCallback(null);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    userCallback.onCallback(null);
                }
            });
        } else {
            userCallback.onNetworkUnavailable();
        }
    }

    /**
     * Retrieves the email associated with a UID from the users database.
     *
     * @param context The context used to check network availability.
     * @param uid The UID to retrieve the email for.
     * @param userCallback The callback to handle the result.
     */
    public void getEmailFromUid(Context context, String uid, final UserCallback<String> userCallback) {
        if (NetworkUtils.isNetworkAvailable(context)) {
            Query emailQuery = usersDatabase.child(uid).child("email");

            emailQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String email = dataSnapshot.getValue(String.class);
                        userCallback.onCallback(email);
                    } else {
                        userCallback.onCallback(null);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    userCallback.onCallback(null);
                }
            });
        } else {
            userCallback.onNetworkUnavailable();
        }
    }

}
