package com.example.cineverse.data.source.user;

import android.content.Context;

import com.example.cineverse.data.model.User;
import com.example.cineverse.service.firebase.UserFirebaseDatabaseServices;
import com.google.firebase.auth.FirebaseUser;

/**
 * The {@link UserStorageManagerSource} class serves as a higher-level abstraction for user-related operations,
 * combining functionality from Firebase, local storage, and user queries. It includes methods for
 * registering a user, authenticating a Google user, and logging in a user.
 */
public class UserStorageManagerSource
        extends UserFirebaseDatabaseServices {

    private final Context context;
    private final UserLocalSource localSource;
    private final UserFirebaseSource firebaseSource;
    private Callback<User> callback;

    public UserStorageManagerSource(Context context, UserLocalSource localSource, UserFirebaseSource firebaseSource) {
        this.context = context;
        this.localSource = localSource;
        this.firebaseSource = firebaseSource;
    }

    public void setCallback(Callback<User> callback) {
        this.callback = callback;
    }

    /**
     * Registers a user by saving their information in Firebase and locally.
     *
     * @param firebaseUser The Firebase user object.
     * @param username The username to associate with the user.
     */
    public void register(FirebaseUser firebaseUser, String username) {
        if (callback != null) {
            User user = new User(firebaseUser, username);

            firebaseSource.saveUser(user, new Callback<Boolean>() {
                @Override
                public void onCallback(Boolean userSaved) {
                    handleUserSaveResult(userSaved, user);
                }

                @Override
                public void onNetworkUnavailable() {
                    callback.onNetworkUnavailable();
                }
            });
        }
    }

    /**
     * Authenticates a Google user. If the user is not registered, it proceeds to register them;
     * otherwise, it logs in the existing user.
     *
     * @param firebaseUser The Firebase user object.
     */
    public void googleAuth(FirebaseUser firebaseUser) {
        if (callback != null) {
            firebaseSource.getUserFromUid(firebaseUser.getUid(), context,
                    new Callback<User>() {
                        @Override
                        public void onCallback(User user) {
                            if (user == null) {
                                register(firebaseUser, null);
                            } else {
                                login(user.getUid());
                            }
                        }

                        @Override
                        public void onNetworkUnavailable() {
                            callback.onNetworkUnavailable();
                        }
                    });
        }
    }

    /**
     * Logs in a user by retrieving their information from Firebase and saving it locally.
     *
     * @param uid The UID of the user to log in.
     */
    public void login(String uid) {
        if (callback != null) {
            firebaseSource.getUserFromUid(uid, context,
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

    private void handleUserSaveResult(Boolean userSaved, User user) {
        if (userSaved != null && userSaved) {
            localSource.saveUser(user);
            callback.onCallback(user);
        } else {
            callback.onCallback(null);
        }
    }

}
