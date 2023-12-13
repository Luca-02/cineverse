package com.example.cineverse.viewmodel.verified_account.section.home.content.section;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.cineverse.data.source.content.ISectionContentRemoteDataSource;
import com.example.cineverse.data.source.content.section.NowPlayingMovieRemoteDataSource;
import com.example.cineverse.viewmodel.verified_account.section.home.content.AbstractSectionMovieViewModel;

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
    protected ISectionContentRemoteDataSource createRemoteDataSourceInstance() {
        return new NowPlayingMovieRemoteDataSource(
                getApplication().getApplicationContext(),
                this
        );
    }

}
