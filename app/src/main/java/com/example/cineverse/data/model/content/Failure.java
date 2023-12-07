package com.example.cineverse.data.model.content;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class Failure {

    private boolean success;
    @SerializedName("status_code")
    private int statusCode;
    @SerializedName("status_message")
    private String statusMessage;

    public Failure(boolean success, int statusCode, String statusMessage) {
        this.success = success;
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public static Failure getUnexpectedError() {
        return new Failure(false, -1, "Unexpected error");
    }

    @NonNull
    @Override
    public String toString() {
        return "Failure{" +
                "success=" + success +
                ", statusCode=" + statusCode +
                ", statusMessage='" + statusMessage + '\'' +
                '}';
    }

}