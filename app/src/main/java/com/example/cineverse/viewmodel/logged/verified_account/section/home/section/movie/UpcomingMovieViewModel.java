package com.example.cineverse.viewmodel.logged.verified_account.section.home.section.movie;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.cineverse.R;
import com.example.cineverse.viewmodel.logged.verified_account.section.home.section.AbstractSectionMovieViewModel;

public class UpcomingMovieViewModel
        extends AbstractSectionMovieViewModel {

    /**
     * Constructs an {@link UpcomingMovieViewModel} object with the given {@link Application}.
     *
     * @param application The {@link Application} of the calling component.
     */
    public UpcomingMovieViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public void fetch() {
        repository.fetchUpcomingMovies(
                getApplication().getString(R.string.language),
                1,
                this::updateMovieLiveData
        );
    }

}
