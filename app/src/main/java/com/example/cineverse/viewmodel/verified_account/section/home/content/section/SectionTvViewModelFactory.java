package com.example.cineverse.viewmodel.verified_account.section.home.content.section;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.cineverse.data.model.content.section.Tv;
import com.example.cineverse.data.source.content.AbstractSectionContentRemoteDataSource;
import com.example.cineverse.viewmodel.verified_account.section.home.content.AbstractSectionContentViewModel;
import com.example.cineverse.viewmodel.verified_account.section.home.content.AbstractSectionContentViewModelFactory;

/**
 * The {@link SectionTvViewModelFactory} class extends {@link AbstractSectionContentViewModelFactory}
 * and is responsible for creating instances of {@link SectionTvViewModel} with the provided
 * application context and remote data source for TV content sections.
 */
public class SectionTvViewModelFactory extends AbstractSectionContentViewModelFactory<SectionTvViewModel> {

    private final Application application;
    private final AbstractSectionContentRemoteDataSource<Tv> remoteDataSource;

    /**
     * Constructs a new instance of {@link SectionTvViewModelFactory}.
     *
     * @param application     The application context.
     * @param remoteDataSource The remote data source for TV content sections.
     */
    public SectionTvViewModelFactory(@NonNull Application application, AbstractSectionContentRemoteDataSource<Tv> remoteDataSource) {
        super(new SectionTvViewModel(application, remoteDataSource));
        this.application = application;
        this.remoteDataSource = remoteDataSource;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<? extends AbstractSectionContentViewModel> getViewModelClass() {
        return SectionTvViewModel.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AbstractSectionContentViewModelFactory<SectionTvViewModel> newInstance() {
        return new SectionTvViewModelFactory(application, remoteDataSource);
    }

}