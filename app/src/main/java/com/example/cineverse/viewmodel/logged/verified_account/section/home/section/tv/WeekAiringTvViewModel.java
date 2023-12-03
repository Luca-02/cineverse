package com.example.cineverse.viewmodel.logged.verified_account.section.home.section.tv;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.cineverse.R;
import com.example.cineverse.viewmodel.logged.verified_account.section.home.section.AbstractSectionTvViewModel;

public class WeekAiringTvViewModel
        extends AbstractSectionTvViewModel {

    /**
     * Constructs an {@link WeekAiringTvViewModel} object with the given {@link Application}.
     *
     * @param application The {@link Application} of the calling component.
     */
    public WeekAiringTvViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public void fetch() {
        repository.fetchWeekAiringTv(
                getApplication().getString(R.string.language),
                1,
                this::updateMovieLiveData
        );
    }

}
