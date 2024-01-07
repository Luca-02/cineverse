package com.example.cineverse.data.model.details;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Role implements Parcelable {

    private String character;
    @SerializedName("episode_count")
    private String episodeCount;

    public Role(String character, String episodeCount) {
        this.character = character;
        this.episodeCount = episodeCount;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getEpisodeCount() {
        return episodeCount;
    }

    public void setEpisodeCount(String episodeCount) {
        this.episodeCount = episodeCount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.character);
        dest.writeString(this.episodeCount);
    }

    protected Role(Parcel in) {
        this.character = in.readString();
        this.episodeCount = in.readString();
    }

    public static final Parcelable.Creator<Role> CREATOR = new Parcelable.Creator<Role>() {
        @Override
        public Role createFromParcel(Parcel source) {
            return new Role(source);
        }

        @Override
        public Role[] newArray(int size) {
            return new Role[size];
        }
    };

}
