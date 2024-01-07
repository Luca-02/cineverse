package com.example.cineverse.data.model.review;

import android.os.Parcel;
import android.os.Parcelable;

public class UserReview implements Parcelable {

    private String userId;
    private Review review;

    public UserReview(String userId, Review review) {
        this.userId = userId;
        this.review = review;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
        dest.writeString(this.userId);
        dest.writeParcelable(this.review, flags);
    }

    protected UserReview(Parcel in) {
        this.userId = in.readString();
        this.review = in.readParcelable(Review.class.getClassLoader());
    }

    public static final Parcelable.Creator<UserReview> CREATOR = new Parcelable.Creator<UserReview>() {
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
