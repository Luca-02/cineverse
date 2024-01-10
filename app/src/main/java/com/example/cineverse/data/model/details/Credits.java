package com.example.cineverse.data.model.details;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Credits implements Parcelable {

    private List<Cast> cast;
    private List<Crew> crew;

    public Credits(List<Cast> cast, List<Crew> crew) {
        this.cast = cast;
        this.crew = crew;
    }

    public List<Cast> getCast() {
        return cast;
    }

    public void setCast(List<Cast> cast) {
        this.cast = cast;
    }

    public List<Crew> getCrew() {
        return crew;
    }

    public void setCrew(List<Crew> crew) {
        this.crew = crew;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.cast);
        dest.writeTypedList(this.crew);
    }

    protected Credits(Parcel in) {
        this.cast = in.createTypedArrayList(Cast.CREATOR);
        this.crew = in.createTypedArrayList(Crew.CREATOR);
    }

    public static final Parcelable.Creator<Credits> CREATOR = new Parcelable.Creator<Credits>() {
        @Override
        public Credits createFromParcel(Parcel source) {
            return new Credits(source);
        }

        @Override
        public Credits[] newArray(int size) {
            return new Credits[size];
        }
    };

    public Map<String, List<Crew>> getCrewMap() {
        Map<String, List<Crew>> crewMap = new HashMap<>();
        for (Crew item : crew) {
            String department = item.getDepartment();
            List<Crew> crewList = crewMap.get(department);
            if (crewList == null) {
                crewList = new ArrayList<>();
                crewList.add(item);
                crewMap.put(department, crewList);
            } else {
                crewList.add(item);
            }
        }
        return crewMap;
    }

}
