package com.example.cineverse.viewmodel.logged.verified_account.section.home;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.cineverse.R;
import com.example.cineverse.data.model.ui.ContentSection;
import com.example.cineverse.viewmodel.logged.verified_account.VerifiedAccountViewModel;
import com.example.cineverse.viewmodel.logged.verified_account.section.home.section.poster.AiringTodayTvViewModel;
import com.example.cineverse.viewmodel.logged.verified_account.section.home.section.poster.NowPlayingMoviesViewModel;
import com.example.cineverse.viewmodel.logged.verified_account.section.home.section.poster.PopularMovieViewModel;
import com.example.cineverse.viewmodel.logged.verified_account.section.home.section.poster.TopRatedMovieViewModel;
import com.example.cineverse.viewmodel.logged.verified_account.section.home.section.poster.UpcomingMovieViewModel;
import com.example.cineverse.viewmodel.logged.verified_account.section.home.section.poster.PopularTvViewModel;
import com.example.cineverse.viewmodel.logged.verified_account.section.home.section.poster.TopRatedTvViewModel;
import com.example.cineverse.viewmodel.logged.verified_account.section.home.section.poster.WeekAiringTvViewModel;

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

    public List<ContentSection> getHomeContentSection() {
        List<ContentSection> sectionList = new ArrayList<>();
        sectionList.add(new ContentSection(R.string.top_rated_movie, TopRatedMovieViewModel.class, ContentSection.CAROUSEL_TYPE));
        sectionList.add(new ContentSection(R.string.popular_movie, PopularMovieViewModel.class, ContentSection.POSTER_TYPE));
        sectionList.add(new ContentSection(R.string.upcoming_movie, UpcomingMovieViewModel.class, ContentSection.POSTER_TYPE));
        sectionList.add(new ContentSection(R.string.now_playing_movie, NowPlayingMoviesViewModel.class, ContentSection.POSTER_TYPE));
        sectionList.add(new ContentSection(R.string.top_rated_tv, TopRatedTvViewModel.class, ContentSection.CAROUSEL_TYPE));
        sectionList.add(new ContentSection(R.string.popular_tv, PopularTvViewModel.class, ContentSection.POSTER_TYPE));
        sectionList.add(new ContentSection(R.string.week_airing_tv, WeekAiringTvViewModel.class, ContentSection.POSTER_TYPE));
        sectionList.add(new ContentSection(R.string.airing_today_tv, AiringTodayTvViewModel.class, ContentSection.POSTER_TYPE));
        return sectionList;
    }

}
