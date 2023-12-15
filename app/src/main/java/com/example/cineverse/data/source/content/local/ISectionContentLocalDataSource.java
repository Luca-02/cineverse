package com.example.cineverse.data.source.content.local;

import com.example.cineverse.data.model.content.AbstractContent;

import java.util.List;

public interface ISectionContentLocalDataSource {
    void fetch(String section);
    void insertContent(List<? extends AbstractContent> contentList, String section);
}
