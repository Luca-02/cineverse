package com.example.cineverse.repository.details;

import android.content.Context;

import com.example.cineverse.data.model.details.section.MovieDetails;
import com.example.cineverse.data.source.details.section.MovieDetailsRemoteDataSource;

public class MovieDetailsRepository
        extends AbstractDetailsRepository<MovieDetails> {

    public MovieDetailsRepository(Context context) {
        super(context, new MovieDetailsRemoteDataSource(context));
    }

}
