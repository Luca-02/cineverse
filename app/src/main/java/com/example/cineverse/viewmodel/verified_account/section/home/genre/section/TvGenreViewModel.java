package com.example.cineverse.viewmodel.verified_account.section.home.genre.section;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.cineverse.data.source.genre.IGenresRemoteDataSource;
import com.example.cineverse.data.source.genre.section.TvGenresRemoteDataSource;
import com.example.cineverse.viewmodel.verified_account.section.home.content.AbstractSectionContentViewModel;
import com.example.cineverse.viewmodel.verified_account.section.home.content.section.TvFromGenreViewModel;
import com.example.cineverse.viewmodel.verified_account.section.home.genre.AbstractSectionGenreViewModel;

public class TvGenreViewModel
        extends AbstractSectionGenreViewModel {

    /**
     * Constructs an {@link TvGenreViewModel} object with the given {@link Application}.
     *
     * @param application The {@link Application} of the calling component.
     */
    public TvGenreViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    protected IGenresRemoteDataSource createRemoteDataSourceInstance() {
        return new TvGenresRemoteDataSource(
                getApplication().getApplicationContext(),
                this
        );
    }

    @Override
    public Class<? extends AbstractSectionContentViewModel> getSectionContentViewModelClass() {
        return TvFromGenreViewModel.class;
    }

}
