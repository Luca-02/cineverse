package com.example.cineverse.data.database;

import android.content.Context;

import com.example.cineverse.data.database.dao.ContentDao;
import com.example.cineverse.data.database.dao.SectionContentCrossRefDao;
import com.example.cineverse.data.model.content.ContentEntityDb;
import com.example.cineverse.utils.ServiceLocator;

import java.util.List;

/**
 * The {@link ContentEntityManager} class provides utility methods for managing content entities in the database.
 * It includes a method to remove content entities without connections to any section, preventing orphaned records.
 */
public class ContentEntityManager {

    /**
     * Removes content entities without connections to any section from the database.
     * This helps prevent orphaned records in the content and cross-reference tables.
     *
     * @param context           The application context.
     * @param contentEntityDbList A list of {@link ContentEntityDb} objects to be checked and removed if necessary.
     */
    public synchronized static void removeContentEntityWithoutConnection(Context context, List<ContentEntityDb> contentEntityDbList) {
        SectionContentCrossRefDao sectionContentCrossRefDao = ServiceLocator.getInstance().getSectionContentCrossRefDao(context);
        ContentDao contentDao = ServiceLocator.getInstance().getContentDao(context);

        for (ContentEntityDb entityDb : contentEntityDbList) {
            if (sectionContentCrossRefDao.getContentCount(entityDb.getId()) == 0) {
                contentDao.delete(entityDb);
            }
        }
    }

}
