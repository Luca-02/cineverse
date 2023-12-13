package com.example.cineverse.data.source.content.section;

import android.content.Context;

import com.example.cineverse.data.model.content.section.ContentMovie;
import com.example.cineverse.data.model.content.section.ContentMovieApiResponse;
import com.example.cineverse.data.source.content.AbstractSectionMovieRemoteDataSource;
import com.example.cineverse.data.source.content.ISectionContentRemoteDataSource;
import com.example.cineverse.data.source.content.SectionContentResponseCallback;

import retrofit2.Call;

public class NowPlayingMovieRemoteDataSource
        extends AbstractSectionMovieRemoteDataSource
        implements ISectionContentRemoteDataSource {

    public NowPlayingMovieRemoteDataSource(Context context, SectionContentResponseCallback<ContentMovie> callback) {
        super(context, callback);
    }

    @Override
    public void fetch(int page) {
        Call<ContentMovieApiResponse> call =
                movieApiService.getNowPlayingMovies(language, page, region, bearerAccessTokenAuth);
        handlePosterApiCal(call);
    }

}
