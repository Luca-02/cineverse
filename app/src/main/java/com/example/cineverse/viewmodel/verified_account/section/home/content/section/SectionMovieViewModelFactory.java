package com.example.cineverse.viewmodel.verified_account.section.home.content.section;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.cineverse.data.model.content.section.Movie;
import com.example.cineverse.data.source.content.AbstractSectionContentRemoteDataSource;
import com.example.cineverse.viewmodel.verified_account.section.home.content.AbstractSectionContentViewModel;
import com.example.cineverse.viewmodel.verified_account.section.home.content.AbstractSectionContentViewModelFactory;

/**
 * The {@link SectionMovieViewModelFactory} class extends {@link AbstractSectionContentViewModelFactory}
 * and is responsible for creating instances of {@link SectionMovieViewModel} with the provided
 * application context and remote data source for movie content sections.
 */
public class SectionMovieViewModelFactory extends AbstractSectionContentViewModelFactory<SectionMovieViewModel> {

    private final Application application;
    private final AbstractSectionContentRemoteDataSource<Movie> remoteDataSource;

    /**
     * Constructs a new instance of {@link SectionMovieViewModelFactory}.
     *
     * @param application     The application context.
     * @param remoteDataSource The remote data source for movie content sections.
     */
    public SectionMovieViewModelFactory(@NonNull Application application, AbstractSectionContentRemoteDataSource<Movie> remoteDataSource) {
        super(new SectionMovieViewModel(application, remoteDataSource));
        this.application = application;
        this.remoteDataSource = remoteDataSource;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<? extends AbstractSectionContentViewModel> getViewModelClass() {
        return SectionMovieViewModel.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AbstractSectionContentViewModelFactory<SectionMovieViewModel> newInstance() {
        return new SectionMovieViewModelFactory(application, remoteDataSource);
    }

}