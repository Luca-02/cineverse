package com.example.cineverse.data.model.content.section;

import android.os.Parcel;

import com.example.cineverse.data.model.content.AbstractContent;
import com.google.gson.annotations.SerializedName;

public class ContentTv extends AbstractContent {

    private String name;
    @SerializedName("first_air_date")
    private String releaseDate;

    public ContentTv(int id, String posterPath, String backdropPath, String name) {
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

    protected ContentTv(Parcel in) {
        super(in);
        this.name = in.readString();
        this.releaseDate = in.readString();
    }

    public static final Creator<ContentTv> CREATOR = new Creator<ContentTv>() {
        @Override
        public ContentTv createFromParcel(Parcel source) {
            return new ContentTv(source);
        }

        @Override
        public ContentTv[] newArray(int size) {
            return new ContentTv[size];
        }
    };

}
