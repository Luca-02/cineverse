package com.example.cineverse.data.source.user;

import android.content.Context;

import com.example.cineverse.data.model.User;
import com.example.cineverse.service.firebase.FirebaseCallback;
import com.example.cineverse.service.firebase.UserFirebaseDatabaseService;
import com.google.firebase.auth.FirebaseUser;

/**
 * The {@link UserStorageManagerSource} class serves as a higher-level abstraction for user-related operations,
 * combining functionality from Firebase, local storage, and user queries. It includes methods for
 * registering a user, authenticating a Google user, and logging in a user.
 */
public class UserStorageManagerSource
        extends UserFirebaseDatabaseService {

    private final Context context;
    private final UserLocalSource localSource;
    private final UserFirebaseSource firebaseSource;
    private final FirebaseCallback<User> firebaseCallback;

    public UserStorageManagerSource(
            Context context,
            UserLocalSource localSource,
            UserFirebaseSource firebaseSource,
            FirebaseCallback<User> firebaseCallback) {
        this.context = context;
        this.localSource = localSource;
        this.firebaseSource = firebaseSource;
        this.firebaseCallback = firebaseCallback;
    }

    /**
     * Registers a user by saving their information in Firebase and locally.
     *
     * @param firebaseUser The Firebase user object.
     * @param username The username to associate with the user.
     */
    public void register(FirebaseUser firebaseUser, String username) {
        if (firebaseCallback != null) {
            User user = new User(firebaseUser, username);

            firebaseSource.saveUser(user, new FirebaseCallback<Boolean>() {
                @Override
                public void onCallback(Boolean userSaved) {
                    handleUserSaveResult(userSaved, user);
                }

                @Override
                public void onNetworkUnavailable() {
                    firebaseCallback.onNetworkUnavailable();
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
        if (firebaseCallback != null) {
            firebaseSource.getUserFromUid(firebaseUser.getUid(), context,
                    new FirebaseCallback<User>() {
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
                            firebaseCallback.onNetworkUnavailable();
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
        if (firebaseCallback != null) {
            firebaseSource.getUserFromUid(uid, context,
                    new FirebaseCallback<User>() {
                        @Override
                        public void onCallback(User user) {
                            if (user != null) {
                                localSource.saveUser(user);
                            }
                            firebaseCallback.onCallback(user);
                        }

                        @Override
                        public void onNetworkUnavailable() {
                            firebaseCallback.onNetworkUnavailable();
                        }
                    });
        }
    }

    private void handleUserSaveResult(Boolean userSaved, User user) {
        if (userSaved != null && userSaved) {
            localSource.saveUser(user);
            firebaseCallback.onCallback(user);
        } else {
            firebaseCallback.onCallback(null);
        }
    }

}
