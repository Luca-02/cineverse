package com.example.cineverse.data.model.content.section;

import android.os.Parcel;

import com.example.cineverse.data.model.content.AbstractContent;
import com.google.gson.annotations.SerializedName;

public class ContentMovie extends AbstractContent {

    @SerializedName("title")
    private String name;
    @SerializedName("release_date")
    private String releaseDate;

    public ContentMovie(int id, String posterPath, String backdropPath, String name) {
        super(id, posterPath, backdropPath);
        this.name = name;
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

    protected ContentMovie(Parcel in) {
        super(in);
        this.name = in.readString();
        this.releaseDate = in.readString();
    }

    public static final Creator<ContentMovie> CREATOR = new Creator<ContentMovie>() {
        @Override
        public ContentMovie createFromParcel(Parcel source) {
            return new ContentMovie(source);
        }

        @Override
        public ContentMovie[] newArray(int size) {
            return new ContentMovie[size];
        }
    };

}
