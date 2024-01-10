package com.example.cineverse.data.model.content.section;

import android.os.Parcel;

import com.example.cineverse.data.model.content.AbstractContent;
import com.example.cineverse.data.model.content.ContentEntityDb;
import com.google.gson.annotations.SerializedName;

/**
 * The {@link Tv} class represents a TV show with specific attributes such as name, release date,
 * poster path, and backdrop path. It extends the {@link AbstractContent} class.
 */
public class Tv extends AbstractContent {

    protected String name;
    @SerializedName("first_air_date")
    protected String releaseDate;

    public Tv(int id, String name, String overview, String releaseDate,
              String posterPath, String backdropPath, String originalLanguage) {
        super(id, overview, posterPath, backdropPath, originalLanguage);
        this.name = name;
        this.releaseDate = releaseDate;
    }

    public Tv(ContentEntityDb entityDb) {
        this(entityDb.getId(), entityDb.getName(), entityDb.getOverview(),
                entityDb.getReleaseDate(), entityDb.getPosterPath(),
                entityDb.getBackdropPath(), entityDb.getOriginalLanguage());
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

    protected Tv(Parcel in) {
        super(in);
        this.name = in.readString();
        this.releaseDate = in.readString();
    }

    public static final Creator<Tv> CREATOR = new Creator<Tv>() {
        @Override
        public Tv createFromParcel(Parcel source) {
            return new Tv(source);
        }

        @Override
        public Tv[] newArray(int size) {
            return new Tv[size];
        }
    };

}
