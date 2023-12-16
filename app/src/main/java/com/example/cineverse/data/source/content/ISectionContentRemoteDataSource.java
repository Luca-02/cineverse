package com.example.cineverse.data.source.content;

/**
 * The {@link ISectionContentRemoteDataSource} interface defines methods for remote data source
 * operations related to section content.
 */
public interface ISectionContentRemoteDataSource {
    /**
     * Fetches content for the specified page.
     *
     * @param page The page number to fetch content for.
     */
    void fetch(int page);
}