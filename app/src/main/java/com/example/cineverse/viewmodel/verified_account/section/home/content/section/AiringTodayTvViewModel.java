package com.example.cineverse.viewmodel.verified_account.section.home.content.section;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.cineverse.data.model.content.section.Tv;
import com.example.cineverse.data.source.content.AbstractSectionContentRemoteDataSource;
import com.example.cineverse.data.source.content.section.AiringTodayTvRemoteDataSource;
import com.example.cineverse.viewmodel.verified_account.section.home.content.AbstractSectionTvViewModel;

/**
 * The {@link AiringTodayTvViewModel} class is a ViewModel for content section.
 */
public class AiringTodayTvViewModel
        extends AbstractSectionTvViewModel {

    /**
     * Constructs an {@link AiringTodayTvViewModel} object with the given {@link Application}.
     *
     * @param application The {@link Application} of the calling component.
     */
    public AiringTodayTvViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    protected AbstractSectionContentRemoteDataSource<Tv> createRemoteDataSourceInstance() {
        return new AiringTodayTvRemoteDataSource(getApplication().getApplicationContext());
    }

}
