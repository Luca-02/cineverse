package com.example.cineverse.data.model.content;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * The {@link AbstractContent} class is an abstract class representing the common attributes
 * of content entities such as movies and TV shows.
 */
public abstract class AbstractContent implements Parcelable {

    private int id;
    private String overview;
    @SerializedName("poster_path")
    protected String posterPath;
    @SerializedName("backdrop_path")
    protected String backdropPath;

    public AbstractContent(int id, String overview, String posterPath, String backdropPath) {
        this.id = id;
        this.overview = overview;
        this.posterPath = posterPath;
        this.backdropPath = backdropPath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
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
        dest.writeString(this.overview);
        dest.writeString(this.posterPath);
        dest.writeString(this.backdropPath);
    }

    protected AbstractContent(Parcel in) {
        this.id = in.readInt();
        this.overview = in.readString();
        this.posterPath = in.readString();
        this.backdropPath = in.readString();
    }

}
