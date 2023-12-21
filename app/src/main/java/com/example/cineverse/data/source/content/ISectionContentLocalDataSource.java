package com.example.cineverse.data.source.content;

import com.example.cineverse.data.model.content.AbstractContent;

import java.util.List;

/**
 * The {@link ISectionContentLocalDataSource} interface defines methods for local data source
 * operations related to section content.
 */
public interface ISectionContentLocalDataSource {
    /**
     * Fetches content for the specified {@code section}.
     *
     * @param section The name of the section to fetch content for.
     */
    void fetch(String section);

    /**
     * Inserts the provided {@code contentList} into the local database for the specified {@code section}.
     *
     * @param contentList The list of content items to be inserted.
     * @param section     The name of the section where the content will be inserted.
     */
    void insertContent(List<? extends AbstractContent> contentList, String section);
}
