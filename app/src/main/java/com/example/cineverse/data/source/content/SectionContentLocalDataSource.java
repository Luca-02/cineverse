package com.example.cineverse.data.source.content;

import android.content.Context;

import com.example.cineverse.data.database.ContentSectionDatabase;
import com.example.cineverse.data.database.dao.ContentDao;
import com.example.cineverse.data.database.dao.SectionContentCrossRefDao;
import com.example.cineverse.data.database.dao.SectionDao;
import com.example.cineverse.data.model.content.AbstractContent;
import com.example.cineverse.data.model.content.AbstractContentResponse;
import com.example.cineverse.data.model.content.ContentEntityDb;
import com.example.cineverse.utils.mapper.ContentTypeMappingManager;
import com.example.cineverse.data.model.content.SectionContentCrossRef;
import com.example.cineverse.data.model.content.SectionEntity;
import com.example.cineverse.utils.ServiceLocator;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@link SectionContentLocalDataSource} class is responsible for managing local data
 * related to a specific section and content type.
 *
 * @param <T> The type of content, extending {@link AbstractContent}.
 */
public class SectionContentLocalDataSource<T extends AbstractContent>
        implements ISectionContentLocalDataSource {

    protected final Context context;
    protected final Class<T> contentType;
    private final SectionDao sectionDao;
    private final SectionContentCrossRefDao sectionContentCrossRefDao;
    private final ContentDao contentDao;
    protected SectionContentLocalResponseCallback<T> localResponseCallback;

    /**
     * Constructs a {@link SectionContentLocalDataSource} with the specified context and content type.
     *
     * @param context     The application context.
     * @param contentType The class representing the content type.
     */
    public SectionContentLocalDataSource(Context context, Class<T> contentType) {
        this.context = context;
        this.contentType = contentType;
        sectionDao = ServiceLocator.getInstance().getSectionDao(context);
        sectionContentCrossRefDao = ServiceLocator.getInstance().getSectionContentCrossRefDao(context);
        contentDao = ServiceLocator.getInstance().getContentDao(context);
    }

    public void setLocalResponseCallback(SectionContentLocalResponseCallback<T> localResponseCallback) {
        this.localResponseCallback = localResponseCallback;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void fetch(String section) {
        if (localResponseCallback != null && section != null) {
            ContentSectionDatabase.databaseWriteExecutor.execute(
                    () -> getLocalSectionContent(section));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void insertContent(List<? extends AbstractContent> contentList, String section) {
        ContentSectionDatabase.databaseWriteExecutor.execute(() ->
                insertContentInSection(contentList, section));
    }

    /**
     * Retrieves local section content and invokes the callback with the response.
     *
     * @param section The section name.
     */
    private void getLocalSectionContent(String section) {
        String contentTypeString = ContentTypeMappingManager.getContentType(contentType);
        if (contentTypeString != null) {
            List<ContentEntityDb> contentEntityDbList = sectionContentCrossRefDao.getContentForSection(
                    section, contentTypeString);

            AbstractContentResponse<T> response = (AbstractContentResponse<T>)
                    AbstractContentResponse.createResponse(contentEntityDbList, contentType);
            localResponseCallback.onLocalResponse(response);
        }
    }

    /**
     * Inserts content into the specified section and manages cross-references.
     *
     * @param contentList The list of content to insert.
     * @param sectionName The name of the section.
     */
    private synchronized void insertContentInSection(List<? extends AbstractContent> contentList, String sectionName) {
        // Check if content or section is not null, otherwise there is not section content
        if (contentList != null && sectionName != null) {
            List<ContentEntityDb> contentEntityDbList = ContentEntityDb.createContentEntityDbList(contentList, contentType);

            // Get the ID of the section
            SectionEntity existingSection = sectionDao.getSectionByName(sectionName);
            String section;

            if (existingSection == null) {
                // If the section doesn't exist, create a new one
                SectionEntity newSection = new SectionEntity(sectionName);
                sectionDao.insert(newSection);
                section = newSection.getSectionName();
            } else {
                // If the section already exists, get its ID
                section = existingSection.getSectionName();

                // Delete all existing references between the section and content
                sectionContentCrossRefDao.deleteBySectionId(section);
            }

            // Associate the movies with the section in the cross-reference table
            List<SectionContentCrossRef> crossRefs = new ArrayList<>();
            for (ContentEntityDb entityDb : contentEntityDbList) {
                crossRefs.add(new SectionContentCrossRef(section, entityDb.getId()));
                contentDao.insert(entityDb);
            }

            sectionContentCrossRefDao.insertAll(crossRefs);

            // Check if there are content without connections and remove them
            for (ContentEntityDb entityDb : contentEntityDbList) {
                if (sectionContentCrossRefDao.getContentCount(entityDb.getId()) == 0) {
                    contentDao.delete(entityDb);
                }
            }
        }
    }

}
