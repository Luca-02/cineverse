package com.example.cineverse.data.source.user;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.cineverse.data.model.User;
import com.example.cineverse.repository.UserRepository;
import com.example.cineverse.utils.constant.GlobalConstant;

import org.jetbrains.annotations.NotNull;

public class SynchronizeLocalUserDataSource {

    public static final String PREF_NAME = "LastSyncTimePreferences";

    public static final String LAST_SYNC_TIME_KEY = "lastSyncTime";

    private final Context context;
    private final UserFirebaseSource firebaseSource;
    private final UserLocalSource localSource;
    private UserRepository.SynchronizeLocalUserCallback synchronizeLocalUserCallback;

    public SynchronizeLocalUserDataSource(Context context, UserFirebaseSource firebaseSource, UserLocalSource localSource) {
        this.context = context;
        this.firebaseSource = firebaseSource;
        this.localSource = localSource;
    }

    public void setSynchronizeLocalUserCallback(UserRepository.SynchronizeLocalUserCallback synchronizeLocalUserCallback) {
        this.synchronizeLocalUserCallback = synchronizeLocalUserCallback;
    }

    /**
     * Every 24h synchronize the local user with the firebase user, because the user is able to change the username.
     */
    public void synchronizeLocalUserIf24HoursPassed(@NotNull User currentUser) {
        // Retrieve the last execution date
        long lastExecution = readLastLocalUserSynchronizationTimestamp();

        // Get the current date
        long currentDate = System.currentTimeMillis();

        // Calculate the time difference in milliseconds between the two dates
        long timeDifference = currentDate - lastExecution;

        // If 24 hours or more have passed, execute the method and update the last execution date
        if (timeDifference >= 24 * 60 * 60 * 1000) {
            Log.d(GlobalConstant.TAG, "synchronized");
            synchronizeLocalUser(currentUser);
            saveLocalUserSynchronizationTimestamp();
        } else {
            Log.d(GlobalConstant.TAG, "not synchronized");
            synchronizeLocalUserCallback.onSynchronizedLocalUser(currentUser);
        }
    }

    private void synchronizeLocalUser(@NotNull User currentUser) {
        firebaseSource.getUserFromUid(context, currentUser.getUid(), new UserCallback<User>() {
            @Override
            public void onCallback(User user) {
                localSource.updateUser(user);
                if (user != null) {
                    synchronizeLocalUserCallback.onSynchronizedLocalUser(user);
                } else {
                    synchronizeLocalUserCallback.onSynchronizedLocalUser(currentUser);
                }
            }

            @Override
            public void onNetworkUnavailable() {
                synchronizeLocalUserCallback.onSynchronizedLocalUser(currentUser);
            }
        });
    }

    private long readLastLocalUserSynchronizationTimestamp() {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return preferences.getLong(LAST_SYNC_TIME_KEY, 0);
    }

    private void saveLocalUserSynchronizationTimestamp() {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(LAST_SYNC_TIME_KEY, System.currentTimeMillis());
        editor.apply();
    }

    public static void clearLocalUserSynchronizationTimestamp(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(LAST_SYNC_TIME_KEY);
        editor.apply();
    }

}
