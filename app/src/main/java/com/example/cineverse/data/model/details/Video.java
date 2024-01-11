package com.example.cineverse.data.model.details;

import android.os.Parcel;
import android.os.Parcelable;

public class Video implements Parcelable {

    private String iso_639_1;
    private String iso_3166_1;
    private String name;
    private String key;
    private String site;
    private int size;
    private String type;
    private boolean official;
    private String id;

    public Video(String iso_639_1, String iso_3166_1, String name, String key,
                 String site, int size, String type, boolean official, String id) {
        this.iso_639_1 = iso_639_1;
        this.iso_3166_1 = iso_3166_1;
        this.name = name;
        this.key = key;
        this.site = site;
        this.size = size;
        this.type = type;
        this.official = official;
        this.id = id;
    }

    public String getIso_639_1() {
        return iso_639_1;
    }

    public void setIso_639_1(String iso_639_1) {
        this.iso_639_1 = iso_639_1;
    }

    public String getIso_3166_1() {
        return iso_3166_1;
    }

    public void setIso_3166_1(String iso_3166_1) {
        this.iso_3166_1 = iso_3166_1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isOfficial() {
        return official;
    }

    public void setOfficial(boolean official) {
        this.official = official;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.iso_639_1);
        dest.writeString(this.iso_3166_1);
        dest.writeString(this.name);
        dest.writeString(this.key);
        dest.writeString(this.site);
        dest.writeInt(this.size);
        dest.writeString(this.type);
        dest.writeByte(this.official ? (byte) 1 : (byte) 0);
        dest.writeString(this.id);
    }

    protected Video(Parcel in) {
        this.iso_639_1 = in.readString();
        this.iso_3166_1 = in.readString();
        this.name = in.readString();
        this.key = in.readString();
        this.site = in.readString();
        this.size = in.readInt();
        this.type = in.readString();
        this.official = in.readByte() != 0;
        this.id = in.readString();
    }

    public static final Parcelable.Creator<Video> CREATOR = new Parcelable.Creator<Video>() {
        @Override
        public Video createFromParcel(Parcel source) {
            return new Video(source);
        }

        @Override
        public Video[] newArray(int size) {
            return new Video[size];
        }
    };

}
