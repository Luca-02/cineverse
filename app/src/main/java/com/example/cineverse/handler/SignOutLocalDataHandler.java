package com.example.cineverse.handler;

import android.content.Context;

import com.example.cineverse.data.model.CurrentUser;
import com.example.cineverse.data.source.search.SearchHistoryLocalDataSource;
import com.example.cineverse.theme.ThemeModeController;
import com.example.cineverse.data.source.user.SynchronizeLocalUserDataSource;

public class SignOutLocalDataHandler {

    public static void handleDataOnSignOut(Context context) {
        ThemeModeController.clearThemeMode(context);
        SearchHistoryLocalDataSource.clearHistory(context);
        SynchronizeLocalUserDataSource.clearLocalUserSynchronizationTimestamp(context);
        CurrentUser.getInstance().clearCurrentUserMutableLiveData();
    }

}