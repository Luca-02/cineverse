package com.example.cineverse.data.model.details;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Cast implements Parcelable {

    private int id;
    @SerializedName("known_for_department")
    private String knownForDepartment;
    private String name;
    @SerializedName("profile_path")
    private String profilePath;
    private String character;
    private List<Role> roles;

    public Cast(int id, String knownForDepartment, String name, String profilePath, String character, List<Role> roles) {
        this.id = id;
        this.knownForDepartment = knownForDepartment;
        this.name = name;
        this.profilePath = profilePath;
        this.character = character;
        this.roles = roles;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKnownForDepartment() {
        return knownForDepartment;
    }

    public void setKnownForDepartment(String knownForDepartment) {
        this.knownForDepartment = knownForDepartment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfilePath() {
        return profilePath;
    }

    public void setProfilePath(String profilePath) {
        this.profilePath = profilePath;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.knownForDepartment);
        dest.writeString(this.name);
        dest.writeString(this.profilePath);
        dest.writeString(this.character);
        dest.writeTypedList(this.roles);
    }

    protected Cast(Parcel in) {
        this.id = in.readInt();
        this.knownForDepartment = in.readString();
        this.name = in.readString();
        this.profilePath = in.readString();
        this.character = in.readString();
        this.roles = in.createTypedArrayList(Role.CREATOR);
    }

    public static final Creator<Cast> CREATOR = new Creator<Cast>() {
        @Override
        public Cast createFromParcel(Parcel source) {
            return new Cast(source);
        }

        @Override
        public Cast[] newArray(int size) {
            return new Cast[size];
        }
    };

}
