package com.example.cineverse.viewmodel.verified_account.section.home.genre;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import com.example.cineverse.data.model.api.Failure;
import com.example.cineverse.data.model.genre.Genre;
import com.example.cineverse.data.model.genre.GenreResponse;
import com.example.cineverse.data.source.genre.AbstractGenresRemoteDataSource;
import com.example.cineverse.data.source.genre.GenresRemoteResponseCallback;
import com.example.cineverse.exception.UnsupportedViewModelException;
import com.example.cineverse.exception.ViewModelFactoryCreationException;
import com.example.cineverse.repository.genre.GenreRepository;
import com.example.cineverse.utils.mapper.SectionGenreViewModelFactoryMappingManager;
import com.example.cineverse.viewmodel.verified_account.section.home.AbstractContentViewModel;
import com.example.cineverse.viewmodel.verified_account.section.home.content.AbstractSectionContentViewModel;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * The {@link AbstractContentGenreViewModel} class extend {@link AbstractContentViewModel} is an
 * abstract base class for ViewModel classes representing various genre sections in the home screen
 * of the application.
 */
public abstract class AbstractContentGenreViewModel
        extends AbstractContentViewModel
        implements GenresRemoteResponseCallback {

    protected GenreRepository repository;
    private MutableLiveData<List<Genre>> contentLiveData;

    /**
     * Constructs an {@link AbstractContentGenreViewModel} object with the given {@link Application}.
     *
     * @param application The {@link Application} of the calling component.
     */
    public AbstractContentGenreViewModel(@NonNull Application application) {
        super(application);
        repository = new GenreRepository(createRemoteDataSourceInstance(), this);
    }

    public MutableLiveData<List<Genre>> getContentLiveData() {
        if (contentLiveData == null) {
            contentLiveData = new MutableLiveData<>();
        }
        return contentLiveData;
    }

    @Override
    public boolean isContentEmpty() {
        List<Genre> content = getContentLiveData().getValue();
        if (content != null) {
            return content.isEmpty();
        }
        return true;
    }

    @Override
    public void fetch() {
        repository.fetch();
    }

    @Override
    public void onRemoteResponse(GenreResponse response) {
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

    /**
     * Retrieves the ViewModel class for the associated content section.
     *
     * @return The {@link Class} representing the associated content section ViewModel.
     */
    public ViewModelProvider.Factory getSectionContentFactory(int genreId) {
        Class<? extends AbstractSectionContentViewModel> viewModelClass =
                getSectionContentViewModelClass();
        Class<? extends ViewModelProvider.Factory> factoryClass =
                SectionGenreViewModelFactoryMappingManager.getFactoryClass(viewModelClass);
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

    /**
     * Retrieves the class of the associated content section ViewModel.
     *
     * @return The {@link Class} representing the associated content section ViewModel.
     */
    public abstract Class<? extends AbstractSectionContentViewModel> getSectionContentViewModelClass();

    /**
     * Creates an instance of the remote data source for genres.
     *
     * @return An instance of the {@link AbstractGenresRemoteDataSource}.
     */
    protected abstract AbstractGenresRemoteDataSource createRemoteDataSourceInstance();

}
