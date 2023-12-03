package com.example.cineverse.repository.content.movie;

import android.content.Context;

import com.example.cineverse.data.model.content.movie.PosterMovie;
import com.example.cineverse.data.model.content.movie.PosterMovieApiResponse;
import com.example.cineverse.repository.content.AbstractPosterRepository;
import com.example.cineverse.service.api.MovieApiService;
import com.example.cineverse.utils.ServiceLocator;

import retrofit2.Call;

public class PosterMovieRepository extends AbstractPosterRepository<PosterMovie> {

    private final MovieApiService movieApiService;

    public PosterMovieRepository(Context context) {
        super(context);
        movieApiService = ServiceLocator.getInstance()
                .getRetrofitService()
                .create(MovieApiService.class);
    }

    public void fetchPopularMovies(String language, int page, ResponseCallback<PosterMovie> callback) {
        Call<PosterMovieApiResponse> call =
                movieApiService.getPopularMovies(language, page, bearerAccessTokenAuth);
        handleApiCal(call, callback);
    }

    public void fetchTopRatedMovies(String language, int page, ResponseCallback<PosterMovie> callback) {
        Call<PosterMovieApiResponse> call =
                movieApiService.getTopRatedMovies(language, page, bearerAccessTokenAuth);
        handleApiCal(call, callback);
    }

    public void fetchUpcomingMovies(String language, int page, ResponseCallback<PosterMovie> callback) {
        Call<PosterMovieApiResponse> call =
                movieApiService.getUpcomingMovies(language, page, bearerAccessTokenAuth);
        handleApiCal(call, callback);
    }

}
