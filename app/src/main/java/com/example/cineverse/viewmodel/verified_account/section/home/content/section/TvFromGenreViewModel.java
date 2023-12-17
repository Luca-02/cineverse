package com.example.cineverse.viewmodel.verified_account.section.home.content.section;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.cineverse.data.model.content.section.Tv;
import com.example.cineverse.data.source.content.AbstractSectionContentRemoteDataSource;
import com.example.cineverse.data.source.content.section.TvFromGenreRemoteDataSource;
import com.example.cineverse.viewmodel.GlobalViewModelFactory;
import com.example.cineverse.viewmodel.verified_account.section.home.content.AbstractSectionTvViewModel;

/**
 * The {@link TvFromGenreViewModel} class is a ViewModel for content section.
 */
public class TvFromGenreViewModel
        extends AbstractSectionTvViewModel {

    /**
     * Constructs an {@link MovieFromGenreViewModel} object with the given {@link Application}.
     *
     * @param application The {@link Application} of the calling component.
     */
    public TvFromGenreViewModel(@NonNull Application application, int genreId) {
        super(application, genreId);
    }

    @Override
    protected AbstractSectionContentRemoteDataSource<Tv> createRemoteDataSourceInstance() {
        return new TvFromGenreRemoteDataSource(getApplication().getApplicationContext(), genreId);
    }

    public static class Factory extends GlobalViewModelFactory<TvFromGenreViewModel> {

        public Factory(@NonNull Application application, int genreId) {
            super(new TvFromGenreViewModel(application, genreId));
        }

    }

}
