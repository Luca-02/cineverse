package com.example.cineverse.viewmodel.verified_account.section.home.content;

import android.app.Application;

import com.example.cineverse.data.model.Failure;
import com.example.cineverse.data.model.content.AbstractContent;
import com.example.cineverse.data.model.content.AbstractContentResponse;
import com.example.cineverse.data.source.content.remote.AbstractSectionContentRemoteDataSource;
import com.example.cineverse.data.source.content.remote.SectionContentResponseCallback;
import com.example.cineverse.repository.content.AbstractSectionContentRepository;

public abstract class AbstractSectionContentTypeViewModel<T extends AbstractContent>
        extends AbstractSectionContentViewModel
        implements SectionContentResponseCallback<T> {

    protected AbstractSectionContentRepository<T> repository;
    protected int genreId;

    public AbstractSectionContentTypeViewModel(Application application, int genreId) {
        super(application);
        this.genreId = genreId;
    }

    @Override
    public void fetch() {
        repository.fetch(getPage());
    }

    @Override
    public void onResponse(AbstractContentResponse<T> response) {
        if (response != null) {
            handleResponse(response.getResults(), getContentLiveData());
        }
    }

    @Override
    public void onFailure(Failure failure) {
        if (failure != null) {
            handleFailure(failure);
        }
    }

    protected abstract AbstractSectionContentRemoteDataSource<T> createRemoteDataSourceInstance();

}
