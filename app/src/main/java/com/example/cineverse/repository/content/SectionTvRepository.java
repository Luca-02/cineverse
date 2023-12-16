package com.example.cineverse.repository.content;

import android.content.Context;

import com.example.cineverse.data.model.content.section.Tv;
import com.example.cineverse.data.source.content.AbstractSectionContentRemoteDataSource;
import com.example.cineverse.data.source.content.SectionContentRemoteResponseCallback;

/**
 * The {@link SectionTvRepository} class extends the {@link AbstractSectionContentRepository}
 * and serves as the repository for managing TV-related section-specific content.
 */
public class SectionTvRepository
        extends AbstractSectionContentRepository<Tv> {

    /**
     * Constructor for {@link SectionTvRepository}.
     *
     * @param context         The application context.
     * @param remoteDataSource The remote data source for TV-related section-specific content.
     * @param callback        The callback for handling remote responses.
     */
    public SectionTvRepository(Context context,
                               AbstractSectionContentRemoteDataSource<Tv> remoteDataSource,
                               SectionContentRemoteResponseCallback<Tv> callback) {
        super(context, remoteDataSource, Tv.class, callback);
    }

}
