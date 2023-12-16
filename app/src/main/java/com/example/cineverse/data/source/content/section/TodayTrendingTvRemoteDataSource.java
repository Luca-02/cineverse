package com.example.cineverse.data.source.content.section;

import android.content.Context;

import com.example.cineverse.data.model.content.section.TvResponse;
import com.example.cineverse.data.source.content.AbstractSectionTvRemoteDataSource;
import com.example.cineverse.data.source.content.ISectionContentRemoteDataSource;

import retrofit2.Call;

/**
 * The {@link TodayTrendingTvRemoteDataSource} class is responsible for fetching remote data.
 */
public class TodayTrendingTvRemoteDataSource
        extends AbstractSectionTvRemoteDataSource
        implements ISectionContentRemoteDataSource {

    public TodayTrendingTvRemoteDataSource(Context context) {
        super(context);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void fetch(int page) {
        Call<TvResponse> call =
                movieApiService.getTodayTrendingTv(
                        getLanguage(),
                        page,
                        getBearerAccessTokenAuth()
                );
        handlePosterApiCal(call);
    }

}
