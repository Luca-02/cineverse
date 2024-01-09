package com.example.cineverse.data.model.review;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.cineverse.data.model.User;

public class UserReview implements Parcelable {

    private User user;
    private Review review;

    public UserReview(User user, Review review) {
        this.user = user;
        this.review = review;
    }

    public UserReview(String userUid, Review review) {
        this.user = new User(userUid);
        this.review = review;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.user, flags);
        dest.writeParcelable(this.review, flags);
    }

    protected UserReview(Parcel in) {
        this.user = in.readParcelable(User.class.getClassLoader());
        this.review = in.readParcelable(Review.class.getClassLoader());
    }

    public static final Creator<UserReview> CREATOR = new Creator<UserReview>() {
        @Override
        public UserReview createFromParcel(Parcel source) {
            return new UserReview(source);
        }

        @Override
        public UserReview[] newArray(int size) {
            return new UserReview[size];
        }
    };

}
