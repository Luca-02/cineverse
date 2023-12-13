package com.example.cineverse.data.source.genre.section;

import android.content.Context;

import com.example.cineverse.data.model.genre.GenreApiResponse;
import com.example.cineverse.data.source.genre.AbstractGenresRemoteDataSource;
import com.example.cineverse.data.source.genre.GenresResponseCallback;
import com.example.cineverse.data.source.genre.IGenresRemoteDataSource;

import retrofit2.Call;

public class MovieGenresRemoteDataSource
        extends AbstractGenresRemoteDataSource
        implements IGenresRemoteDataSource {

    public MovieGenresRemoteDataSource(Context context, GenresResponseCallback callback) {
        super(context, callback);
    }

    @Override
    public void fetch() {
        Call<GenreApiResponse> call =
                genreApiService.getMovieGenres(language, bearerAccessTokenAuth);
        handlePosterApiCal(call);
    }

}
