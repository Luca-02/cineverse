package com.example.cineverse.service.api;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.cineverse.R;
import com.example.cineverse.data.model.content.movie.PosterMovie;
import com.example.cineverse.data.model.content.movie.PosterMovieApiResponse;
import com.example.cineverse.repository.firebase.user.UserFirebaseSourceTest;
import com.example.cineverse.utils.ServiceLocator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@RunWith(AndroidJUnit4.class)
public class MovieApiServiceTest {

    private static final String TAG = UserFirebaseSourceTest.class.getSimpleName();

    private Context context;
    private MovieApiService movieApiService;

    @Before
    public void setup() {
        context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        movieApiService = ServiceLocator.getInstance()
                .getRetrofitService()
                .create(MovieApiService.class);
    }

    @Test
    public void getPopularMovies() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);
        String language = context.getString(R.string.language);
        int page = 41575;
        String bearerAccessTokenAuth = "Bearer " + context.getString(R.string.access_token_auth);

        Log.d(TAG, "getPopularMovies: " + language);
        Log.d(TAG, "getPopularMovies: " + page);
        Log.d(TAG, "getPopularMovies: " + bearerAccessTokenAuth);

        Call<PosterMovieApiResponse> call = movieApiService.getPopularMovies(language, page, bearerAccessTokenAuth);

        call.enqueue(new Callback<PosterMovieApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<PosterMovieApiResponse> call, @NonNull Response<PosterMovieApiResponse> response) {
                if (response.body() != null && response.isSuccessful()) {
                    PosterMovieApiResponse movieResponse = response.body();
                    if (movieResponse.isResponseOk()) {
                        List<PosterMovie> posterMovies = movieResponse.getResults();

                        for (PosterMovie m : posterMovies) {
                            Log.d(TAG, m.toString());
                        }
                    } else {
                        Log.d(TAG, "onResponse: response error");
                    }
                } else {
                    Log.d(TAG, "onResponse: error");
                }
                assertNotNull(response.body());
                assertTrue(response.isSuccessful());
                latch.countDown();
            }

            @Override
            public void onFailure(@NonNull Call<PosterMovieApiResponse> call, @NonNull Throwable t) {
                Log.d(TAG, "onFailure: failed");
                latch.countDown();
            }
        });

        latch.await(5, TimeUnit.SECONDS);
        assertTrue(true);
    }

}
