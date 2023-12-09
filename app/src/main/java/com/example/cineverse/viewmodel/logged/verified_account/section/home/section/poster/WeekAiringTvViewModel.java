package com.example.cineverse.viewmodel.logged.verified_account.section.home.section.poster;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.cineverse.R;
import com.example.cineverse.data.source.content.poster.IPosterContentRemoteDataSource;
import com.example.cineverse.data.source.content.poster.WeekAiringTvRemoteDataSource;
import com.example.cineverse.viewmodel.logged.verified_account.section.home.section.AbstractSectionTvViewModel;

public class WeekAiringTvViewModel
        extends AbstractSectionTvViewModel {

    /**
     * Constructs an {@link WeekAiringTvViewModel} object with the given {@link Application}.
     *
     * @param application The {@link Application} of the calling component.
     */
    public WeekAiringTvViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    protected IPosterContentRemoteDataSource createRemoteDataSourceInstance() {
        return new WeekAiringTvRemoteDataSource(
                getApplication().getBaseContext(),
                this
        );
    }

}
