package com.example.cineverse.viewmodel.verified_account.section.home.genre.section;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.cineverse.data.source.genre.AbstractGenresRemoteDataSource;
import com.example.cineverse.data.source.genre.section.MovieGenresRemoteDataSource;
import com.example.cineverse.viewmodel.verified_account.section.home.content.AbstractSectionContentViewModel;
import com.example.cineverse.viewmodel.verified_account.section.home.content.section.MovieFromGenreViewModel;
import com.example.cineverse.viewmodel.verified_account.section.home.genre.AbstractContentGenreViewModel;

/**
 * The {@link MovieContentGenreViewModel} class represents the ViewModel for the movie genre section in the home screen.
 * It extends the {@link AbstractContentGenreViewModel} class.
 */
public class MovieContentGenreViewModel
        extends AbstractContentGenreViewModel {

    /**
     * Constructs an {@link MovieContentGenreViewModel} object with the given {@link Application}.
     *
     * @param application The {@link Application} of the calling component.
     */
    public MovieContentGenreViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    protected AbstractGenresRemoteDataSource createRemoteDataSourceInstance() {
        return new MovieGenresRemoteDataSource(getApplication().getApplicationContext());
    }

    @Override
    public Class<? extends AbstractSectionContentViewModel> getSectionContentViewModelClass() {
        return MovieFromGenreViewModel.class;
    }

}
