package com.example.cineverse.repository.content;

import android.content.Context;

import com.example.cineverse.data.model.content.section.Tv;
import com.example.cineverse.data.source.content.AbstractSectionContentRemoteDataSource;
import com.example.cineverse.data.source.content.SectionContentRemoteResponseCallback;

public class SectionTvRepository
        extends AbstractSectionContentRepository<Tv> {

    public SectionTvRepository(Context context,
                               AbstractSectionContentRemoteDataSource<Tv> remoteDataSource,
                               SectionContentRemoteResponseCallback<Tv> callback) {
        super(context, remoteDataSource, Tv.class, callback);
    }

}
