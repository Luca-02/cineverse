package com.example.cineverse.viewmodel.verified_account.section.home.content.section;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.cineverse.data.model.content.section.MovieEntity;
import com.example.cineverse.data.source.content.remote.AbstractSectionContentRemoteDataSource;
import com.example.cineverse.data.source.content.remote.section.PopularMovieRemoteDataSource;
import com.example.cineverse.viewmodel.verified_account.section.home.content.AbstractSectionMovieViewModel;

public class PopularMovieViewModel
        extends AbstractSectionMovieViewModel {

    /**
     * Constructs an {@link PopularMovieViewModel} object with the given {@link Application}.
     *
     * @param application The {@link Application} of the calling component.
     */
    public PopularMovieViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    protected AbstractSectionContentRemoteDataSource<MovieEntity> createRemoteDataSourceInstance() {
        return new PopularMovieRemoteDataSource(getApplication().getApplicationContext());
    }

}
