package com.example.cineverse.data.model.review;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.cineverse.data.model.User;

import java.util.Objects;

public class UserReview implements Parcelable {

    private User user;
    private Review review;
    private long likeCount;
    private boolean userLikeReview;

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

    public long getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(long likeCount) {
        this.likeCount = likeCount;
    }

    public boolean isUserLikeReview() {
        return userLikeReview;
    }

    public void setUserLikeReview(boolean userLikeReview) {
        this.userLikeReview = userLikeReview;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserReview)) return false;
        UserReview that = (UserReview) o;
        return Objects.equals(getUser(), that.getUser()) && Objects.equals(getReview(), that.getReview());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUser(), getReview(), getLikeCount(), isUserLikeReview());
    }

    @Override
    public String toString() {
        return "UserReview{" +
                "user=" + user +
                ", review=" + review +
                ", likeCount=" + likeCount +
                ", userLikeReview=" + userLikeReview +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.user, flags);
        dest.writeParcelable(this.review, flags);
        dest.writeLong(this.likeCount);
        dest.writeByte(this.userLikeReview ? (byte) 1 : (byte) 0);
    }

    protected UserReview(Parcel in) {
        this.user = in.readParcelable(User.class.getClassLoader());
        this.review = in.readParcelable(Review.class.getClassLoader());
        this.likeCount = in.readLong();
        this.userLikeReview = in.readByte() != 0;
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
