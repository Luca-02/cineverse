package com.example.cineverse.data.model.details;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Crew implements Parcelable {

    private int id;
    @SerializedName("known_for_department")
    private String knownForDepartment;
    private String name;
    @SerializedName("profile_path")
    private String profilePath;
    private String department;
    private String job;
    private List<Jobs> jobs;

    public Crew(int id, String knownForDepartment, String name, String profilePath, String department, String job, List<Jobs> jobs) {
        this.id = id;
        this.knownForDepartment = knownForDepartment;
        this.name = name;
        this.profilePath = profilePath;
        this.department = department;
        this.job = job;
        this.jobs = jobs;
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

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public List<Jobs> getJobs() {
        return jobs;
    }

    public void setJobs(List<Jobs> jobs) {
        this.jobs = jobs;
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
        dest.writeString(this.department);
        dest.writeString(this.job);
        dest.writeTypedList(this.jobs);
    }

    protected Crew(Parcel in) {
        this.id = in.readInt();
        this.knownForDepartment = in.readString();
        this.name = in.readString();
        this.profilePath = in.readString();
        this.department = in.readString();
        this.job = in.readString();
        this.jobs = in.createTypedArrayList(Jobs.CREATOR);
    }

    public static final Creator<Crew> CREATOR = new Creator<Crew>() {
        @Override
        public Crew createFromParcel(Parcel source) {
            return new Crew(source);
        }

        @Override
        public Crew[] newArray(int size) {
            return new Crew[size];
        }
    };

}
