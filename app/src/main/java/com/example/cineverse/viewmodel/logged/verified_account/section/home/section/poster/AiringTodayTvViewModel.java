package com.example.cineverse.viewmodel.logged.verified_account.section.home.section.poster;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.cineverse.R;
import com.example.cineverse.viewmodel.logged.verified_account.section.home.section.AbstractSectionTvViewModel;

public class AiringTodayTvViewModel
        extends AbstractSectionTvViewModel {

    /**
     * Constructs an {@link AiringTodayTvViewModel} object with the given {@link Application}.
     *
     * @param application The {@link Application} of the calling component.
     */
    public AiringTodayTvViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public void fetch() {
        repository.fetchAiringTodayTv(getApplication().getString(R.string.language), 1);
    }

}
