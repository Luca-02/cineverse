package com.example.cineverse.viewmodel.logged.verified_account.section.home.section.tv;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.cineverse.R;
import com.example.cineverse.viewmodel.logged.verified_account.section.home.section.AbstractSectionTvViewModel;

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
    public void fetch() {
        repository.fetchTopRatedTv(
                getApplication().getString(R.string.language),
                1,
                this::updateMovieLiveData
        );
    }

}
