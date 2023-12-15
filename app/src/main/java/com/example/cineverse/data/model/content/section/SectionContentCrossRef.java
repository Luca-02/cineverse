package com.example.cineverse.data.model.content.section;

import static com.example.cineverse.utils.constant.Database.SECTION_CONTENT_CROSS_REF_TABLE_NAME;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = SECTION_CONTENT_CROSS_REF_TABLE_NAME,
        primaryKeys = {"sectionName", "contentId"},
        foreignKeys = {
                @ForeignKey(entity = SectionEntity.class,
                        parentColumns = "sectionName",
                        childColumns = "sectionName"),
                @ForeignKey(entity = ContentEntityDb.class,
                        parentColumns = "id",
                        childColumns = "contentId")
        })
public class SectionContentCrossRef {

    private @NotNull String sectionName;
    private int contentId;

    public SectionContentCrossRef(@NonNull String sectionName, int contentId) {
        this.sectionName = sectionName;
        this.contentId = contentId;
    }

    @NonNull
    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(@NonNull String sectionName) {
        this.sectionName = sectionName;
    }

    public int getContentId() {
        return contentId;
    }

    public void setContentId(int contentId) {
        this.contentId = contentId;
    }

}
