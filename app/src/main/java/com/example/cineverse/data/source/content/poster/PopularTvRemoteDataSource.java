package com.example.cineverse.data.source.content.poster;

import android.content.Context;

import com.example.cineverse.data.model.content.poster.PosterTv;
import com.example.cineverse.data.model.content.poster.PosterTvApiResponse;

import retrofit2.Call;

public class PopularTvRemoteDataSource
        extends AbstractPosterTvRemoteDataSource
        implements IPosterContentRemoteDataSource {

    public PopularTvRemoteDataSource(Context context,
                                     PosterContentResponseCallback<PosterTv> callback) {
        super(context, callback);
    }

    @Override
    public void fetch(int page) {
        Call<PosterTvApiResponse> call =
                movieApiService.getPopularTv(language, page, region, bearerAccessTokenAuth);
        handlePosterApiCal(call);
    }

}
