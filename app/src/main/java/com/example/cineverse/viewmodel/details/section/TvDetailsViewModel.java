package com.example.cineverse.viewmodel.details.section;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.cineverse.data.model.details.section.TvDetails;
import com.example.cineverse.repository.details.section.TvDetailsRepository;
import com.example.cineverse.viewmodel.details.AbstractContentDetailsViewModel;

public class TvDetailsViewModel
        extends AbstractContentDetailsViewModel<TvDetails> {

    public TvDetailsViewModel(@NonNull Application application) {
        super(application, new TvDetailsRepository(application.getApplicationContext()));
    }

}
