package com.example.cineverse.viewmodel.verified_account.section.home.genre.section;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.cineverse.data.source.genre.AbstractGenresRemoteDataSource;
import com.example.cineverse.data.source.genre.section.TvGenresRemoteDataSource;
import com.example.cineverse.viewmodel.verified_account.section.home.content.AbstractSectionContentViewModel;
import com.example.cineverse.viewmodel.verified_account.section.home.content.section.TvFromGenreViewModel;
import com.example.cineverse.viewmodel.verified_account.section.home.genre.AbstractContentGenreViewModel;

/**
 * The {@link TvContentGenreViewModel} class represents the ViewModel for the TV genre section in the home screen.
 * It extends the {@link AbstractContentGenreViewModel} class.
 */
public class TvContentGenreViewModel
        extends AbstractContentGenreViewModel {

    /**
     * Constructs an {@link TvContentGenreViewModel} object with the given {@link Application}.
     *
     * @param application The {@link Application} of the calling component.
     */
    public TvContentGenreViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    protected AbstractGenresRemoteDataSource createRemoteDataSourceInstance() {
        return new TvGenresRemoteDataSource(getApplication().getApplicationContext());
    }

    @Override
    public Class<? extends AbstractSectionContentViewModel> getSectionContentViewModelClass() {
        return TvFromGenreViewModel.class;
    }

}
