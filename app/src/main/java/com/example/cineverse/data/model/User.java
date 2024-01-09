package com.example.cineverse.data.model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.auth.FirebaseUser;

import java.util.Random;

/**
 * The {@link User} class represents a user with essential information such as UID, username,
 * email, and photo URL.
 */
public class User implements Parcelable {

    private String uid;
    private String username;
    private String email;
    private String photoUrl;

    public User() {}

    public User(String uid) {
        this.uid = uid;
    }

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

    public User(String uid, String username, String email, String photoUrl) {
        this.uid = uid;
        this.username = username;
        this.email = email;
        this.photoUrl = photoUrl;
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.uid);
        dest.writeString(this.username);
        dest.writeString(this.email);
        dest.writeString(this.photoUrl);
    }

    protected User(Parcel in) {
        this.uid = in.readString();
        this.username = in.readString();
        this.email = in.readString();
        this.photoUrl = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

}
