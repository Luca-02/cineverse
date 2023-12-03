package com.example.cineverse.data.model.ui;

import com.example.cineverse.data.model.content.AbstractPoster;
import com.example.cineverse.viewmodel.logged.verified_account.section.home.AbstractSectionViewModel;

public class HomeContentSection {

    public final int sectionTitleStringId;
    public final Class<? extends AbstractSectionViewModel
            <? extends AbstractPoster>> viewModelClass;

    public HomeContentSection(int sectionTitleStringId,
                              Class<? extends AbstractSectionViewModel
                                      <? extends AbstractPoster>> viewModelClass) {
        this.sectionTitleStringId = sectionTitleStringId;
        this.viewModelClass = viewModelClass;
    }

}