package com.example.cineverse.data.source.content.section;

import static com.example.cineverse.utils.constant.GlobalConstant.TAG;

import android.content.Context;
import android.util.Log;

import com.example.cineverse.data.model.content.section.ContentMovie;
import com.example.cineverse.data.model.content.section.ContentMovieApiResponse;
import com.example.cineverse.data.source.content.AbstractSectionMovieRemoteDataSource;
import com.example.cineverse.data.source.content.ISectionContentRemoteDataSource;
import com.example.cineverse.data.source.content.SectionContentResponseCallback;

import retrofit2.Call;

public class MovieFromGenreRemoteDataSource
        extends AbstractSectionMovieRemoteDataSource
        implements ISectionContentRemoteDataSource {

    private final int genreId;

    public MovieFromGenreRemoteDataSource(Context context,
                                          int genreId,
                                          SectionContentResponseCallback<ContentMovie> callback) {
        super(context, callback);
        this.genreId = genreId;
    }

    @Override
    public void fetch(int page) {
        Call<ContentMovieApiResponse> call =
                movieApiService.getMovieFromGenres(language, page, genreId, bearerAccessTokenAuth);
        handlePosterApiCal(call);
    }

}
