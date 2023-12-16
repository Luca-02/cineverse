package com.example.cineverse.data.source.content.section;

import android.content.Context;

import com.example.cineverse.data.model.content.section.TvResponse;
import com.example.cineverse.data.source.content.AbstractSectionTvRemoteDataSource;

import retrofit2.Call;

/**
 * The {@link WeekAiringTvRemoteDataSource} class is responsible for fetching remote data.
 */
public class WeekAiringTvRemoteDataSource
        extends AbstractSectionTvRemoteDataSource {

    public WeekAiringTvRemoteDataSource(Context context) {
        super(context);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Call<TvResponse> createApiCall(int page) {
        return movieApiService.getWeekAiringTv(
                getLanguage(),
                page,
                getRegion(),
                getBearerAccessTokenAuth()
        );
    }

}
