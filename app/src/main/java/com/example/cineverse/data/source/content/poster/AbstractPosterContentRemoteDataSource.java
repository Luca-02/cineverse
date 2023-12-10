package com.example.cineverse.data.source.content.poster;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.cineverse.R;
import com.example.cineverse.data.model.content.Failure;
import com.example.cineverse.data.model.content.AbstractContent;
import com.example.cineverse.data.model.content.AbstractContentApiResponse;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class AbstractPosterContentRemoteDataSource<T extends AbstractContent> {

    protected String language;
    protected String region;
    protected String bearerAccessTokenAuth;
    private final PosterContentResponseCallback<T> callback;

    public AbstractPosterContentRemoteDataSource(Context context,
                                                 PosterContentResponseCallback<T> callback) {
        language = context.getString(R.string.language);
        region = Locale.getDefault().getCountry();
        bearerAccessTokenAuth =
                "Bearer " + context.getString(R.string.access_token_auth);
        this.callback = callback;
    }

    public <A extends AbstractContentApiResponse<T>> void handlePosterApiCal(Call<A> call) {
        call.enqueue(new Callback<A>() {
            @Override
            public void onResponse(@NonNull Call<A> call, @NonNull Response<A> response) {
                if (response.body() != null && response.isSuccessful()) {
                    if (response.code() == 200) {
                        AbstractContentApiResponse<T> movieResponse = response.body();
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
