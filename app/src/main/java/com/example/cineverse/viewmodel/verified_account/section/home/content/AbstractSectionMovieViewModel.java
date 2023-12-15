package com.example.cineverse.viewmodel.verified_account.section.home.content;

import android.app.Application;

import com.example.cineverse.data.model.content.section.MovieEntity;
import com.example.cineverse.repository.content.SectionMovieRepository;

public abstract class AbstractSectionMovieViewModel
        extends AbstractSectionContentTypeViewModel<MovieEntity> {

    public AbstractSectionMovieViewModel(Application application) {
        this(application, 0);
    }

    public AbstractSectionMovieViewModel(Application application, int genre) {
        super(application, genre);
        repository = new SectionMovieRepository(
                application.getApplicationContext(),
                createRemoteDataSourceInstance(),
                this
        );
    }

}
