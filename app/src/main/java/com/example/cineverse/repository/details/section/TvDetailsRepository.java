package com.example.cineverse.repository.details.section;

import android.content.Context;

import com.example.cineverse.data.model.User;
import com.example.cineverse.data.model.details.section.MovieDetails;
import com.example.cineverse.data.model.details.section.TvDetails;
import com.example.cineverse.data.source.details.ContentDetailsRemoteResponseCallback;
import com.example.cineverse.data.source.details.section.TvDetailsRemoteDataSource;
import com.example.cineverse.repository.details.AbstractDetailsRepository;
import com.example.cineverse.service.NetworkCallback;

public class TvDetailsRepository
        extends AbstractDetailsRepository<TvDetails> {

    public TvDetailsRepository(Context context) {
        super(new TvDetailsRemoteDataSource(context));
    }

}
