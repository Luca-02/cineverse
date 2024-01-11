package com.example.cineverse.data.model.details;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ContentVideos implements Parcelable {

    private List<Video> results;

    public ContentVideos(List<Video> results) {
        this.results = results;
    }

    public List<Video> getResults() {
        return results;
    }

    public void setResults(List<Video> results) {
        this.results = results;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.results);
    }

    public void readFromParcel(Parcel source) {
        this.results = source.createTypedArrayList(Video.CREATOR);
    }

    protected ContentVideos(Parcel in) {
        this.results = in.createTypedArrayList(Video.CREATOR);
    }

    public static final Creator<ContentVideos> CREATOR = new Creator<ContentVideos>() {
        @Override
        public ContentVideos createFromParcel(Parcel source) {
            return new ContentVideos(source);
        }

        @Override
        public ContentVideos[] newArray(int size) {
            return new ContentVideos[size];
        }
    };

}
