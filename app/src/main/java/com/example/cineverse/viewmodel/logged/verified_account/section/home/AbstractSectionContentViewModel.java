package com.example.cineverse.viewmodel.logged.verified_account.section.home;

import android.app.Application;

import com.example.cineverse.data.model.content.AbstractContent;
import com.example.cineverse.data.model.content.AbstractContentApiResponse;
import com.example.cineverse.data.model.content.Failure;
import com.example.cineverse.data.source.content.poster.IPosterContentRemoteDataSource;
import com.example.cineverse.data.source.content.poster.PosterContentResponseCallback;
import com.example.cineverse.repository.content.poster.PosterContentRepository;

import java.util.List;

public abstract class AbstractSectionContentViewModel<T extends AbstractContent>
        extends AbstractSectionViewModel
        implements PosterContentResponseCallback<T> {

    protected PosterContentRepository repository;

    public AbstractSectionContentViewModel(Application application) {
        super(application);
        repository = new PosterContentRepository(createRemoteDataSourceInstance());
    }

    @Override
    public void fetch() {
        repository.fetch(getPage());
    }

    @Override
    public void onResponse(AbstractContentApiResponse<T> response) {
        if (response != null) {
            List<T> resultData = response.getResults();
            getContentLiveData().postValue(resultData);
        }
    }

    @Override
    public void onFailure(Failure failure) {
        getFailureLiveData().postValue(failure);
        clearFailureLiveData();
    }

    protected abstract IPosterContentRemoteDataSource createRemoteDataSourceInstance();

}
