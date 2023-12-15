package com.example.cineverse.data.source.content.remote.section;

import android.content.Context;

import com.example.cineverse.data.model.content.section.TvResponse;
import com.example.cineverse.data.source.content.remote.AbstractSectionTvRemoteDataSource;
import com.example.cineverse.data.source.content.remote.ISectionContentRemoteDataSource;

import retrofit2.Call;

public class TvFromGenreRemoteDataSource
        extends AbstractSectionTvRemoteDataSource
        implements ISectionContentRemoteDataSource {

    private final int genreId;

    public TvFromGenreRemoteDataSource(Context context, int genreId) {
        super(context);
        this.genreId = genreId;
    }

    @Override
    public String getSection() {
        return null;
    }

    @Override
    public void fetch(int page) {
        Call<TvResponse> call =
                movieApiService.getTvFromGenres(language, page, genreId, bearerAccessTokenAuth);
        handlePosterApiCal(call);
    }

}
