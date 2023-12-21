package com.example.cineverse.data.database;

import static com.example.cineverse.utils.constant.Database.CONTENT_DATABASE_NAME;
import static com.example.cineverse.utils.constant.Database.CONTENT_DATABASE_VERSION;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.cineverse.data.database.dao.ContentDao;
import com.example.cineverse.data.database.dao.SectionContentCrossRefDao;
import com.example.cineverse.data.database.dao.SectionDao;
import com.example.cineverse.data.model.content.ContentEntityDb;
import com.example.cineverse.data.model.content.SectionContentCrossRef;
import com.example.cineverse.data.model.content.SectionEntity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The {@link ContentSectionDatabase} class represents the Room database for managing content-related
 * entities, including sections, content entities, and their relationships.
 */
@Database(entities = {SectionEntity.class, SectionContentCrossRef.class, ContentEntityDb.class}, version = CONTENT_DATABASE_VERSION)
public abstract class ContentSectionDatabase extends RoomDatabase {

    private static final int NUMBER_OF_THREADS = Runtime.getRuntime().availableProcessors();
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public abstract SectionDao sectionDao();
    public abstract SectionContentCrossRefDao sectionContentCrossRefDao();
    public abstract ContentDao contentDao();

    private static ContentSectionDatabase instance;

    /**
     * Retrieves the singleton instance of the database.
     *
     * @param context The application {@link Context}.
     * @return The database instance.
     */
    public static synchronized ContentSectionDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            ContentSectionDatabase.class, CONTENT_DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

}