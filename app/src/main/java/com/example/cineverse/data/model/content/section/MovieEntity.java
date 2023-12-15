package com.example.cineverse.data.model.content.section;

import android.os.Parcel;

import com.example.cineverse.data.model.content.AbstractContent;
import com.google.gson.annotations.SerializedName;

public class MovieEntity extends AbstractContent {

    public static final String TYPE = "movie";

    @SerializedName("title")
    private String name;
    @SerializedName("release_date")
    private String releaseDate;

    public MovieEntity(int id, String name, String releaseDate, String posterPath, String backdropPath) {
        super(id, posterPath, backdropPath);
        this.name = name;
        this.releaseDate = releaseDate;
    }

    public MovieEntity(ContentEntityDb entityDb) {
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

    protected MovieEntity(Parcel in) {
        super(in);
        this.name = in.readString();
        this.releaseDate = in.readString();
    }

    public static final Creator<MovieEntity> CREATOR = new Creator<MovieEntity>() {
        @Override
        public MovieEntity createFromParcel(Parcel source) {
            return new MovieEntity(source);
        }

        @Override
        public MovieEntity[] newArray(int size) {
            return new MovieEntity[size];
        }
    };

}
