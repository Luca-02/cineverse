package com.example.cineverse.data.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.cineverse.data.model.content.SectionEntity;

/**
 * The {@link SectionDao} interface provides Data Access Object (DAO) methods for interacting with
 * the section database table.
 */
@Dao
public interface SectionDao {

    /**
     * Inserts a section entity into the database table.
     *
     * @param section The section entity to be inserted.
     */
    @Transaction
    @Insert
    void insert(SectionEntity section);

    /**
     * Retrieves a section entity from the database based on the specified section name.
     *
     * @param sectionName The name of the section for which the entity is requested.
     * @return The section entity with the specified section name, or {@code null} if not found.
     */
    @Query("SELECT * FROM section WHERE sectionName = :sectionName")
    SectionEntity getSectionByName(String sectionName);

}
