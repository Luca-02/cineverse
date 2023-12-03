package com.example.cineverse.viewmodel.logged.verified_account.section.home.section.movie;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.cineverse.R;
import com.example.cineverse.viewmodel.logged.verified_account.section.home.AbstractSectionViewModel;
import com.example.cineverse.viewmodel.logged.verified_account.section.home.section.AbstractSectionMovieViewModel;

public class PopularMovieViewModel
        extends AbstractSectionMovieViewModel {

    /**
     * Constructs an {@link PopularMovieViewModel} object with the given {@link Application}.
     *
     * @param application The {@link Application} of the calling component.
     */
    public PopularMovieViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public void fetch() {
        repository.fetchPopularMovies(
                getApplication().getString(R.string.language),
                1,
                this::updateMovieLiveData
        );
    }

}