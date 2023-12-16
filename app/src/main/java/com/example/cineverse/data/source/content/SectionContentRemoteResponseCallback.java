package com.example.cineverse.data.source.content;

import com.example.cineverse.data.model.content.AbstractContent;
import com.example.cineverse.data.model.content.AbstractContentResponse;
import com.example.cineverse.data.source.api.BaseRemoteResponseCallback;

/**
 * The {@link SectionContentRemoteResponseCallback} interface extends the
 * {@link BaseRemoteResponseCallback} and defines methods for handling remote
 * data source responses related to section content.
 *
 * @param <T> The type of content, extending {@link AbstractContent}.
 */
public interface SectionContentRemoteResponseCallback<T extends AbstractContent>
        extends BaseRemoteResponseCallback<AbstractContentResponse<T>> {
}