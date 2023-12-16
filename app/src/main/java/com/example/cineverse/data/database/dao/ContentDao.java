package com.example.cineverse.data.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Transaction;

import com.example.cineverse.data.model.content.ContentEntityDb;

/**
 * The {@link ContentDao} interface provides Data Access Object (DAO) methods for interacting
 * with the content database table.
 */
@Dao
public interface ContentDao {

    /**
     * Inserts a content entity into the database table, replacing any existing content with
     * the same primary key.
     *
     * @param content The {@link ContentEntityDb} to be inserted.
     */
    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ContentEntityDb content);

    /**
     * Deletes a content entity from the database table.
     *
     * @param content The {@link ContentEntityDb} to be deleted.
     */
    @Transaction
    @Delete
    void delete(ContentEntityDb content);

}
