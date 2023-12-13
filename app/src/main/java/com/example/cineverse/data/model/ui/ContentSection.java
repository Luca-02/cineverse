package com.example.cineverse.data.model.ui;

import com.example.cineverse.viewmodel.verified_account.section.home.AbstractSectionViewModel;

public class ContentSection {

    public static final int POSTER_TYPE = 0;
    public static final int CAROUSEL_TYPE = 1;
    public static final int GENRE_TYPE = 2;

    private final Integer sectionTitleStringId;
    private final Class<? extends AbstractSectionViewModel> viewModelClass;
    private final int viewType;
    private boolean forceRefresh;

    public ContentSection(Integer sectionTitleStringId,
                          Class<? extends AbstractSectionViewModel> viewModelClass,
                          int viewType) {
        this.sectionTitleStringId = sectionTitleStringId;
        this.viewModelClass = viewModelClass;
        this.viewType = viewType;
        forceRefresh = false;
    }

    public int getSectionTitleStringId() {
        return sectionTitleStringId;
    }

    public Class<? extends AbstractSectionViewModel> getViewModelClass() {
        return viewModelClass;
    }

    public int getViewType() {
        return viewType;
    }

    public boolean isForceRefresh() {
        if (forceRefresh) {
            forceRefresh = false;
            return true;
        }
        return false;
    }

    public void setForceRefresh(boolean forceRefresh) {
        this.forceRefresh = forceRefresh;
    }

}