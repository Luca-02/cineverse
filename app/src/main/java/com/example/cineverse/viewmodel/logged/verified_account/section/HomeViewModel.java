package com.example.cineverse.viewmodel.logged.verified_account.section;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.cineverse.data.model.content.PosterMovie;
import com.example.cineverse.data.model.content.PosterMovieApiResponse;
import com.example.cineverse.repository.classes.content.PosterMovieRepository;
import com.example.cineverse.viewmodel.logged.verified_account.AbstractVerifiedAccountViewModel;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel
        extends AbstractVerifiedAccountViewModel {

    private final PosterMovieRepository posterMovieRepository;
    private MutableLiveData<List<PosterMovie>> popularMovieLiveData;

    /**
     * Constructs an {@link HomeViewModel} object with the given {@link Application}.
     *
     * @param application The {@link Application} of the calling component.
     */
    public HomeViewModel(@NonNull Application application) {
        super(application);
        posterMovieRepository = new PosterMovieRepository(application.getBaseContext());
        PosterMovie posterMovie = new PosterMovie(1, "/3bhkrj58Vtu7enYsRolD1fZdja1.jpg");
        List<PosterMovie> list = new ArrayList<>();
        list.add(posterMovie);
        getPopularMovieLiveData().postValue(list);
    }

    public MutableLiveData<List<PosterMovie>> getPopularMovieLiveData() {
        if (popularMovieLiveData == null) {
            popularMovieLiveData = new MutableLiveData<>();
        }
        return popularMovieLiveData;
    }

    public void fetchPopularMovies(int page) {
        posterMovieRepository.fetchPopularMovies(page, response -> {
            if (response != null) {
                List<PosterMovie> currentData = popularMovieLiveData.getValue();
                List<PosterMovie> resultData = response.getResults().subList(0, 1);
                if (currentData == null) {
                    currentData = resultData;
                } else {
                    currentData.addAll(resultData);
                }
                popularMovieLiveData.postValue(currentData);
            }
        });
    }

}
