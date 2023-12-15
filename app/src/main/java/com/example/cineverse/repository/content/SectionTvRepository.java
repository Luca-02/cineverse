package com.example.cineverse.repository.content;

import android.content.Context;

import com.example.cineverse.data.model.content.section.TvEntity;
import com.example.cineverse.data.source.content.remote.AbstractSectionContentRemoteDataSource;
import com.example.cineverse.data.source.content.remote.SectionContentResponseCallback;

public class SectionTvRepository
        extends AbstractSectionContentRepository<TvEntity> {

    public SectionTvRepository(Context context,
                               AbstractSectionContentRemoteDataSource<TvEntity> remoteDataSource,
                               SectionContentResponseCallback<TvEntity> callback) {
        super(context, remoteDataSource, TvEntity.class, callback);
    }

}
