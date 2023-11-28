package com.example.cineverse.repository.classes;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.example.cineverse.data.model.auth.LocalAuth;
import com.example.cineverse.data.model.user.User;
import com.example.cineverse.data.storage.user.UserStorage;
import com.example.cineverse.repository.interfaces.IUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * The {@link AbstractUserRepository} class is a base class providing authentication-related functionality
 * using Firebase authentication services. It encapsulates methods for handling user authentication,
 * managing user data, and checking network connectivity. This class is meant to be extended for specific
 * authentication methods such as email/password, Google Sign-In, etc.
 */
public abstract class AbstractUserRepository
        extends AbstractNetworkRepository
        implements IUser {

    /**
     * Firebase authentication instance.
     */
    protected static final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    /**
     * Local authentication instance.
     */
    protected static final LocalAuth localAuth = LocalAuth.getInstance();

    protected final Context context;
    protected final UserStorage userStorage;

    private final MutableLiveData<User> userLiveData;

    /**
     * Constructs an {@link AbstractUserRepository} object with the given application {@link Context}.
     * Initializes {@link MutableLiveData} objects for user data and network connectivity.
     *
     * @param context The application {@link Context}.
     */
    public AbstractUserRepository(Context context) {
        this.context = context;
        userLiveData = new MutableLiveData<>();
        userStorage = new UserStorage(context);

        // Check if there is a currently authenticated user and update userLiveData if available.
        User currentUser = getCurrentUser(context);
        if (currentUser != null) {
            userLiveData.postValue(currentUser);
        }
    }

    public MutableLiveData<User> getUserLiveData() {
        return userLiveData;
    }

    public void setUserLiveData(User user) {
        userLiveData.postValue(user);
    }

    /**
     * Gets the currently authenticated user based on Firebase and local storage.
     *
     * @param context The application {@link Context}.
     * @return The currently authenticated {@link User} object or {@code null} if not authenticated.
     */
    public static User getCurrentUser(Context context) {
        /*
         * If getCurrentFirebaseUser() is NOT NULL and the User locally saved exist with equal
         * uid, then the user is already logged.
         * If one of them is NULL or does not exist, or have different uid, then is not already
         * logged, and if FirebaseUser is NOT NULL, sign out from firebase, if User locally saved
         * exist delete it.
         */

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
     * Returns the currently authenticated {@link FirebaseUser} object.
     *
     * @return {@link FirebaseUser} representing the currently authenticated user, or {@code null} if not authenticated.
     */
    public static FirebaseUser getCurrentFirebaseUser() {
        return firebaseAuth.getCurrentUser();
    }

    /**
     * Returns the currently authenticated {@link User} object saved locally.
     *
     * @param context The application {@link Context}.
     * @return {@link User} representing the currently authenticated user, or {@code null} if not authenticated.
     */
    public static User getCurrentLocalUser(Context context) {
        return localAuth.getUser(context);
    }

    /**
     * Checks if the currently authenticated user's email is verified.
     *
     * @return {@code true} if the user's email is verified, {@code false} otherwise.
     */
    public static boolean isEmailVerified() {
        FirebaseUser user = getCurrentFirebaseUser();
        return user != null && user.isEmailVerified();
    }

    /**
     * Clears all user-related information, {@link FirebaseAuth#signOut signOut} from Firebase and deleting locally saved user.
     *
     * @param context The application {@link Context}.
     */
    public static void clearAllUser(Context context) {
        firebaseAuth.signOut();
        localAuth.clearUser(context);
    }

}
