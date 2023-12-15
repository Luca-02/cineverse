package com.example.cineverse.data.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Transaction;

import com.example.cineverse.data.model.content.section.ContentEntityDb;

@Dao
public interface ContentDao {

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ContentEntityDb content);

    @Transaction
    @Delete
    void delete(ContentEntityDb content);

}
