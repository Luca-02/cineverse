package com.example.cineverse.data.model.ui;

import com.example.cineverse.viewmodel.verified_account.section.home.AbstractContentViewModel;
import com.example.cineverse.viewmodel.verified_account.section.home.content.AbstractSectionContentViewModel;

/**
 * The {@link ContentSection} class represents a section of content in the UI, including information
 * such as the section title, the associated {@link AbstractContentViewModel}, the view type, and
 * a flag indicating whether to force a refresh of the content in the section.
 */
public class ContentSection {

    /**
     * The {@link ViewType} enum represents the different types of views for a content section,
     * including POSTER_TYPE, CAROUSEL_TYPE, and GENRE_TYPE.
     */
    public enum ViewType {
        POSTER_TYPE(0),
        CAROUSEL_TYPE(1);

        private final int viewType;

        ViewType(int viewType) {
            this.viewType = viewType;
        }

        /**
         * Gets the integer value associated with the view type.
         *
         * @return The integer value of the view type.
         */
        public int getViewType() {
            return viewType;
        }
    }

    private final Integer sectionTitleStringId;
    private final Class<? extends AbstractSectionContentViewModel> viewModelClass;
    private final ViewType viewType;
    private boolean forceRefresh;

    /**
     * Constructs a {@link ContentSection} with the specified section title string ID, view model class,
     * and view type.
     *
     * @param sectionTitleStringId The resource ID for the section title string.
     * @param viewModelClass       The class of the associated view model.
     * @param viewType             The type of the content view.
     */
    public ContentSection(Integer sectionTitleStringId,
                          Class<? extends AbstractSectionContentViewModel> viewModelClass,
                          ViewType viewType) {
        this.sectionTitleStringId = sectionTitleStringId;
        this.viewModelClass = viewModelClass;
        this.viewType = viewType;
        forceRefresh = false;
    }

    public int getSectionTitleStringId() {
        return sectionTitleStringId;
    }

    public Class<? extends AbstractSectionContentViewModel> getViewModelClass() {
        return viewModelClass;
    }

    public ViewType getViewType() {
        return viewType;
    }

    /**
     * Checks if a force refresh of the content in the section is required, and resets the flag if true.
     *
     * @return {@code true} if a force refresh is required, {@code false} otherwise.
     */
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