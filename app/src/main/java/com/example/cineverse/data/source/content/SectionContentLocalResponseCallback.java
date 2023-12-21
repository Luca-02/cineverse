package com.example.cineverse.data.source.content;

import com.example.cineverse.data.model.content.AbstractContent;
import com.example.cineverse.data.model.content.AbstractContentResponse;

/**
 * The {@link SectionContentLocalResponseCallback} interface defines methods for handling local
 * data source responses related to section content.
 *
 * @param <T> The type of content, extending {@link AbstractContent}.
 */
public interface SectionContentLocalResponseCallback<T extends AbstractContent> {
    /**
     * Called when a local response related to section content is received.
     *
     * @param response The local response containing the content.
     */
    void onLocalResponse(AbstractContentResponse<T> response);
}