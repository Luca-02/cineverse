package com.example.cineverse.data.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.cineverse.data.model.content.section.ContentEntityDb;
import com.example.cineverse.data.model.content.section.SectionContentCrossRef;

import org.jetbrains.annotations.NotNull;

import java.util.List;

@Dao
public interface SectionContentCrossRefDao {

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<SectionContentCrossRef> sectionMovieCrossRefs);

    @Transaction
    @Query("DELETE FROM section_content_cross_ref WHERE sectionName = :sectionName")
    void deleteBySectionId(String sectionName);

    @Query("SELECT COUNT(*) FROM section_content_cross_ref WHERE contentId = :contentId")
    int getContentCount(int contentId);

    @Query("SELECT content.* FROM content " +
            "INNER JOIN section_content_cross_ref ON content.id = section_content_cross_ref.contentId " +
            "INNER JOIN section ON section_content_cross_ref.sectionName = section.sectionName " +
            "WHERE section.sectionName = :sectionName AND content.contentType = :contentType " +
            "ORDER BY content.position")
    List<ContentEntityDb> getContentForSection(@NotNull String sectionName, @NotNull String contentType);

}
