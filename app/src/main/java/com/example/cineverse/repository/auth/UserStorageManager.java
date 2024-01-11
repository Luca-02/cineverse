package com.example.cineverse.repository.auth;

import android.content.Context;

import com.example.cineverse.data.model.User;
import com.example.cineverse.data.source.user.UserFirebaseSource;
import com.example.cineverse.data.source.user.UserLocalSource;
import com.example.cineverse.data.source.user.UserCallback;
import com.example.cineverse.service.firebase.UserFirebaseDatabaseService;
import com.google.firebase.auth.FirebaseUser;

/**
 * The {@link UserStorageManager} class serves as a higher-level abstraction for user-related operations,
 * combining functionality from Firebase, local storage, and user queries. It includes methods for
 * registering a user, authenticating a Google user, and logging in a user.
 */
public class UserStorageManager
        extends UserFirebaseDatabaseService {

    private final Context context;
    private final UserLocalSource localSource;
    private final UserFirebaseSource firebaseSource;
    private final UserCallback<User> userCallback;

    public UserStorageManager(
            Context context,
            UserLocalSource localSource,
            UserFirebaseSource firebaseSource,
            UserCallback<User> userCallback) {
        this.context = context;
        this.localSource = localSource;
        this.firebaseSource = firebaseSource;
        this.userCallback = userCallback;
    }

    /**
     * Registers a user by saving their information in Firebase and locally.
     *
     * @param firebaseUser The Firebase user object.
     * @param username The username to associate with the user.
     */
    public void register(FirebaseUser firebaseUser, String username) {
        if (userCallback != null) {
            User user = new User(firebaseUser, username);

            firebaseSource.saveUser(user, new UserCallback<Boolean>() {
                @Override
                public void onCallback(Boolean userSaved) {
                    handleUserSaveResult(userSaved, user);
                }

                @Override
                public void onNetworkUnavailable() {
                    userCallback.onNetworkUnavailable();
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
        if (userCallback != null) {
            firebaseSource.getUserFromUid(context, firebaseUser.getUid(), new UserCallback<User>() {
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
                    userCallback.onNetworkUnavailable();
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
        if (userCallback != null) {
            firebaseSource.getUserFromUid(context, uid, new UserCallback<User>() {
                @Override
                public void onCallback(User user) {
                    if (user != null) {
                        localSource.saveUser(user);
                    }
                    userCallback.onCallback(user);
                }

                @Override
                public void onNetworkUnavailable() {
                    userCallback.onNetworkUnavailable();
                }
            });
        }
    }

    private void handleUserSaveResult(Boolean userSaved, User user) {
        if (userSaved != null && userSaved) {
            localSource.saveUser(user);
            userCallback.onCallback(user);
        } else {
            userCallback.onCallback(null);
        }
    }

}
