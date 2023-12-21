package com.example.cineverse.viewmodel.verified_account.section.home.genre;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.cineverse.data.model.api.Failure;
import com.example.cineverse.data.model.genre.Genre;
import com.example.cineverse.data.model.genre.GenreResponse;
import com.example.cineverse.data.source.genre.AbstractGenresRemoteDataSource;
import com.example.cineverse.data.source.genre.GenresRemoteResponseCallback;
import com.example.cineverse.repository.genre.GenreRepository;
import com.example.cineverse.viewmodel.verified_account.section.home.AbstractContentViewModel;

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
    public AbstractContentGenreViewModel(@NonNull Application application,
                                         AbstractGenresRemoteDataSource remoteDataSource) {
        super(application);
        repository = new GenreRepository(remoteDataSource, this);
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

}
