package com.example.cineverse.model;

import android.net.Uri;

import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;
import java.util.Map;

public class User {

    private String uid;
    private String username;
    private String email;
    private String photoUrl;

    public User() {}

    public User(FirebaseUser firebaseUser, String username) {
        this.uid = firebaseUser.getUid();
        this.username = username;
        this.email = firebaseUser.getEmail();
        Uri photoUri = firebaseUser.getPhotoUrl();
        if (photoUri != null) {
            this.photoUrl = photoUri.toString();
        } else {
            this.photoUrl = null;
        }
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public Map<String, Object> createUserMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("uid", uid);
        map.put("username", username);
        map.put("email", email);
        map.put("photoUrl", photoUrl);
        return map;
    }

}
