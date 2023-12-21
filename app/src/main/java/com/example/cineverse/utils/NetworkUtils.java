package com.example.cineverse.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * The {@link NetworkUtils} class provides utility methods for checking network connectivity.
 * It includes a method to determine whether a network connection is available.
 */
public class NetworkUtils {

    /**
     * Private constructor to prevent instantiation of this utility class.
     * Throws an {@link IllegalStateException} if attempted to create an instance.
     */
    private NetworkUtils() {
        throw new IllegalStateException(NetworkUtils.class.getSimpleName());
    }

    /**
     * Checks whether a network connection is available.
     *
     * @param context The context used to retrieve the {@link ConnectivityManager}.
     * @return {@code true} if a network connection is available, {@code false} otherwise.
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo =
                connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

}
