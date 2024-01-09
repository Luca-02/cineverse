package com.example.cineverse.viewmodel.verified_account.section.home.content;

import static com.example.cineverse.utils.constant.Api.STARTING_PAGE;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.cineverse.data.model.content.AbstractContent;
import com.example.cineverse.utils.constant.GlobalConstant;
import com.example.cineverse.viewmodel.verified_account.AbstractFailureResponseViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@link AbstractSectionContentViewModel} class extends {@link AbstractFailureResponseViewModel} and is an abstract
 * base class for ViewModel classes representing various content sections in the home screen of the application.
 */
public abstract class AbstractSectionContentViewModel
        extends AbstractFailureResponseViewModel {

    private MutableLiveData<List<? extends AbstractContent>> contentLiveData;
    private int page;
    private boolean isLoading;

    /**
     * Constructs an {@link AbstractSectionContentViewModel} object with the given {@link Application}.
     *
     * @param application The {@link Application} of the calling component.
     */
    public AbstractSectionContentViewModel(Application application) {
        super(application);
        page = STARTING_PAGE;
        isLoading = false;
    }

    public MutableLiveData<List<? extends AbstractContent>> getContentLiveData() {
        if (contentLiveData == null) {
            contentLiveData = new MutableLiveData<>();
        }
        return contentLiveData;
    }

    /**
     * Retrieves the current page number.
     *
     * @return The current page number.
     */
    public int getPage() {
        return page;
    }

    /**
     * Increases the current page number.
     */
    public void increasePage() {
        page++;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    /**
     * Initiates the process of fetching content and increases the page number.
     */
    public void fetchAndIncreasePage() {
        fetch();
        increasePage();
    }

    public void emptyContentLiveDataList() {
        getContentLiveData().setValue(new ArrayList<>());
    }

    public boolean isContentEmpty() {
        List<? extends AbstractContent> content = getContentLiveData().getValue();
        if (content != null) {
            return content.isEmpty();
        }
        return true;
    }

    /**
     * Initiates the data fetching process.
     */
    public abstract void fetch();

}
