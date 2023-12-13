package com.example.cineverse.viewmodel.verified_account.section.home.genre;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import com.example.cineverse.data.model.Failure;
import com.example.cineverse.data.model.genre.Genre;
import com.example.cineverse.data.model.genre.GenreApiResponse;
import com.example.cineverse.data.source.genre.GenresResponseCallback;
import com.example.cineverse.data.source.genre.IGenresRemoteDataSource;
import com.example.cineverse.exception.UnsupportedViewModelException;
import com.example.cineverse.exception.ViewModelFactoryCreationException;
import com.example.cineverse.repository.genre.GenreRepository;
import com.example.cineverse.viewmodel.verified_account.section.home.AbstractSectionViewModel;
import com.example.cineverse.viewmodel.verified_account.section.home.content.AbstractSectionContentViewModel;
import com.example.cineverse.viewmodel.verified_account.section.home.content.section.MovieFromGenreViewModel;
import com.example.cineverse.viewmodel.verified_account.section.home.content.section.TvFromGenreViewModel;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractSectionGenreViewModel
        extends AbstractSectionViewModel
        implements GenresResponseCallback {

    protected GenreRepository repository;
    private MutableLiveData<List<Genre>> contentLiveData;
    private final Map<Class<? extends AbstractSectionContentViewModel>, Class<? extends ViewModelProvider.Factory>> factoryMap;

    public AbstractSectionGenreViewModel(@NonNull Application application) {
        super(application);
        repository = new GenreRepository(createRemoteDataSourceInstance());
        factoryMap = new HashMap<>();
        factoryMap.put(TvFromGenreViewModel.class, TvFromGenreViewModel.Factory.class);
        factoryMap.put(MovieFromGenreViewModel.class, MovieFromGenreViewModel.Factory.class);
    }

    public MutableLiveData<List<Genre>> getContentLiveData() {
        if (contentLiveData == null) {
            contentLiveData = new MutableLiveData<>();
        }
        return contentLiveData;
    }

    @Override
    public void emptyContentLiveDataList() {
        getContentLiveData().setValue(new ArrayList<>());
    }

    @Override
    public boolean isContentEmpty() {
        List<Genre> content = getContentLiveData().getValue();
        if (content != null) {
            return content.isEmpty();
        }
        return true;
    }

    public void fetch() {
        repository.fetch();
    }

    @Override
    public void onResponse(GenreApiResponse response) {
        if (response != null) {
            handleResponse(response.getGenres(), getContentLiveData());
        }
    }

    @Override
    public void onFailure(Failure failure) {
        if (failure != null) {
            handleFailure(failure);
        }
    }

    public ViewModelProvider.Factory getSectionContentFactory(int genreId) {
        Class<? extends AbstractSectionContentViewModel> viewModelClass =
                getSectionContentViewModelClass();
        Class<? extends ViewModelProvider.Factory> factoryClass =
                SectionGenreViewModelFactoryMapper.getFactoryClass(viewModelClass);
        if (factoryClass != null) {
            return createFactoryInstance(factoryClass, getApplication(), genreId);
        } else {
            throw new UnsupportedViewModelException(viewModelClass);
        }
    }

    private <T extends ViewModelProvider.Factory> T createFactoryInstance(
            Class<T> factoryClass, Application application, int genreId) {
        try {
            return factoryClass.getConstructor(Application.class, int.class).newInstance(application, genreId);
        } catch (InvocationTargetException | IllegalAccessException |
                 InstantiationException | NoSuchMethodException e) {
            throw new ViewModelFactoryCreationException(e);
        }
    }

    public abstract Class<? extends AbstractSectionContentViewModel> getSectionContentViewModelClass();
    protected abstract IGenresRemoteDataSource createRemoteDataSourceInstance();

}
