package com.example.cineverse.data.source.content.section;

import android.content.Context;

import com.example.cineverse.data.model.content.section.TvResponse;
import com.example.cineverse.data.source.content.AbstractSectionTvRemoteDataSource;
import com.example.cineverse.data.source.content.ISectionContentRemoteDataSource;

import retrofit2.Call;

/**
 * The {@link TvFromGenreRemoteDataSource} class is responsible for fetching remote data.
 */
public class TvFromGenreRemoteDataSource
        extends AbstractSectionTvRemoteDataSource
        implements ISectionContentRemoteDataSource {

    private final int genreId;

    public TvFromGenreRemoteDataSource(Context context, int genreId) {
        super(context);
        this.genreId = genreId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void fetch(int page) {
        Call<TvResponse> call =
                movieApiService.getTvFromGenres(
                        getLanguage(),
                        page,
                        genreId,
                        getBearerAccessTokenAuth()
                );
        handlePosterApiCal(call);
    }

}
