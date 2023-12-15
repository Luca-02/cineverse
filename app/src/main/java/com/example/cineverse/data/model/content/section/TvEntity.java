package com.example.cineverse.data.model.content.section;

import android.os.Parcel;

import com.example.cineverse.data.model.content.AbstractContent;
import com.google.gson.annotations.SerializedName;

public class TvEntity extends AbstractContent {

    public static final String TYPE = "tv";

    private String name;
    @SerializedName("first_air_date")
    private String releaseDate;

    public TvEntity(int id, String name, String releaseDate, String posterPath, String backdropPath) {
        super(id, posterPath, backdropPath);
        this.name = name;
        this.releaseDate = releaseDate;
    }

    public TvEntity(ContentEntityDb entityDb) {
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

    protected TvEntity(Parcel in) {
        super(in);
        this.name = in.readString();
        this.releaseDate = in.readString();
    }

    public static final Creator<TvEntity> CREATOR = new Creator<TvEntity>() {
        @Override
        public TvEntity createFromParcel(Parcel source) {
            return new TvEntity(source);
        }

        @Override
        public TvEntity[] newArray(int size) {
            return new TvEntity[size];
        }
    };

}
