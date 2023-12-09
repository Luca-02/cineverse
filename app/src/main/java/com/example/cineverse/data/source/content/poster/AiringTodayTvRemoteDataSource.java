package com.example.cineverse.data.source.content.poster;

import android.content.Context;

import com.example.cineverse.data.model.content.poster.PosterTv;
import com.example.cineverse.data.model.content.poster.PosterTvApiResponse;

import retrofit2.Call;

public class AiringTodayTvRemoteDataSource
        extends AbstractPosterTvRemoteDataSource
        implements IPosterContentRemoteDataSource {

    public AiringTodayTvRemoteDataSource(Context context,
                                         PosterContentResponseCallback<PosterTv> callback) {
        super(context, callback);
    }

    public void fetch(int page) {
        Call<PosterTvApiResponse> call =
                movieApiService.getAiringTodayTv(language, page, region, bearerAccessTokenAuth);
        handlePosterApiCal(call);
    }

}
