package com.example.cineverse.data.model.content;

import static com.example.cineverse.utils.constant.Database.SECTION_TABLE_NAME;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * The {@link SectionEntity} class represents a Room entity for storing section information in the database.
 */
@Entity(tableName = SECTION_TABLE_NAME)
public class SectionEntity {

    @PrimaryKey
    private @NonNull String sectionName;

    public SectionEntity(@NonNull String sectionName) {
        this.sectionName = sectionName;
    }

    @NonNull
    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(@NonNull String sectionName) {
        this.sectionName = sectionName;
    }

}
