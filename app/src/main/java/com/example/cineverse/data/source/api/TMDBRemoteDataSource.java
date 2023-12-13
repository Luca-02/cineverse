package com.example.cineverse.data.source.api;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.cineverse.R;
import com.example.cineverse.data.model.ApiResponse;
import com.example.cineverse.data.model.Failure;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TMDBRemoteDataSource {

    protected final String language;
    protected final String bearerAccessTokenAuth;

    public TMDBRemoteDataSource(Context context) {
        language = context.getString(R.string.language);
        bearerAccessTokenAuth =
                "Bearer " + context.getString(R.string.access_token_auth);
    }

    protected <Y extends ApiResponse, T extends Y> void handlePosterApiCal(Call<T> call, BaseResponseCallback<Y> callback) {
        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {
                if (response.body() != null && response.isSuccessful()) {
                    if (response.code() == 200) {
                        T bodyResponse = response.body();
                        callback.onResponse(bodyResponse);
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