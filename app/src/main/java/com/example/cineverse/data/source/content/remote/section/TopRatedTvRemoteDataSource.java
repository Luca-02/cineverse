package com.example.cineverse.data.source.content.remote.section;

import android.content.Context;

import com.example.cineverse.data.model.content.section.TvResponse;
import com.example.cineverse.data.source.content.remote.AbstractSectionTvRemoteDataSource;
import com.example.cineverse.data.source.content.remote.ISectionContentRemoteDataSource;

import retrofit2.Call;

public class TopRatedTvRemoteDataSource
        extends AbstractSectionTvRemoteDataSource
        implements ISectionContentRemoteDataSource {

    public TopRatedTvRemoteDataSource(Context context) {
        super(context);
    }

    @Override
    public void fetch(int page) {
        Call<TvResponse> call =
                movieApiService.getTopRatedTv(language, page, region, bearerAccessTokenAuth);
        handlePosterApiCal(call);
    }

}
