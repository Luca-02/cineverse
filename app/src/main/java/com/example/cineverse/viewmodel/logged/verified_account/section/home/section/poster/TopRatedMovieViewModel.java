package com.example.cineverse.viewmodel.logged.verified_account.section.home.section.poster;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.cineverse.data.source.content.poster.IPosterContentRemoteDataSource;
import com.example.cineverse.data.source.content.poster.section.TopRatedMovieRemoteDataSource;
import com.example.cineverse.viewmodel.logged.verified_account.section.home.section.AbstractSectionMovieViewModel;

public class TopRatedMovieViewModel
        extends AbstractSectionMovieViewModel {

    /**
     * Constructs an {@link TopRatedMovieViewModel} object with the given {@link Application}.
     *
     * @param application The {@link Application} of the calling component.
     */
    public TopRatedMovieViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    protected IPosterContentRemoteDataSource createRemoteDataSourceInstance() {
        return new TopRatedMovieRemoteDataSource(
                getApplication().getBaseContext(),
                this
        );
    }

}
