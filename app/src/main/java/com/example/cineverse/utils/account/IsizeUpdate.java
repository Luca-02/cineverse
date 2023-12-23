package com.example.cineverse.utils.account;

import android.content.Context;

public interface IsizeUpdate {

    public int calculateNewSize(float collapsePercent, int startSize, int endSize);

    public int calculateNewTextSize(float collapsePercent, int startTextSize, int endTextSize);

    public int dpToPx(Context context, int dp);
}
