package com.example.cineverse.data.source.content.poster;

import com.example.cineverse.data.model.content.poster.PosterTv;
import com.example.cineverse.data.model.content.poster.PosterTvApiResponse;
import com.example.cineverse.repository.content.poster.interfaces.IPosterTvRepository;
import com.example.cineverse.service.api.TvApiService;
import com.example.cineverse.utils.ServiceLocator;

import retrofit2.Call;

public class PosterTvRemoteDataSource
        extends AbstractPosterContentRemoteDataSource<PosterTv>
        implements IPosterTvRepository {

    private final TvApiService movieApiService;

    public PosterTvRemoteDataSource(String bearerAccessTokenAuth,
                                    String region,
                                    PosterContentResponseCallback<PosterTv> callback) {
        super(bearerAccessTokenAuth, region, callback);
        movieApiService = ServiceLocator.getInstance()
                .getRetrofitService()
                .create(TvApiService.class);
    }

    @Override
    public void fetchAiringTodayTv(String language, int page) {
        Call<PosterTvApiResponse> call =
                movieApiService.getAiringTodayTv(language, page, region, bearerAccessTokenAuth);
        handlePosterApiCal(call);
    }

    @Override
    public void fetchWeekAiringTv(String language, int page) {
        Call<PosterTvApiResponse> call =
                movieApiService.getWeekAiringTv(language, page, region, bearerAccessTokenAuth);
        handlePosterApiCal(call);
    }

    @Override
    public void fetchPopularTv(String language, int page) {
        Call<PosterTvApiResponse> call =
                movieApiService.getPopularTv(language, page, region, bearerAccessTokenAuth);
        handlePosterApiCal(call);
    }

    @Override
    public void fetchTopRatedTv(String language, int page) {
        Call<PosterTvApiResponse> call =
                movieApiService.getTopRatedTv(language, page, region, bearerAccessTokenAuth);
        handlePosterApiCal(call);
    }

}
