package com.example.cineverse.utils.account;

import android.content.Context;

public abstract class AbstractSizeUpdate {

    public int calculateNewSize(float collapsePercent, int startSize, int endSize) {
        // Calculate the size dynamically based on the collapse percentage
        return (int) (startSize + (endSize - startSize) * collapsePercent);
    }

    public int calculateNewTextSize(float collapsePercent, int startTextSize, int endTextSize) {
        // Calculate the text size dynamically based on the collapse percentage
        return (int) (startTextSize + (endTextSize - startTextSize) * collapsePercent);
    }

    public int dpToPx(Context context, int dp) {
        // Convert dp to pixels
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}
