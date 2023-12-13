package com.example.cineverse.viewmodel.verified_account.section.home.content;

import android.app.Application;

import com.example.cineverse.data.model.Failure;
import com.example.cineverse.data.model.content.AbstractContent;
import com.example.cineverse.data.model.content.AbstractContentApiResponse;
import com.example.cineverse.data.source.content.ISectionContentRemoteDataSource;
import com.example.cineverse.data.source.content.SectionContentResponseCallback;
import com.example.cineverse.repository.content.SectionContentRepository;

public abstract class AbstractSectionContentTypeViewModel<T extends AbstractContent>
        extends AbstractSectionContentViewModel
        implements SectionContentResponseCallback<T> {

    protected SectionContentRepository repository;
    protected int genreId;

    public AbstractSectionContentTypeViewModel(Application application) {
        this(application, 0);
    }

    public AbstractSectionContentTypeViewModel(Application application, int genreId) {
        super(application);
        this.genreId = genreId;
        repository = new SectionContentRepository(createRemoteDataSourceInstance());
    }

    @Override
    public void fetch() {
        repository.fetch(getPage());
    }

    @Override
    public void onResponse(AbstractContentApiResponse<T> response) {
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

    protected abstract ISectionContentRemoteDataSource createRemoteDataSourceInstance();

}
