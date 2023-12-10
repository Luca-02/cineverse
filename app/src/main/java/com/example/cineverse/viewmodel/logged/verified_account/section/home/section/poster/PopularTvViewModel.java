package com.example.cineverse.viewmodel.logged.verified_account.section.home.section.poster;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.cineverse.data.source.content.poster.IPosterContentRemoteDataSource;
import com.example.cineverse.data.source.content.poster.section.PopularTvRemoteDataSource;
import com.example.cineverse.viewmodel.logged.verified_account.section.home.section.AbstractSectionTvViewModel;

public class PopularTvViewModel
        extends AbstractSectionTvViewModel {

    /**
     * Constructs an {@link PopularMovieViewModel} object with the given {@link Application}.
     *
     * @param application The {@link Application} of the calling component.
     */
    public PopularTvViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    protected IPosterContentRemoteDataSource createRemoteDataSourceInstance() {
        return new PopularTvRemoteDataSource(
                getApplication().getBaseContext(),
                this
        );
    }

}
