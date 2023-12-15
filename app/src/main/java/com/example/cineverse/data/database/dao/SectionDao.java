package com.example.cineverse.data.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.cineverse.data.model.content.section.SectionEntity;

@Dao
public interface SectionDao {

    @Transaction
    @Insert
    void insert(SectionEntity section);

    @Query("SELECT * FROM section WHERE sectionName = :sectionName")
    SectionEntity getSectionByName(String sectionName);

}
