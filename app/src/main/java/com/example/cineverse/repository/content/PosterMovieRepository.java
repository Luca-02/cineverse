package com.example.cineverse.repository.classes.content;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.cineverse.R;
import com.example.cineverse.data.model.content.PosterMovieApiResponse;
import com.example.cineverse.service.api.MovieApiService;
import com.example.cineverse.utils.ServiceLocator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PosterMovieRepository {

    private final Context context;
    private final MovieApiService movieApiService;

    public PosterMovieRepository(Context context) {
        this.context = context;
        movieApiService = ServiceLocator.getInstance()
                .getRetrofitService()
                .create(MovieApiService.class);
    }

    public void fetchPopularMovies(int page, ResponseCallback callback) {
        String language = context.getString(R.string.language);
        String bearerAccessTokenAuth = "Bearer " + context.getString(R.string.access_token_auth);
        Call<PosterMovieApiResponse> call = movieApiService.getPopularMovies(language, page, bearerAccessTokenAuth);
        handleApiCal(call, callback);
    }

    public void handleApiCal(Call<PosterMovieApiResponse> call, ResponseCallback callback) {
        call.enqueue(new Callback<PosterMovieApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<PosterMovieApiResponse> call, @NonNull Response<PosterMovieApiResponse> response) {
                if (response.body() != null && response.isSuccessful()) {
                    PosterMovieApiResponse movieResponse = response.body();
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
            public void onFailure(@NonNull Call<PosterMovieApiResponse> call, @NonNull Throwable t) {
                callback.onResponse(null);
            }
        });
    }

    public interface ResponseCallback {
        void onResponse(PosterMovieApiResponse response);
    }

}
