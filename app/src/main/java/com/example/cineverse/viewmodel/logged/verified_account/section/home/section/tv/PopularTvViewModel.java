package com.example.cineverse.viewmodel.logged.verified_account.section.home.section.tv;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.cineverse.R;
import com.example.cineverse.viewmodel.logged.verified_account.section.home.section.AbstractSectionTvViewModel;
import com.example.cineverse.viewmodel.logged.verified_account.section.home.section.movie.PopularMovieViewModel;

public class PopularTvViewModel
        extends AbstractSectionTvViewModel {

    /**
     * Constructs an {@link PopularMovieViewModel} object with the given {@link Application}.
     *
     * @param application The {@link Application} of the calling component.
     */
    public PopularTvViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public void fetch() {
        repository.fetchPopularTv(
                getApplication().getString(R.string.language),
                1,
                this::updateMovieLiveData
        );
    }

}