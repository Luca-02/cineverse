package com.example.cineverse.data.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.cineverse.data.model.content.ContentEntityDb;
import com.example.cineverse.data.model.content.SectionContentCrossRef;

import java.util.List;

/**
 * The {@link SectionContentCrossRefDao} interface provides Data Access Object (DAO) methods for
 * interacting with the section content cross-reference database table.
 */
@Dao
public interface SectionContentCrossRefDao {

    /**
     * Inserts a list of section content cross-reference entities into the database table,
     * replacing any existing entries with the same primary key.
     *
     * @param sectionMovieCrossRefs The list of {@link SectionContentCrossRef} to be inserted.
     */
    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<SectionContentCrossRef> sectionMovieCrossRefs);

    /**
     * Deletes section content cross-reference entries from the database table based on the
     * specified section name.
     *
     * @param sectionName The name of the section for which entries will be deleted.
     */
    @Transaction
    @Query("DELETE FROM section_content_cross_ref WHERE sectionName = :sectionName")
    void deleteBySectionId(String sectionName);

    /**
     * Gets the count of section content cross-reference entries for a specified content ID.
     *
     * @param contentId The ID of the content for which the count is requested.
     * @return The count of section content cross-reference entries for the specified content ID.
     */
    @Query("SELECT COUNT(*) FROM section_content_cross_ref WHERE contentId = :contentId")
    int getContentCount(int contentId);

    /**
     * Retrieves content entities associated with a specified section name and content type
     * from the database, ordered by content position.
     *
     * @param sectionName The name of the section for which content entities are requested.
     * @param contentType The type of content for which entities are requested.
     * @return The list of content entities associated with the specified section name and content type.
     */
    @Query("SELECT content.* FROM content " +
            "INNER JOIN section_content_cross_ref ON content.id = section_content_cross_ref.contentId " +
            "INNER JOIN section ON section_content_cross_ref.sectionName = section.sectionName " +
            "WHERE section.sectionName = :sectionName AND content.contentType = :contentType " +
            "ORDER BY content.position")
    List<ContentEntityDb> getContentForSection(String sectionName, String contentType);

}
