package com.example.cineverse.data.source.api;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.cineverse.R;
import com.example.cineverse.data.model.api.ApiResponse;
import com.example.cineverse.data.model.api.Failure;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * The {@link TMDBRemoteDataSource} class serves as a remote data source for handling API requests
 * related to The Movie Database (TMDB). It provides methods to retrieve language and access token
 * information and includes a generic method for handling poster API calls with callbacks.
 */
public class TMDBRemoteDataSource {

    private final Context context;

    public TMDBRemoteDataSource(Context context) {
        this.context = context;
    }

    /**
     * Retrieves the language information from the resources.
     *
     * @return The language string.
     */
    public String getLanguage() {
        return context.getString(R.string.language);
    }

    /**
     * Retrieves the Bearer access token for authentication from the resources.
     *
     * @return The Bearer access token string.
     */
    public String getBearerAccessTokenAuth() {
        return "Bearer " + context.getString(R.string.access_token_auth);
    }

    /**
     * Handles poster API calls with the provided Retrofit {@code call} and a callback for
     * processing the remote response.
     *
     * @param call     The Retrofit call for the poster API.
     * @param callback The callback for handling remote responses.
     * @param <Y>      The type parameter representing the expected API response.
     * @param <T>      The type parameter extending {@link ApiResponse}.
     */
    protected <Y extends ApiResponse, T extends Y> void handlePosterApiCal(Call<T> call, BaseRemoteResponseCallback<Y> callback) {
        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {
                if (response.body() != null && response.isSuccessful()) {
                    if (response.code() == 200) {
                        T bodyResponse = response.body();
                        callback.onRemoteResponse(bodyResponse);
                    } else {
                        callback.onFailure(Failure.getUnexpectedError());
                    }
                } else {
                    Failure failure = parseFailureResponse(response.errorBody());
                    callback.onFailure(failure != null ? failure : Failure.getUnexpectedError());
                }
            }

            @Override
            public void onFailure(@NonNull Call<T> call, @NonNull Throwable t) {
                callback.onFailure(Failure.getUnexpectedError());
            }
        });
    }

    /**
     * Parses the failure response from the {@code responseBody}.
     *
     * @param responseBody The response body containing failure information.
     * @return The parsed failure object or {@code null} if parsing fails.
     */
    protected Failure parseFailureResponse(ResponseBody responseBody) {
        try {
            if (responseBody != null) {
                return new Gson().fromJson(responseBody.string(), Failure.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}