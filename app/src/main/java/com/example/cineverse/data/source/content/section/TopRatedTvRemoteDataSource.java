package com.example.cineverse.data.source.content.section;

import android.content.Context;

import com.example.cineverse.data.model.content.section.TvResponse;
import com.example.cineverse.data.source.content.AbstractSectionTvRemoteDataSource;
import com.example.cineverse.data.source.content.ISectionContentRemoteDataSource;

import retrofit2.Call;

/**
 * The {@link TopRatedTvRemoteDataSource} class is responsible for fetching remote data.
 */
public class TopRatedTvRemoteDataSource
        extends AbstractSectionTvRemoteDataSource
        implements ISectionContentRemoteDataSource {

    public TopRatedTvRemoteDataSource(Context context) {
        super(context);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Call<TvResponse> createApiCall(int page) {
        return movieApiService.getTopRatedTv(
                getLanguage(),
                page,
                getRegion(),
                getBearerAccessTokenAuth()
        );
    }

}
