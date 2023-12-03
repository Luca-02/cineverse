package com.example.cineverse.repository.content;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.cineverse.R;
import com.example.cineverse.data.model.content.AbstractPoster;
import com.example.cineverse.data.model.content.AbstractPosterApiResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class AbstractPosterRepository
        <T extends AbstractPoster> {

    protected String bearerAccessTokenAuth;

    public AbstractPosterRepository(Context context) {
        bearerAccessTokenAuth = "Bearer " + context.getString(R.string.access_token_auth);
    }

    public <A extends AbstractPosterApiResponse<T>> void handleApiCal(Call<A> call, ResponseCallback<T> callback) {
        call.enqueue(new Callback<A>() {
            @Override
            public void onResponse(@NonNull Call<A> call, @NonNull Response<A> response) {
                if (response.body() != null && response.isSuccessful()) {
                    AbstractPosterApiResponse<T> movieResponse = response.body();
                    if (movieResponse.isResponseOk()) {
                        callback.onResponse(movieResponse);
                    } else {
                        callback.onResponse(null);
                    }
                } else {
                    callback.onResponse(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<A> call, @NonNull Throwable t) {
                callback.onResponse(null);
            }
        });
    }

    public interface ResponseCallback<B extends AbstractPoster> {
        void onResponse(AbstractPosterApiResponse<B> response);
    }

}
