package com.example.cineverse.data.model;

import androidx.lifecycle.MutableLiveData;

public class CurrentUser {

    private MutableLiveData<User> currentUserMutableLiveData;
    private static CurrentUser instance = null;

    private CurrentUser() {}

    public static synchronized CurrentUser getInstance() {
        if (instance == null) {
            instance = new CurrentUser();
        }
        return instance;
    }

    public synchronized void handleCurrentUser(User user) {
        User currentUser = getCurrentUserMutableLiveData().getValue();
        if (currentUser != null && user != null && !currentUser.equalsContent(user)) {
            getCurrentUserMutableLiveData().postValue(user);
        }
    }

    public synchronized MutableLiveData<User> getCurrentUserMutableLiveData() {
        if (currentUserMutableLiveData == null) {
            currentUserMutableLiveData = new MutableLiveData<>();
        }
        return currentUserMutableLiveData;
    }

    public void clearCurrentUserMutableLiveData() {
        currentUserMutableLiveData = null;
    }

}
