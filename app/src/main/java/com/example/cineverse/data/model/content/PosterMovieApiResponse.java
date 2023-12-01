package com.example.cineverse.data.model.content;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PosterMovieApiResponse implements Parcelable {

    private int page;
    private List<PosterMovie> results;
    @SerializedName("total_pages")
    private int totalPages;
    @SerializedName("total_results")
    private int totalResults;

    public PosterMovieApiResponse(int page, List<PosterMovie> results, int totalPages, int totalResults) {
        this.page = page;
        this.results = results;
        this.totalPages = totalPages;
        this.totalResults = totalResults;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<PosterMovie> getResults() {
        return results;
    }

    public void setResults(List<PosterMovie> results) {
        this.results = results;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int total_pages) {
        this.totalPages = total_pages;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public boolean isResponseOk() {
        return results != null;
    }

    @NonNull
    @Override
    public String toString() {
        return "MovieApiResponse{" +
                "page=" + page +
                ", results=" + results +
                ", total_pages=" + totalPages +
                ", total_results=" + totalResults +
                '}';
    }

    protected PosterMovieApiResponse(Parcel in) {
        page = in.readInt();
        results = in.createTypedArrayList(PosterMovie.CREATOR);
        totalPages = in.readInt();
        totalResults = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(page);
        dest.writeTypedList(results);
        dest.writeInt(totalPages);
        dest.writeInt(totalResults);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PosterMovieApiResponse> CREATOR = new Creator<PosterMovieApiResponse>() {
        @Override
        public PosterMovieApiResponse createFromParcel(Parcel in) {
            return new PosterMovieApiResponse(in);
        }

        @Override
        public PosterMovieApiResponse[] newArray(int size) {
            return new PosterMovieApiResponse[size];
        }
    };
}
