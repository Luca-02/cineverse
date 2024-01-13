package com.example.cineverse.data.model.content;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.cineverse.R;
import com.example.cineverse.data.model.review.UserReview;
import com.google.gson.annotations.SerializedName;

import java.util.Comparator;
import java.util.Locale;

/**
 * The {@link AbstractContent} class is an abstract class representing the common attributes
 * of content entities such as movies and TV shows.
 */
public abstract class AbstractContent implements Parcelable {

    protected int id;
    protected String overview;
    @SerializedName("poster_path")
    protected String posterPath;
    @SerializedName("backdrop_path")
    protected String backdropPath;
    @SerializedName("original_language")
    private String originalLanguage;
    private Long watchlistTimestamp;

    public AbstractContent(int id) {
        this.id = id;
    }

    public AbstractContent(int id, Long watchlistTimestamp) {
        this.id = id;
        this.watchlistTimestamp = watchlistTimestamp;
    }

    public AbstractContent(int id, String overview, String posterPath, String backdropPath, String originalLanguage) {
        this.id = id;
        this.overview = overview;
        this.posterPath = posterPath;
        this.backdropPath = backdropPath;
        this.originalLanguage = originalLanguage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public Long getWatchlistTimestamp() {
        return watchlistTimestamp;
    }

    public void setWatchlistTimestamp(Long watchlistTimestamp) {
        this.watchlistTimestamp = watchlistTimestamp;
    }

    public String getCountryName() {
        Locale locale = new Locale(originalLanguage);
        return locale.getDisplayLanguage(locale);
    }

    public static String[] getSortingArrayString(Context context) {
        return new String[]{
                context.getString(R.string.recent_old),
                context.getString(R.string.old_recent),
                context.getString(R.string.alphabetic_a_z),
                context.getString(R.string.alphabetic_z_a)
        };
    }

    public static Comparator<AbstractContent> getComparator(int sortIndex) {
        switch (sortIndex) {
            case 0:
                return (o1, o2) -> Long.compare(
                        o1.getWatchlistTimestamp(), o2.getWatchlistTimestamp()) * -1;
            case 1:
                return Comparator.comparingLong(AbstractContent::getWatchlistTimestamp);
            case 2:
                return (o1, o2) -> o1.getName().compareToIgnoreCase(o2.getName());
            case 3:
                return (o1, o2) ->
                        o1.getName().compareToIgnoreCase(o2.getName()) * -1;
            default:
                return null;
        }
    }

    public abstract String getName();

    public abstract void setName(String name);

    public abstract String getReleaseDate();

    public abstract void setReleaseDate(String releaseDate);

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.overview);
        dest.writeString(this.posterPath);
        dest.writeString(this.backdropPath);
        dest.writeString(this.originalLanguage);
        dest.writeValue(this.watchlistTimestamp);
    }

    protected AbstractContent(Parcel in) {
        this.id = in.readInt();
        this.overview = in.readString();
        this.posterPath = in.readString();
        this.backdropPath = in.readString();
        this.originalLanguage = in.readString();
        this.watchlistTimestamp = (Long) in.readValue(Long.class.getClassLoader());
    }

}
