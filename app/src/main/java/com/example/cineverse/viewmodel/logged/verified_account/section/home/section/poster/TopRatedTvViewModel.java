package com.example.cineverse.viewmodel.logged.verified_account.section.home.section.poster;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.cineverse.R;
import com.example.cineverse.data.source.content.poster.IPosterContentRemoteDataSource;
import com.example.cineverse.data.source.content.poster.TopRatedTvRemoteDataSource;
import com.example.cineverse.viewmodel.logged.verified_account.section.home.section.AbstractSectionTvViewModel;

public class TopRatedTvViewModel
        extends AbstractSectionTvViewModel {

    /**
     * Constructs an {@link TopRatedTvViewModel} object with the given {@link Application}.
     *
     * @param application The {@link Application} of the calling component.
     */
    public TopRatedTvViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    protected IPosterContentRemoteDataSource createRemoteDataSourceInstance() {
        return new TopRatedTvRemoteDataSource(
                getApplication().getBaseContext(),
                this
        );
    }

}
