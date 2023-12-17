package com.example.cineverse.viewmodel.verified_account.section.home.content.section;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.cineverse.data.model.content.section.Movie;
import com.example.cineverse.data.source.content.AbstractSectionContentRemoteDataSource;
import com.example.cineverse.data.source.content.section.NowPlayingMovieRemoteDataSource;
import com.example.cineverse.viewmodel.verified_account.section.home.content.AbstractSectionMovieViewModel;

/**
 * The {@link NowPlayingMovieViewModel} class is a ViewModel for content section.
 */
public class NowPlayingMovieViewModel
        extends AbstractSectionMovieViewModel {

    /**
     * Constructs an {@link NowPlayingMovieViewModel} object with the given {@link Application}.
     *
     * @param application The {@link Application} of the calling component.
     */
    public NowPlayingMovieViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    protected AbstractSectionContentRemoteDataSource<Movie> createRemoteDataSourceInstance() {
        return new NowPlayingMovieRemoteDataSource(getApplication().getApplicationContext());
    }

}
