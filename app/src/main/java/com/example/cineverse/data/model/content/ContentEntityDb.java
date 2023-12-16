package com.example.cineverse.data.model.content;

import static com.example.cineverse.utils.constant.Database.CONTENT_TABLE_NAME;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.cineverse.data.model.content.section.Movie;
import com.example.cineverse.data.model.content.section.Tv;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@link ContentEntityDb} class represents a Room entity for storing content information in the database.
 */
@Entity(tableName = CONTENT_TABLE_NAME)
public class ContentEntityDb {

    @PrimaryKey
    private final int id;
    private final String name;
    private final String releaseDate;
    private final String posterPath;
    private final String backdropPath;
    private final String contentType;
    private final int position;

    public ContentEntityDb(int id, String name, String releaseDate, String posterPath,
                           String backdropPath, String contentType, int position) {
        this.id = id;
        this.name = name;
        this.releaseDate = releaseDate;
        this.posterPath = posterPath;
        this.backdropPath = backdropPath;
        this.contentType = contentType;
        this.position = position;
    }

    /**
     * Constructs a {@link ContentEntityDb} instance from an abstract content and content type.
     *
     * @param content      The {@link AbstractContent}.
     * @param contentType  The type of the content (e.g., "movie" or "tv show").
     * @param position     The position of the content within its section.
     */
    public <T extends AbstractContent> ContentEntityDb(T content, String contentType, int position) {
        this(
                content.getId(),
                content.getName(),
                content.getReleaseDate(),
                content.getPosterPath(),
                content.getBackdropPath(),
                contentType,
                position
        );
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public int getPosition() {
        return position;
    }

    public String getContentType() {
        return contentType;
    }

    /**
     * Converts a {@link ContentEntityDb} instance back to the corresponding content class.
     *
     * @param entityDb      The {@link ContentEntityDb} instance.
     * @param contentClass  The content class.
     * @param <T>           The type of the content class.
     * @return The corresponding content class instance.
     */
    public static <T extends AbstractContent> T convertFromContentEntityDb(
            ContentEntityDb entityDb, Class<T> contentClass) {
        if (Movie.class.isAssignableFrom(contentClass)) {
            return (T) new Movie(entityDb);
        } else if (Tv.class.isAssignableFrom(contentClass)) {
            return (T) new Tv(entityDb);
        }
        return null;
    }

    /**
     * Creates a list of {@link ContentEntityDb} instances from a list of content items.
     *
     * @param contentList   The list of content items.
     * @param contentClass  The content class.
     * @param <T>           The type of the {@link AbstractContent} class.
     * @return The list of corresponding {@link ContentEntityDb} instances.
     */
    public static <T extends AbstractContent> List<ContentEntityDb> createContentEntityDbList(
            List<? extends AbstractContent> contentList, Class<T> contentClass) {
        List<ContentEntityDb> result = new ArrayList<>();
        for (int i = 0; i < contentList.size(); i++) {
            result.add(new ContentEntityDb(
                    contentList.get(i), ContentTypeMappingManager.getContentType(contentClass), i));
        }
        return result;
    }

}
