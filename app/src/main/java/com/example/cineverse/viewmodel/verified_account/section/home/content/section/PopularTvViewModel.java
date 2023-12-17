package com.example.cineverse.viewmodel.verified_account.section.home.content.section;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.cineverse.data.model.content.section.Tv;
import com.example.cineverse.data.source.content.AbstractSectionContentRemoteDataSource;
import com.example.cineverse.data.source.content.section.PopularTvRemoteDataSource;
import com.example.cineverse.viewmodel.verified_account.section.home.content.AbstractSectionTvViewModel;

/**
 * The {@link PopularTvViewModel} class is a ViewModel for content section.
 */
public class PopularTvViewModel
        extends AbstractSectionTvViewModel {

    /**
     * Constructs an {@link PopularTvViewModel} object with the given {@link Application}.
     *
     * @param application The {@link Application} of the calling component.
     */
    public PopularTvViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    protected AbstractSectionContentRemoteDataSource<Tv> createRemoteDataSourceInstance() {
        return new PopularTvRemoteDataSource(getApplication().getApplicationContext());
    }

}
