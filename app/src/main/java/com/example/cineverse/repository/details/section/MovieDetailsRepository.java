package com.example.cineverse.repository.details.section;

import android.content.Context;

import com.example.cineverse.data.model.User;
import com.example.cineverse.data.model.details.section.MovieDetails;
import com.example.cineverse.data.source.details.ContentDetailsRemoteResponseCallback;
import com.example.cineverse.data.source.details.section.MovieDetailsRemoteDataSource;
import com.example.cineverse.repository.details.AbstractDetailsRepository;
import com.example.cineverse.service.NetworkCallback;

public class MovieDetailsRepository
        extends AbstractDetailsRepository<MovieDetails> {

    public MovieDetailsRepository(Context context) {
        super(new MovieDetailsRemoteDataSource(context));
    }

}
