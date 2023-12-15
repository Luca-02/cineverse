package com.example.cineverse.viewmodel.verified_account.section.home.content.section;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.cineverse.data.model.content.section.MovieEntity;
import com.example.cineverse.data.source.content.remote.AbstractSectionContentRemoteDataSource;
import com.example.cineverse.data.source.content.remote.section.UpcomingMovieRemoteDataSource;
import com.example.cineverse.viewmodel.verified_account.section.home.content.AbstractSectionMovieViewModel;

public class UpcomingMovieViewModel
        extends AbstractSectionMovieViewModel {

    /**
     * Constructs an {@link UpcomingMovieViewModel} object with the given {@link Application}.
     *
     * @param application The {@link Application} of the calling component.
     */
    public UpcomingMovieViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    protected AbstractSectionContentRemoteDataSource<MovieEntity> createRemoteDataSourceInstance() {
        return new UpcomingMovieRemoteDataSource(getApplication().getApplicationContext());
    }

}
