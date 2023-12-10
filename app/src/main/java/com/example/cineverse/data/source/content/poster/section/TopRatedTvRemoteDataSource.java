package com.example.cineverse.data.source.content.poster.section;

import android.content.Context;

import com.example.cineverse.data.model.content.section.ContentTv;
import com.example.cineverse.data.model.content.section.ContentTvApiResponse;
import com.example.cineverse.data.source.content.poster.AbstractPosterTvRemoteDataSource;
import com.example.cineverse.data.source.content.poster.IPosterContentRemoteDataSource;
import com.example.cineverse.data.source.content.poster.PosterContentResponseCallback;

import retrofit2.Call;

public class TopRatedTvRemoteDataSource
        extends AbstractPosterTvRemoteDataSource
        implements IPosterContentRemoteDataSource {

    public TopRatedTvRemoteDataSource(Context context,
                                      PosterContentResponseCallback<ContentTv> callback) {
        super(context, callback);
    }

    @Override
    public void fetch(int page) {
        Call<ContentTvApiResponse> call =
                movieApiService.getTopRatedTv(language, page, region, bearerAccessTokenAuth);
        handlePosterApiCal(call);
    }

}
