package com.example.cineverse.data.model.content;

import android.content.Context;
import android.content.res.Resources;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Locale;

/**
 * The {@link AbstractContent} class is an abstract class representing the common attributes
 * of content entities such as movies and TV shows.
 */
public abstract class AbstractContent implements Parcelable {

    protected int id;
    protected String overview;
    @SerializedName("poster_path")
    protected String posterPath;
    @SerializedName("backdrop_path")
    protected String backdropPath;
    @SerializedName("original_language")
    private String originalLanguage;

    public AbstractContent(int id, String overview, String posterPath, String backdropPath, String originalLanguage) {
        this.id = id;
        this.overview = overview;
        this.posterPath = posterPath;
        this.backdropPath = backdropPath;
        this.originalLanguage = originalLanguage;
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

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getCountryName() {
        Locale locale = new Locale(originalLanguage);
        return locale.getDisplayLanguage(locale);
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
        dest.writeString(this.originalLanguage);
    }

    protected AbstractContent(Parcel in) {
        this.id = in.readInt();
        this.overview = in.readString();
        this.posterPath = in.readString();
        this.backdropPath = in.readString();
        this.originalLanguage = in.readString();
    }

}
