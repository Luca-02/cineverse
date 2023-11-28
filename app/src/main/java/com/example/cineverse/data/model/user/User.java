package com.example.cineverse.data.model.user;

import android.net.Uri;

import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * The {@link User} class represents a user with essential information such as UID, username,
 * email, and photo URL.
 */
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

    /**
     * Creates a standard username for the user. The generated username is prefixed with "user"
     * followed by a random sequence of digits.
     */
    public void createUsername() {
        String username = generateStandardUsername();
        setUsername(username);
    }

    /**
     * Generates a standard username by appending "user" with a random sequence of 15 numeric digits.
     *
     * @return The generated standard username.
     */
    private String generateStandardUsername() {
        int length = 15;

        StringBuilder stringBuilder = new StringBuilder("user");
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            int randomNumber = random.nextInt(10);
            stringBuilder.append(randomNumber);
        }

        return stringBuilder.toString();
    }

}
