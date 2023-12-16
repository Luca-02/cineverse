package com.example.cineverse.data.model.content;

import com.example.cineverse.data.model.content.section.Movie;
import com.example.cineverse.data.model.content.section.Tv;

import java.util.HashMap;
import java.util.Map;

/**
 * The {@link ContentTypeMappingManager} class manages the mapping between content classes and their corresponding types.
 */
public class ContentTypeMappingManager {

    /**
     * Enumeration representing content types, such as "movie" or "tv".
     */
    public enum ContentType {
        MOVIE("movie"),
        TV("tv");

        private final String section;

        ContentType(String sectionName) {
            this.section = sectionName;
        }

        public String getType() {
            return section;
        }
    }

    /**
     * A map that associates content classes with their corresponding content types.
     */
    private static final Map<Class<? extends AbstractContent>, ContentType> typeMap = new HashMap<>();

    // Initialize the map with predefined content classes and types.
    static {
        typeMap.put(Movie.class, ContentType.MOVIE);
        typeMap.put(Tv.class, ContentType.TV);
    }

    /**
     * Gets the content type for a given content class.
     *
     * @param contentClass The content class.
     * @return The content type as a string, or null if not found.
     */
    public static String getContentType(Class<? extends AbstractContent> contentClass) {
        ContentType sectionType = typeMap.get(contentClass);
        return (sectionType != null) ? sectionType.getType() : null;
    }

}
