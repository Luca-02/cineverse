package com.example.cineverse.viewmodel.verified_account.section.home.content.section;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.cineverse.data.model.content.section.Tv;
import com.example.cineverse.data.source.content.AbstractSectionContentRemoteDataSource;
import com.example.cineverse.data.source.content.section.TopRatedTvRemoteDataSource;
import com.example.cineverse.viewmodel.verified_account.section.home.content.AbstractSectionTvViewModel;

/**
 * The {@link TopRatedTvViewModel} class is a ViewModel for content section.
 */
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
    protected AbstractSectionContentRemoteDataSource<Tv> createRemoteDataSourceInstance() {
        return new TopRatedTvRemoteDataSource(getApplication().getApplicationContext());
    }

}
