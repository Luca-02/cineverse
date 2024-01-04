package com.example.cineverse.viewmodel.details.section;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.cineverse.data.model.details.section.MovieDetails;
import com.example.cineverse.repository.details.MovieDetailsRepository;
import com.example.cineverse.viewmodel.details.AbstractContentDetailsViewModel;

public class MovieDetailsViewModel
        extends AbstractContentDetailsViewModel<MovieDetails> {

    public MovieDetailsViewModel(@NonNull Application application) {
        super(application, new MovieDetailsRepository(application.getApplicationContext()));
    }

}
