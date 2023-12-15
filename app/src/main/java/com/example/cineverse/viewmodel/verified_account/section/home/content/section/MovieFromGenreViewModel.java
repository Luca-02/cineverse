package com.example.cineverse.viewmodel.verified_account.section.home.content.section;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.cineverse.data.model.content.section.MovieEntity;
import com.example.cineverse.data.source.content.remote.AbstractSectionContentRemoteDataSource;
import com.example.cineverse.data.source.content.remote.section.MovieFromGenreRemoteDataSource;
import com.example.cineverse.viewmodel.GlobalViewModelFactory;
import com.example.cineverse.viewmodel.verified_account.section.home.content.AbstractSectionMovieViewModel;

public class MovieFromGenreViewModel
        extends AbstractSectionMovieViewModel {

    /**
     * Constructs an {@link MovieFromGenreViewModel} object with the given {@link Application}.
     *
     * @param application The {@link Application} of the calling component.
     */
    public MovieFromGenreViewModel(@NonNull Application application, int genreId) {
        super(application, genreId);
    }

    @Override
    protected AbstractSectionContentRemoteDataSource<MovieEntity> createRemoteDataSourceInstance() {
        return new MovieFromGenreRemoteDataSource(getApplication().getApplicationContext(), genreId);
    }

    public static class Factory extends GlobalViewModelFactory<MovieFromGenreViewModel> {

        public Factory(@NonNull Application application, int genreId) {
            super(new MovieFromGenreViewModel(application, genreId));
        }

    }

}
