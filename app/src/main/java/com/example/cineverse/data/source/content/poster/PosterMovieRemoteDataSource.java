package com.example.cineverse.data.source.content.poster;

import com.example.cineverse.data.model.content.poster.PosterMovie;
import com.example.cineverse.data.model.content.poster.PosterMovieApiResponse;
import com.example.cineverse.repository.content.poster.interfaces.IPosterMovieRepository;
import com.example.cineverse.service.api.MovieApiService;
import com.example.cineverse.utils.ServiceLocator;

import retrofit2.Call;

public class PosterMovieRemoteDataSource
        extends AbstractPosterContentRemoteDataSource<PosterMovie>
        implements IPosterMovieRepository {

    private final MovieApiService movieApiService;

    public PosterMovieRemoteDataSource(String bearerAccessTokenAuth,
                                       String region,
                                       PosterContentResponseCallback<PosterMovie> callback) {
        super(bearerAccessTokenAuth, region, callback);
        movieApiService = ServiceLocator.getInstance()
                .getRetrofitService()
                .create(MovieApiService.class);
    }

    @Override
    public void fetchNowPlayingMovies(String language, int page) {
        Call<PosterMovieApiResponse> call =
                movieApiService.getNowPlayingMovies(language, page, region, bearerAccessTokenAuth);
        handlePosterApiCal(call);
    }

    @Override
    public void fetchPopularMovies(String language, int page) {
        Call<PosterMovieApiResponse> call =
                movieApiService.getPopularMovies(language, page, region, bearerAccessTokenAuth);
        handlePosterApiCal(call);
    }

    @Override
    public void fetchTopRatedMovies(String language, int page) {
        Call<PosterMovieApiResponse> call =
                movieApiService.getTopRatedMovies(language, page, region, bearerAccessTokenAuth);
        handlePosterApiCal(call);
    }

    @Override
    public void fetchUpcomingMovies(String language, int page) {
        Call<PosterMovieApiResponse> call =
                movieApiService.getUpcomingMovies(language, page, region, bearerAccessTokenAuth);
        handlePosterApiCal(call);
    }

}
