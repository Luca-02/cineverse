package com.example.cineverse.data.model.content;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public abstract class AbstractContent implements Parcelable {

    private int id;
    @SerializedName("poster_path")
    protected String posterPath;
    @SerializedName("backdrop_path")
    protected String backdropPath;

    public AbstractContent(int id, String posterPath, String backdropPath) {
        this.id = id;
        this.posterPath = posterPath;
        this.backdropPath = backdropPath;
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

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public abstract String getName();

    public abstract void setName(String name);

    public abstract String getReleaseDate();

    public abstract void setReleaseDate(String releaseDate);

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.posterPath);
        dest.writeString(this.backdropPath);
    }

    protected AbstractContent(Parcel in) {
        this.id = in.readInt();
        this.posterPath = in.readString();
        this.backdropPath = in.readString();
    }

}
