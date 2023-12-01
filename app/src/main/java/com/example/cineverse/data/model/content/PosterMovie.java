package com.example.cineverse.data.model.content;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class PosterMovie implements Parcelable {

    private int id;
    @SerializedName("poster_path")
    private String posterPath;

    public PosterMovie(int id, String posterPath) {
        this.id = id;
        this.posterPath = posterPath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    @NonNull
    @Override
    public String toString() {
        return "PosterMovie{" +
                "id=" + id +
                ", posterPath='" + posterPath + '\'' +
                '}';
    }

    protected PosterMovie(Parcel in) {
        id = in.readInt();
        posterPath = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(posterPath);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PosterMovie> CREATOR = new Creator<PosterMovie>() {
        @Override
        public PosterMovie createFromParcel(Parcel in) {
            return new PosterMovie(in);
        }

        @Override
        public PosterMovie[] newArray(int size) {
            return new PosterMovie[size];
        }
    };

}
