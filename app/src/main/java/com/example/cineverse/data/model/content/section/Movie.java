package com.example.cineverse.data.model.content.section;

import android.os.Parcel;

import com.example.cineverse.data.model.content.AbstractContent;
import com.example.cineverse.data.model.content.ContentEntityDb;
import com.google.gson.annotations.SerializedName;

/**
 * The {@link Movie} class represents a movie with specific attributes such as title, release date,
 * poster path, and backdrop path. It extends the {@link AbstractContent} class.
 */
public class Movie extends AbstractContent {

    @SerializedName("title")
    private String name;
    @SerializedName("release_date")
    private String releaseDate;

    public Movie(int id, String name, String releaseDate, String posterPath, String backdropPath) {
        super(id, posterPath, backdropPath);
        this.name = name;
        this.releaseDate = releaseDate;
    }

    public Movie(ContentEntityDb entityDb) {
        this(entityDb.getId(), entityDb.getName(), entityDb.getReleaseDate(),
                entityDb.getPosterPath(), entityDb.getBackdropPath());
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getReleaseDate() {
        return releaseDate;
    }

    @Override
    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.name);
        dest.writeString(this.releaseDate);
    }

    protected Movie(Parcel in) {
        super(in);
        this.name = in.readString();
        this.releaseDate = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

}