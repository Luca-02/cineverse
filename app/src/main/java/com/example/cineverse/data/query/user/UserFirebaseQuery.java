package com.example.cineverse.data.query.user;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.cineverse.data.model.user.User;
import com.example.cineverse.data.service.firebase.UserFirebaseDatabaseServices;
import com.example.cineverse.handler.network.NetworkHandler;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * The {@link UserFirebaseQuery} class provides methods for querying user-related data from
 * Firebase Realtime Database. It extends the functionality of {@link UserFirebaseDatabaseServices}.
 * It includes methods to check if a username or user with a specific UID is saved in the database,
 * retrieve user information from UID, and obtain the email associated with a username or UID.
 */
public class UserFirebaseQuery
        extends UserFirebaseDatabaseServices {

    /**
     * Checks if a username is saved in the usernames database.
     *
     * @param username The username to check.
     * @param context The context used to check network availability.
     * @param callback The callback to handle the result.
     */
    public static void isUsernameSaved(String username, Context context, final Callback<Boolean> callback) {
        if (NetworkHandler.isNetworkAvailable(context)) {
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
    public static void isUserSaved(String uid, Context context, final Callback<Boolean> callback) {
        if (NetworkHandler.isNetworkAvailable(context)) {
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
    public static void getUserFromUid(String uid, Context context, final Callback<User> callback) {
        if (NetworkHandler.isNetworkAvailable(context)) {
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
    public static void getEmailFromUsername(String username, Context context, final Callback<String> callback) {
        if (NetworkHandler.isNetworkAvailable(context)) {
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
    public static void getEmailFromUid(String uid, Context context, final Callback<String> callback) {
        if (NetworkHandler.isNetworkAvailable(context)) {
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
