package com.example.cineverse.viewmodel.verified_account.section.home.content.section;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.cineverse.data.source.content.ISectionContentRemoteDataSource;
import com.example.cineverse.data.source.content.section.TopRatedMovieRemoteDataSource;
import com.example.cineverse.viewmodel.verified_account.section.home.content.AbstractSectionMovieViewModel;

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
    protected ISectionContentRemoteDataSource createRemoteDataSourceInstance() {
        return new TopRatedMovieRemoteDataSource(
                getApplication().getApplicationContext(),
                this
        );
    }

}
