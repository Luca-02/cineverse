package com.example.cineverse.data.storage.user;

import android.content.Context;

import com.example.cineverse.data.query.user.UserFirebaseQuery;
import com.example.cineverse.data.source.user.UserFirebaseSource;
import com.example.cineverse.data.source.user.UserLocalSource;
import com.example.cineverse.data.model.user.User;
import com.example.cineverse.data.service.firebase.UserFirebaseDatabaseServices;
import com.google.firebase.auth.FirebaseUser;

/**
 * The {@code UserStorage} class serves as a higher-level abstraction for user-related operations,
 * combining functionality from Firebase, local storage, and user queries. It includes methods for
 * registering a user, authenticating a Google user, and logging in a user.
 */
public class UserStorage extends UserFirebaseDatabaseServices {

    private final Context context;
    private final UserFirebaseSource firebaseSource;
    private final UserLocalSource localSource;

    public UserStorage(Context context) {
        this.context = context;
        firebaseSource = new UserFirebaseSource();
        localSource = new UserLocalSource(context);
    }

    /**
     * Registers a user by saving their information in Firebase and locally.
     *
     * @param firebaseUser The Firebase user object.
     * @param username The username to associate with the user.
     * @param callback The callback to handle the result.
     */
    public void registerUser(FirebaseUser firebaseUser,
                             String username,
                             Callback<User> callback) {
        User user = new User(firebaseUser, username);

        firebaseSource.saveUser(user, context,
                new Callback<Boolean>() {
                    @Override
                    public void onCallback(Boolean userSaved) {
                        if (userSaved != null && userSaved) {
                            localSource.saveUser(user);
                            callback.onCallback(user);
                        } else {
                            callback.onCallback(null);
                        }
                    }

                    @Override
                    public void onNetworkUnavailable() {
                        callback.onNetworkUnavailable();
                    }
                });
    }

    /**
     * Authenticates a Google user. If the user is not registered, it proceeds to register them;
     * otherwise, it logs in the existing user.
     *
     * @param firebaseUser The Firebase user object.
     * @param callback The callback to handle the result.
     */
    public void authGoogleUser(FirebaseUser firebaseUser,
                               Callback<User> callback) {
        UserFirebaseQuery.getUserFromUid(firebaseUser.getUid(), context,
                new Callback<User>() {
                    @Override
                    public void onCallback(User user) {
                        if (user == null) {
                            registerUser(firebaseUser, null, callback);
                        } else {
                            loginUser(user.getUid(), callback);
                        }
                    }

                    @Override
                    public void onNetworkUnavailable() {
                        callback.onNetworkUnavailable();
                    }
                });
    }

    /**
     * Logs in a user by retrieving their information from Firebase and saving it locally.
     *
     * @param uid The UID of the user to log in.
     * @param callback The callback to handle the result.
     */
    public void loginUser(String uid,
                          Callback<User> callback) {
        UserFirebaseQuery.getUserFromUid(uid, context,
                new Callback<User>() {
                    @Override
                    public void onCallback(User user) {
                        if (user != null) {
                            localSource.saveUser(user);
                        }
                        callback.onCallback(user);
                    }

                    @Override
                    public void onNetworkUnavailable() {
                        callback.onNetworkUnavailable();
                    }
                });
    }

}