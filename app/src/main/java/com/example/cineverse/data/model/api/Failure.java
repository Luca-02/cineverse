package com.example.cineverse.data.model.api;

import static com.example.cineverse.utils.constant.Api.UNEXPECTED_ERROR_CODE;
import static com.example.cineverse.utils.constant.Api.UNEXPECTED_ERROR_MESSAGE;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

/**
 * The {@link Failure} class represents a failure response from an API request.
 */
public class Failure {

    private boolean success;
    @SerializedName("status_code")
    private int statusCode;
    @SerializedName("status_message")
    private String statusMessage;

    /**
     * Constructs a {@code Failure} instance with the specified success status, status code, and status message.
     *
     * @param success       The success status of the API request.
     * @param statusCode    The status code of the API request.
     * @param statusMessage The status message of the API request.
     */
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

    /**
     * Returns a {@link Failure} instance representing an unexpected error.
     *
     * @return A {@link Failure} instance with a status code of -1 and the message "Unexpected error."
     */
    public static Failure getUnexpectedError() {
        return new Failure(false, UNEXPECTED_ERROR_CODE, UNEXPECTED_ERROR_MESSAGE);
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