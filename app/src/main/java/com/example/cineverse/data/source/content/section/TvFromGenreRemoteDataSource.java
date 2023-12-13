package com.example.cineverse.data.source.content.section;

import android.content.Context;

import com.example.cineverse.data.model.content.section.ContentTv;
import com.example.cineverse.data.model.content.section.ContentTvApiResponse;
import com.example.cineverse.data.source.content.AbstractSectionTvRemoteDataSource;
import com.example.cineverse.data.source.content.ISectionContentRemoteDataSource;
import com.example.cineverse.data.source.content.SectionContentResponseCallback;

import retrofit2.Call;

public class TvFromGenreRemoteDataSource
        extends AbstractSectionTvRemoteDataSource
        implements ISectionContentRemoteDataSource {

    private final int genreId;

    public TvFromGenreRemoteDataSource(Context context,
                                          int genreId,
                                          SectionContentResponseCallback<ContentTv> callback) {
        super(context, callback);
        this.genreId = genreId;
    }

    @Override
    public void fetch(int page) {
        Call<ContentTvApiResponse> call =
                movieApiService.getTvFromGenres(language, page, genreId, bearerAccessTokenAuth);
        handlePosterApiCal(call);
    }

}
