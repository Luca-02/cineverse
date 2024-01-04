package com.example.cineverse.data.model.details;

import com.google.gson.annotations.SerializedName;

public class Cast {

    private int id;
    @SerializedName("known_for_department")
    private String knownForDepartment;
    private String name;
    @SerializedName("profile_path")
    private String profilePath;
    private String character;

    public Cast(int id, String knownForDepartment, String name, String profilePath, String character) {
        this.id = id;
        this.knownForDepartment = knownForDepartment;
        this.name = name;
        this.profilePath = profilePath;
        this.character = character;
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

}
