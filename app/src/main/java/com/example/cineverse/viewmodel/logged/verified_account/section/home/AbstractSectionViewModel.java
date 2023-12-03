package com.example.cineverse.viewmodel.logged.verified_account.section.home;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.cineverse.data.model.content.AbstractPoster;
import com.example.cineverse.data.model.content.AbstractPosterApiResponse;
import com.example.cineverse.data.model.content.movie.PosterMovie;
import com.example.cineverse.data.model.content.tv.PosterTv;
import com.example.cineverse.repository.content.movie.PosterMovieRepository;
import com.example.cineverse.repository.content.tv.PosterTvRepository;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractSectionViewModel<T extends AbstractPoster>
        extends AndroidViewModel {

    private MutableLiveData<List<T>> contentLiveData;

    /**
     * Constructs an {@link AbstractSectionViewModel} object with the given {@link Application}.
     *
     * @param application The {@link Application} of the calling component.
     */
    public AbstractSectionViewModel(Application application) {
        super(application);
    }

    public MutableLiveData<List<T>> getContentLiveData() {
        if (contentLiveData == null) {
            contentLiveData = new MutableLiveData<>();
        }
        return contentLiveData;
    }

    public boolean isContentEmpty() {
        List<T> content = getContentLiveData().getValue();
        if (content != null) {
            return content.isEmpty();
        }
        return true;
    }

    protected void updateMovieLiveData(AbstractPosterApiResponse<T> response) {
        if (response != null) {
            List<T> currentData = getContentLiveData().getValue();
            List<T> resultData = response.getResults();
            if (currentData == null) {
                currentData = new ArrayList<>();
            } else {
                currentData.clear();
            }
            currentData.addAll(resultData);
            getContentLiveData().postValue(currentData);
        }
    }

    public abstract void fetch();

}
