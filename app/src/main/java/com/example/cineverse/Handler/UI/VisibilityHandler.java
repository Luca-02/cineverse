package com.example.cineverse.Handler.UI;

import android.view.View;

/**
 * The VisibilityHandler class provides utility methods to handle the visibility of View objects in Android applications.
 */
public class VisibilityHandler {

    /**
     * Sets the visibility of the specified View to VISIBLE.
     *
     * @param view The View object to be set visible.
     */
    public static void setVisibleView(View view) {
        view.setVisibility(View.VISIBLE);
    }

    /**
     * Sets the visibility of the specified View to GONE.
     *
     * @param view The View object to be set gone.
     */
    public static void setGoneView(View view) {
        view.setVisibility(View.GONE);
    }

}
