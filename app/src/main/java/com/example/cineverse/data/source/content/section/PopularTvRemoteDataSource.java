package com.example.cineverse.data.source.content.section;

import android.content.Context;

import com.example.cineverse.data.model.content.section.ContentTv;
import com.example.cineverse.data.model.content.section.ContentTvApiResponse;
import com.example.cineverse.data.source.content.AbstractSectionTvRemoteDataSource;
import com.example.cineverse.data.source.content.ISectionContentRemoteDataSource;
import com.example.cineverse.data.source.content.SectionContentResponseCallback;

import retrofit2.Call;

public class PopularTvRemoteDataSource
        extends AbstractSectionTvRemoteDataSource
        implements ISectionContentRemoteDataSource {

    public PopularTvRemoteDataSource(Context context,
                                     SectionContentResponseCallback<ContentTv> callback) {
        super(context, callback);
    }

    @Override
    public void fetch(int page) {
        Call<ContentTvApiResponse> call =
                movieApiService.getPopularTv(language, page, region, bearerAccessTokenAuth);
        handlePosterApiCal(call);
    }

}
