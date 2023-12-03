package com.example.cineverse.viewmodel.logged.verified_account.section.home;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.cineverse.R;
import com.example.cineverse.data.model.ui.HomeContentSection;
import com.example.cineverse.viewmodel.logged.verified_account.VerifiedAccountViewModel;
import com.example.cineverse.viewmodel.logged.verified_account.section.home.section.movie.PopularMovieViewModel;
import com.example.cineverse.viewmodel.logged.verified_account.section.home.section.movie.TopRatedMovieViewModel;
import com.example.cineverse.viewmodel.logged.verified_account.section.home.section.movie.UpcomingMovieViewModel;
import com.example.cineverse.viewmodel.logged.verified_account.section.home.section.tv.PopularTvViewModel;
import com.example.cineverse.viewmodel.logged.verified_account.section.home.section.tv.TopRatedTvViewModel;
import com.example.cineverse.viewmodel.logged.verified_account.section.home.section.tv.WeekAiringTvViewModel;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel
        extends VerifiedAccountViewModel {

    /**
     * Constructs an {@link HomeViewModel} object with the given {@link Application}.
     *
     * @param application The {@link Application} of the calling component.
     */
    public HomeViewModel(@NonNull Application application) {
        super(application);
    }

    public List<HomeContentSection> getHomeContentSection() {
        List<HomeContentSection> sectionList = new ArrayList<>();
        sectionList.add(new HomeContentSection(R.string.popular_movie, PopularMovieViewModel.class));
        sectionList.add(new HomeContentSection(R.string.top_rated_movie, TopRatedMovieViewModel.class));
        sectionList.add(new HomeContentSection(R.string.upcoming_movie, UpcomingMovieViewModel.class));
        sectionList.add(new HomeContentSection(R.string.popular_tv, PopularTvViewModel.class));
        sectionList.add(new HomeContentSection(R.string.top_rated_tv, TopRatedTvViewModel.class));
        sectionList.add(new HomeContentSection(R.string.week_airing_tv, WeekAiringTvViewModel.class));
        return sectionList;
    }

}
