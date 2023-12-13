package com.example.cineverse.viewmodel.verified_account.section.home.content;

import static com.example.cineverse.utils.constant.Api.STARTING_PAGE;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.example.cineverse.data.model.content.AbstractContent;
import com.example.cineverse.viewmodel.verified_account.section.home.AbstractSectionViewModel;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractSectionContentViewModel
        extends AbstractSectionViewModel {

    private MutableLiveData<List<? extends AbstractContent>> contentLiveData;
    private int page;

    /**
     * Constructs an {@link AbstractSectionContentViewModel} object with the given {@link Application}.
     *
     * @param application The {@link Application} of the calling component.
     */
    public AbstractSectionContentViewModel(Application application) {
        super(application);
        page = STARTING_PAGE;
    }

    public MutableLiveData<List<? extends AbstractContent>> getContentLiveData() {
        if (contentLiveData == null) {
            contentLiveData = new MutableLiveData<>();
        }
        return contentLiveData;
    }

    @Override
    public void emptyContentLiveDataList() {
        getContentLiveData().setValue(new ArrayList<>());
    }

    public int getPage() {
        return page;
    }

    public void increasePage() {
        page++;
    }

    @Override
    public boolean isContentEmpty() {
        List<? extends AbstractContent> content = getContentLiveData().getValue();
        if (content != null) {
            return content.isEmpty();
        }
        return true;
    }

    public void fetchAndIncreasePage() {
        fetch();
        increasePage();
    }

}
