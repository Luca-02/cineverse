package com.example.cineverse.data.model.details;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Jobs implements Parcelable {

    private String job;
    @SerializedName("episode_count")
    private String episodeCount;

    public Jobs(String job, String episodeCount) {
        this.job = job;
        this.episodeCount = episodeCount;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
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
        dest.writeString(this.job);
        dest.writeString(this.episodeCount);
    }

    protected Jobs(Parcel in) {
        this.job = in.readString();
        this.episodeCount = in.readString();
    }

    public static final Parcelable.Creator<Jobs> CREATOR = new Parcelable.Creator<Jobs>() {
        @Override
        public Jobs createFromParcel(Parcel source) {
            return new Jobs(source);
        }

        @Override
        public Jobs[] newArray(int size) {
            return new Jobs[size];
        }
    };

}
