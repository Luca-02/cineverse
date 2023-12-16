package com.example.cineverse.exception;

import com.example.cineverse.data.model.ui.ContentSection;

/**
 * The {@link ContentSectionViewTypeNotFoundException} class is a custom exception that indicates a
 * view type is not found in the RecyclerView adapter.
 */
public class ContentSectionViewTypeNotFoundException extends RuntimeException {

    /**
     * Constructs a {@link ContentSectionViewTypeNotFoundException} with the specified view type.
     *
     * @param viewType The view type that is not found.
     */
    public ContentSectionViewTypeNotFoundException(ContentSection.ViewType viewType) {
        super("View type [" + viewType + "] not found");
    }

}
