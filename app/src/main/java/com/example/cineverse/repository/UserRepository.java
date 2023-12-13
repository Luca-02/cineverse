package com.example.cineverse.repository;

import android.content.Context;

import com.example.cineverse.data.model.User;
import com.example.cineverse.data.storage.user.UserStorage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * The {@link UserRepository} class is a base class providing authentication-related functionality
 * using Firebase authentication services. It encapsulates methods for handling user authentication,
 * managing user data, and checking network connectivity. This class is meant to be extended for specific
 * authentication methods such as email/password, Google Sign-In, etc.
 * It provides {@link NetworkCallback} to communicate the network error to the caller.
 */
public class UserRepository {

    /**
     * Firebase authentication instance.
     */
    protected static final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    protected final Context context;
    protected final UserStorage userStorage;

    /**
     * Constructs an {@link UserRepository} object with the given application {@link Context}.
     *
     * @param context The application {@link Context}.
     */
    public UserRepository(Context context) {
        this.context = context;
        userStorage = new UserStorage(context);
    }

    /**
     * Returns the currently authenticated {@link FirebaseUser} object.
     *
     * @return {@link FirebaseUser} representing the currently authenticated user, or {@code null} if not authenticated.
     */
    protected FirebaseUser getCurrentFirebaseUser() {
        return firebaseAuth.getCurrentUser();
    }

    /**
     * Returns the currently authenticated {@link User} object saved locally.
     *
     * @return {@link User} representing the currently authenticated user, or {@code null} if not authenticated.
     */
    protected User getCurrentLocalUser() {
        return userStorage.getLocalSource().getUser();
    }

    /**
     * Clears all user-related information, {@link FirebaseAuth#signOut signOut} from Firebase and deleting locally saved user.
     */
    protected void clearAllUser() {
        firebaseAuth.signOut();
        userStorage.getLocalSource().clearUser();
    }

    /**
     * Gets the currently authenticated user based on Firebase and local storage.
     * If {@link #getCurrentFirebaseUser()} is NOT {@code null} and the {@link User} locally saved exist with equal
     * uid, then the user is already logged.
     * <pre></pre>
     * If one of them is {@code null} or does not exist, or have different
     * uid, then is not already logged, so:
     * <ul>
     * <li>if {@link FirebaseUser} is NOT {@code null}, sign out from Firebase.</li>
     * <li>if {@link User} locally saved exist delete it.</li>
     * </ul>
     *
     *
     * @return The currently authenticated {@link User} object or {@code null} if not already authenticated.
     */
    public User getCurrentUser() {
        FirebaseUser firebaseUser = getCurrentFirebaseUser();
        User localUser = getCurrentLocalUser();

        if (firebaseUser != null && localUser != null && firebaseUser.getUid().equals(localUser.getUid())) {
            return localUser;
        } else {
            clearAllUser();
            return null;
        }
    }

    /**
     * Checks if the currently authenticated user's email is verified.
     *
     * @return {@code true} if the user's email is verified, {@code false} otherwise.
     */
    public boolean isEmailVerified() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        return user != null && user.isEmailVerified();
    }

    /**
     * Callback interface for handling network-related errors.
     */
    public interface NetworkCallback {
        /**
         * Invoked when there is a network error
         */
        void onNetworkError();
    }

}
