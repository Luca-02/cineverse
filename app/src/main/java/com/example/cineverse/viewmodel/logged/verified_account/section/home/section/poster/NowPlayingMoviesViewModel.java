package com.example.cineverse.viewmodel.logged.verified_account.section.home.section.poster;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.cineverse.R;
import com.example.cineverse.data.source.content.poster.IPosterContentRemoteDataSource;
import com.example.cineverse.data.source.content.poster.NowPlayingMovieRemoteDataSource;
import com.example.cineverse.viewmodel.logged.verified_account.section.home.section.AbstractSectionMovieViewModel;

public class NowPlayingMoviesViewModel
        extends AbstractSectionMovieViewModel {

    /**
     * Constructs an {@link NowPlayingMoviesViewModel} object with the given {@link Application}.
     *
     * @param application The {@link Application} of the calling component.
     */
    public NowPlayingMoviesViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    protected IPosterContentRemoteDataSource createRemoteDataSourceInstance() {
        return new NowPlayingMovieRemoteDataSource(
                getApplication().getBaseContext(),
                this
        );
    }

}