package com.example.cineverse.viewmodel.verified_account.section.home.genre.section;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.cineverse.data.source.genre.IGenresRemoteDataSource;
import com.example.cineverse.data.source.genre.section.MovieGenresRemoteDataSource;
import com.example.cineverse.viewmodel.verified_account.section.home.content.AbstractSectionContentViewModel;
import com.example.cineverse.viewmodel.verified_account.section.home.content.section.MovieFromGenreViewModel;
import com.example.cineverse.viewmodel.verified_account.section.home.genre.AbstractSectionGenreViewModel;

public class MovieGenreViewModel
        extends AbstractSectionGenreViewModel {

    /**
     * Constructs an {@link MovieGenreViewModel} object with the given {@link Application}.
     *
     * @param application The {@link Application} of the calling component.
     */
    public MovieGenreViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    protected IGenresRemoteDataSource createRemoteDataSourceInstance() {
        return new MovieGenresRemoteDataSource(
                getApplication().getApplicationContext(),
                this
        );
    }

    @Override
    public Class<? extends AbstractSectionContentViewModel> getSectionContentViewModelClass() {
        return MovieFromGenreViewModel.class;
    }

}
