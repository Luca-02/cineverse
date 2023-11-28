package com.example.cineverse.handler.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.cineverse.handler.callback.BackPressedHandler;

/**
 * The {@code NetworkHandler} class provides utility methods for checking network connectivity.
 * It includes a method to determine whether a network connection is available.
 */
public class NetworkHandler {

    /**
     * Private constructor to prevent instantiation of this utility class.
     * Throws an {@code IllegalStateException} if attempted to create an instance.
     */
    private NetworkHandler() {
        throw new IllegalStateException(BackPressedHandler.class.getSimpleName());
    }

    /**
     * Checks whether a network connection is available.
     *
     * @param context The context used to retrieve the {@code ConnectivityManager}.
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
