package com.example.cineverse.repository.details;

import android.content.Context;

import com.example.cineverse.data.model.details.section.TvDetails;
import com.example.cineverse.data.source.details.section.TvDetailsRemoteDataSource;

public class TvDetailsRepository
        extends AbstractDetailsRepository<TvDetails> {

    public TvDetailsRepository(Context context) {
        super(context, new TvDetailsRemoteDataSource(context));
    }

}
