package com.example.cineverse.repository.content.poster;

import android.content.Context;

import com.example.cineverse.data.model.content.poster.PosterTv;
import com.example.cineverse.data.source.content.poster.PosterContentResponseCallback;
import com.example.cineverse.data.source.content.poster.PosterTvRemoteDataSource;
import com.example.cineverse.repository.content.poster.interfaces.IPosterTvRepository;

public class PosterTvRepository
        extends AbstractPosterRepository<PosterTv>
        implements IPosterTvRepository {

    public PosterTvRemoteDataSource remoteDataSource;

    public PosterTvRepository(Context context,
                              PosterContentResponseCallback<PosterTv> callback) {
        super(context, callback);
        remoteDataSource = new PosterTvRemoteDataSource(bearerAccessTokenAuth, region, callback);
    }

    @Override
    public void fetchAiringTodayTv(String language, int page) {
        remoteDataSource.fetchAiringTodayTv(language, page);
    }

    public void fetchWeekAiringTv(String language, int page) {
        remoteDataSource.fetchWeekAiringTv(language, page);
    }

    public void fetchPopularTv(String language, int page) {
        remoteDataSource.fetchPopularTv(language, page);
    }

    public void fetchTopRatedTv(String language, int page) {
        remoteDataSource.fetchTopRatedTv(language, page);
    }

}
