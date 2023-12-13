package com.example.cineverse.viewmodel.verified_account.section.home.content.section;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.cineverse.data.source.content.ISectionContentRemoteDataSource;
import com.example.cineverse.data.source.content.section.TodayTrendingMovieRemoteDataSource;
import com.example.cineverse.viewmodel.verified_account.section.home.content.AbstractSectionMovieViewModel;

public class TodayTrendingMovieViewModel
        extends AbstractSectionMovieViewModel {

    /**
     * Constructs an {@link TodayTrendingMovieViewModel} object with the given {@link Application}.
     *
     * @param application The {@link Application} of the calling component.
     */
    public TodayTrendingMovieViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    protected ISectionContentRemoteDataSource createRemoteDataSourceInstance() {
        return new TodayTrendingMovieRemoteDataSource(
                getApplication().getApplicationContext(),
                this
        );
    }

}
