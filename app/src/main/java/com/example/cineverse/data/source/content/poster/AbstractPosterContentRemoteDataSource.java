package com.example.cineverse.data.source.content.poster;

import androidx.annotation.NonNull;

import com.example.cineverse.data.model.content.Failure;
import com.example.cineverse.data.model.content.poster.AbstractPoster;
import com.example.cineverse.data.model.content.poster.AbstractPosterApiResponse;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class AbstractPosterContentRemoteDataSource<T extends AbstractPoster> {

    protected String bearerAccessTokenAuth;
    protected String region;
    private final PosterContentResponseCallback<T> callback;

    public AbstractPosterContentRemoteDataSource(String bearerAccessTokenAuth,
                                                 String region,
                                                 PosterContentResponseCallback<T> callback) {
        this.bearerAccessTokenAuth = bearerAccessTokenAuth;
        this.region = region;
        this.callback = callback;
    }

    public <A extends AbstractPosterApiResponse<T>> void handlePosterApiCal(Call<A> call) {
        call.enqueue(new Callback<A>() {
            @Override
            public void onResponse(@NonNull Call<A> call, @NonNull Response<A> response) {
                if (response.body() != null && response.isSuccessful()) {
                    if (response.code() == 200) {
                        AbstractPosterApiResponse<T> movieResponse = response.body();
                        callback.onResponse(movieResponse);
                    } else {
                        callback.onFailure(Failure.getUnexpectedError());
                    }
                } else {
                    Failure failure = parseFailureResponse(response.errorBody());
                    callback.onFailure(failure != null ? failure : Failure.getUnexpectedError());
                }
            }

            @Override
            public void onFailure(@NonNull Call<A> call, @NonNull Throwable t) {
                callback.onFailure(Failure.getUnexpectedError());
            }
        });
    }

    private Failure parseFailureResponse(ResponseBody responseBody) {
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
