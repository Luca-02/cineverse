package com.example.cineverse.data.model.content;

import static com.example.cineverse.utils.constant.Database.SECTION_CONTENT_CROSS_REF_TABLE_NAME;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;

import org.jetbrains.annotations.NotNull;

/**
 * The {@link SectionContentCrossRef} class represents the cross-reference table between content sections
 * and their associated content items in the database.
 */
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

    /**
     * The name of the content section.
     */
    private @NotNull String sectionName;

    /**
     * The ID of the content item.
     */
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
