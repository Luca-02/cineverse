package com.example.cineverse.data.model.account_model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieApiResponse {
    @SerializedName("page")
    private String status;

    @SerializedName("total_results")
    private int totaleResult;
    @SerializedName("results")
    private List<MovieModel> mData;

    public MovieApiResponse(){}

    public MovieApiResponse(String status, int totaleResult, List<MovieModel> mData) {
        this.status = status;
        this.totaleResult = totaleResult;
        this.mData = mData;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotaleResult() {
        return totaleResult;
    }

    public void setTotaleResult(int totaleResult) {
        this.totaleResult = totaleResult;
    }

    public List<MovieModel> getmData() {
        return mData;
    }

    public void setmData(List<MovieModel> mData) {
        this.mData = mData;
    }
}
