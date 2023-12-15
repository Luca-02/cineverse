package com.example.cineverse.viewmodel.verified_account.section.home.content.section;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.cineverse.data.model.content.section.TvEntity;
import com.example.cineverse.data.source.content.remote.AbstractSectionContentRemoteDataSource;
import com.example.cineverse.data.source.content.remote.section.WeekAiringTvRemoteDataSource;
import com.example.cineverse.viewmodel.verified_account.section.home.content.AbstractSectionTvViewModel;

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
    protected AbstractSectionContentRemoteDataSource<TvEntity> createRemoteDataSourceInstance() {
        return new WeekAiringTvRemoteDataSource(getApplication().getApplicationContext());
    }

}
