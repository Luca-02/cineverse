package com.example.cineverse.adapter.content;

import com.example.cineverse.data.model.content.AbstractContent;

/**
 * Interface definition for a callback to be invoked when a content item is clicked.
 */
public interface OnContentClickListener {
    /**
     * Called when a content item is clicked.
     *
     * @param content The clicked {@link AbstractContent} item.
     */
    void onContentClick(AbstractContent content);
}