package com.example.cineverse.repository;

import android.content.Context;

import com.example.cineverse.data.model.user.User;
import com.example.cineverse.data.storage.user.UserStorage;
import com.example.cineverse.utils.ServiceLocator;
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
        userStorage = ServiceLocator.getInstance().getUserStorage(context);
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
     * @param context The application {@link Context}.
     * @return {@link User} representing the currently authenticated user, or {@code null} if not authenticated.
     */
    protected User getCurrentLocalUser(Context context) {
        return userStorage.getLocalSource().getUser(context);
    }

    /**
     * Clears all user-related information, {@link FirebaseAuth#signOut signOut} from Firebase and deleting locally saved user.
     *
     * @param context The application {@link Context}.
     */
    protected void clearAllUser(Context context) {
        firebaseAuth.signOut();
        userStorage.getLocalSource().clearUser(context);
    }

    /**
     * Gets the currently authenticated user based on Firebase and local storage.
     *
     * @return The currently authenticated {@link User} object or {@code null} if not authenticated.
     */
    public User getCurrentUser() {
        // If getCurrentFirebaseUser() is NOT NULL and the User locally saved exist with equal
        // uid, then the user is already logged. If one of them is NULL or does not exist, or have different
        // uid, then is not already logged, and if FirebaseUser is NOT NULL, sign out from firebase, if
        // User locally saved exist delete it.

        FirebaseUser firebaseUser = getCurrentFirebaseUser();
        User localUser = getCurrentLocalUser(context);

        if (firebaseUser != null && localUser != null && firebaseUser.getUid().equals(localUser.getUid())) {
            return localUser;
        } else {
            clearAllUser(context);
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
