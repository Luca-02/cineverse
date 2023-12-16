package com.example.cineverse.data.source.genre;

import com.example.cineverse.data.model.genre.GenreApiResponse;
import com.example.cineverse.data.source.api.BaseRemoteResponseCallback;

/**
 * The {@link GenresRemoteResponseCallback} interface extends the {@link BaseRemoteResponseCallback}
 * interface and specifies methods for handling responses related to genres.
 */
public interface GenresRemoteResponseCallback
        extends BaseRemoteResponseCallback<GenreApiResponse> {
}
