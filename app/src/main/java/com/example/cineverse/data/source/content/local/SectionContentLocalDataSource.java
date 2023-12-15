package com.example.cineverse.data.source.content.local;

import android.content.Context;

import com.example.cineverse.data.database.ContentSectionDatabase;
import com.example.cineverse.data.database.dao.ContentDao;
import com.example.cineverse.data.database.dao.SectionContentCrossRefDao;
import com.example.cineverse.data.database.dao.SectionDao;
import com.example.cineverse.data.model.content.AbstractContent;
import com.example.cineverse.data.model.content.AbstractContentResponse;
import com.example.cineverse.data.model.content.ContentTypeMapper;
import com.example.cineverse.data.model.content.section.ContentEntityDb;
import com.example.cineverse.data.model.content.section.SectionContentCrossRef;
import com.example.cineverse.data.model.content.section.SectionEntity;
import com.example.cineverse.data.source.content.remote.SectionContentResponseCallback;
import com.example.cineverse.utils.ServiceLocator;

import java.util.ArrayList;
import java.util.List;

public class SectionContentLocalDataSource<T extends AbstractContent>
        implements ISectionContentLocalDataSource {

    protected final Context context;
    protected final Class<T> contentType;
    private final SectionDao sectionDao;
    private final SectionContentCrossRefDao sectionContentCrossRefDao;
    private final ContentDao contentDao;
    protected SectionContentResponseCallback<T> callback;

    public SectionContentLocalDataSource(Context context, Class<T> contentType) {
        this.context = context;
        this.contentType = contentType;
        sectionDao = ServiceLocator.getInstance().getSectionDao(context);
        sectionContentCrossRefDao = ServiceLocator.getInstance().getSectionContentCrossRefDao(context);
        contentDao = ServiceLocator.getInstance().getContentDao(context);
    }

    public void setCallback(SectionContentResponseCallback<T> callback) {
        this.callback = callback;
    }

    @Override
    public void fetch(String section) {
        if (callback != null && section != null) {
            ContentSectionDatabase.databaseWriteExecutor.execute(
                    () -> getLocalSectionContent(section));
        }
    }

    @Override
    public void insertContent(List<? extends AbstractContent> contentList, String section) {
        ContentSectionDatabase.databaseWriteExecutor.execute(() ->
                insertContentInSection(contentList, section));
    }

    private void getLocalSectionContent(String section) {
        List<ContentEntityDb> contentEntityDbList = sectionContentCrossRefDao.getContentForSection(
                section, ContentTypeMapper.getContentType(contentType));

        AbstractContentResponse<T> response = (AbstractContentResponse<T>) AbstractContentResponse
                .createResponse(contentEntityDbList, contentType);
        callback.onResponse(response);
    }

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
