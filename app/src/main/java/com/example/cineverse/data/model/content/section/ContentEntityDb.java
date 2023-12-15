package com.example.cineverse.data.model.content.section;

import static com.example.cineverse.utils.constant.Database.CONTENT_TABLE_NAME;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.cineverse.data.model.content.AbstractContent;
import com.example.cineverse.data.model.content.ContentTypeMapper;

import java.util.ArrayList;
import java.util.List;

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

    public static <T extends AbstractContent> T convertFromDbContent(
            ContentEntityDb entityDb, Class<T> contentClass) {
        if (MovieEntity.class.isAssignableFrom(contentClass)) {
            return (T) new MovieEntity(entityDb);
        } else if (TvEntity.class.isAssignableFrom(contentClass)) {
            return (T) new TvEntity(entityDb);
        }
        return null;
    }

    public static <T extends AbstractContent> List<ContentEntityDb> createContentEntityDbList(
            List<? extends AbstractContent> contentList, Class<T> contentClass) {
        List<ContentEntityDb> result = new ArrayList<>();
        for (int i = 0; i < contentList.size(); i++) {
            result.add(new ContentEntityDb(
                    contentList.get(i), ContentTypeMapper.getContentType(contentClass), i));
        }
        return result;
    }

}
