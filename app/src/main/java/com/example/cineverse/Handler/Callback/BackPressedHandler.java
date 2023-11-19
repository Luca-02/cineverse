package com.example.cineverse.Handler.Callback;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;

public class BackPressedHandler {

    /**
     * Handles the onBackPressed callback to navigate between destinations or finish the activity.
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
