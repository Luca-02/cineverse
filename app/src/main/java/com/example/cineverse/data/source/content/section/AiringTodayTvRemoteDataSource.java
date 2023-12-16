package com.example.cineverse.data.source.content.section;

import android.content.Context;

import com.example.cineverse.data.model.content.section.TvResponse;
import com.example.cineverse.data.source.content.AbstractSectionTvRemoteDataSource;
import com.example.cineverse.data.source.content.ISectionContentRemoteDataSource;

import retrofit2.Call;

/**
 * The {@link AiringTodayTvRemoteDataSource} class is responsible for fetching remote data.
 */
public class AiringTodayTvRemoteDataSource
        extends AbstractSectionTvRemoteDataSource
        implements ISectionContentRemoteDataSource {

    public AiringTodayTvRemoteDataSource(Context context) {
        super(context);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Call<TvResponse> createApiCall(int page) {
        return movieApiService.getAiringTodayTv(
                getLanguage(),
                page,
                getRegion(),
                getBearerAccessTokenAuth()
        );
    }

}