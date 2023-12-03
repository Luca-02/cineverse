package com.example.cineverse.repository.content.tv;

import android.content.Context;

import com.example.cineverse.data.model.content.tv.PosterTv;
import com.example.cineverse.data.model.content.tv.PosterTvApiResponse;
import com.example.cineverse.repository.content.AbstractPosterRepository;
import com.example.cineverse.repository.content.movie.PosterMovieRepository;
import com.example.cineverse.service.api.TvApiService;
import com.example.cineverse.utils.ServiceLocator;

import retrofit2.Call;

public class PosterTvRepository extends AbstractPosterRepository<PosterTv> {

    private final TvApiService movieApiService;

    public PosterTvRepository(Context context) {
        super(context);
        movieApiService = ServiceLocator.getInstance()
                .getRetrofitService()
                .create(TvApiService.class);
    }

    public void fetchPopularTv(String language, int page, PosterMovieRepository.ResponseCallback<PosterTv> callback) {
        Call<PosterTvApiResponse> call =
                movieApiService.getPopularTv(language, page, bearerAccessTokenAuth);
        handleApiCal(call, callback);
    }

    public void fetchTopRatedTv(String language, int page, PosterMovieRepository.ResponseCallback<PosterTv> callback) {
        Call<PosterTvApiResponse> call =
                movieApiService.getTopRatedTv(language, page, bearerAccessTokenAuth);
        handleApiCal(call, callback);
    }

    public void fetchWeekAiringTv(String language, int page, PosterMovieRepository.ResponseCallback<PosterTv> callback) {
        Call<PosterTvApiResponse> call =
                movieApiService.getWeekAiringTv(language, page, bearerAccessTokenAuth);
        handleApiCal(call, callback);
    }

}
