package com.example.cineverse.data.model.content;

import com.example.cineverse.data.model.content.section.MovieEntity;
import com.example.cineverse.data.model.content.section.TvEntity;

import java.util.HashMap;
import java.util.Map;

public class ContentTypeMapper {

    private static final Map<Class<? extends AbstractContent>, String> typeMap = new HashMap<>();

    static {
        typeMap.put(MovieEntity.class, MovieEntity.TYPE);
        typeMap.put(TvEntity.class, TvEntity.TYPE);
    }

    public static String getContentType(Class<? extends AbstractContent> contentClass) {
        return typeMap.get(contentClass);
    }

}
