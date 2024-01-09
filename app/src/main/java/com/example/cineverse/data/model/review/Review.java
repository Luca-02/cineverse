package com.example.cineverse.data.model.review;

import static com.example.cineverse.utils.constant.GlobalConstant.TIMESTAMP_DATE_FORMAT;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.cineverse.utils.DateTimeUtils;

import java.util.Objects;

public class Review implements Cloneable, Parcelable {

    private int rating;
    private String review;
    private long timestamp;

    public Review() {}

    public Review(int rating, String review, long timestamp) {
        this.rating = rating;
        this.review = review;
        this.timestamp = timestamp;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String timestampString() {
        return DateTimeUtils.formatDateFromTimestamp(TIMESTAMP_DATE_FORMAT, timestamp);
    }

    public static Review createDefaultReview() {
        return new Review(0, null, 0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Review)) return false;
        Review review1 = (Review) o;
        return getRating() == review1.getRating() &&
                Objects.equals(getReview(), review1.getReview());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRating(), getReview(), getTimestamp());
    }

    @NonNull
    @Override
    public Review clone() {
        try {
            return (Review) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.rating);
        dest.writeString(this.review);
        dest.writeLong(this.timestamp);
    }

    protected Review(Parcel in) {
        this.rating = in.readInt();
        this.review = in.readString();
        this.timestamp = in.readLong();
    }

    public static final Creator<Review> CREATOR = new Creator<Review>() {
        @Override
        public Review createFromParcel(Parcel source) {
            return new Review(source);
        }

        @Override
        public Review[] newArray(int size) {
            return new Review[size];
        }
    };

}
