package com.example.cineverse.handler.callback;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;

/**
 * The {@code BackPressedHandler} class provides utility methods to handle the {@code onBackPressed} callback in Android applications.
 */
public class BackPressedHandler {

    private BackPressedHandler() {
        throw new IllegalStateException(BackPressedHandler.class.getSimpleName());
    }

    /**
     * Handles the {@code onBackPressed} callback to navigate between destinations or finish the activity.
     *
     * @param activity      The {@code FragmentActivity} associated with the {@code onBackPressed} callback.
     * @param navController The {@code NavController} used for navigation between destinations.
     */
    public static void handleOnBackPressedCallback(FragmentActivity activity, NavController navController) {
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (navController != null) {
                    NavDestination navDestination = navController.getCurrentDestination();
                    if (navDestination != null) {
                        int startDestination = navController.getGraph().getStartDestinationId();
                        int currentDestination = navController.getCurrentDestination().getId();
                        if (startDestination == currentDestination) {
                            activity.finish();
                        } else {
                            navController.popBackStack();
                        }
                    }
                }
            }
        };
        activity.getOnBackPressedDispatcher().addCallback(callback);
    }

}
