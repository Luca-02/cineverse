package com.example.cineverse.data.source.content.remote.section;

import android.content.Context;

import com.example.cineverse.data.model.content.section.TvResponse;
import com.example.cineverse.data.source.content.remote.AbstractSectionTvRemoteDataSource;

import retrofit2.Call;

public class WeekAiringTvRemoteDataSource
        extends AbstractSectionTvRemoteDataSource {

    public WeekAiringTvRemoteDataSource(Context context) {
        super(context);
    }

    @Override
    public void fetch(int page) {
        Call<TvResponse> call =
                movieApiService.getWeekAiringTv(language, page, region, bearerAccessTokenAuth);
        handlePosterApiCal(call);
    }

}
