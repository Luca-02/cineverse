package com.example.cineverse.viewmodel.verified_account.section.home;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.cineverse.R;
import com.example.cineverse.data.model.ui.ContentSection;
import com.example.cineverse.viewmodel.verified_account.VerifiedAccountViewModel;
import com.example.cineverse.viewmodel.verified_account.section.home.content.section.AiringTodayTvViewModel;
import com.example.cineverse.viewmodel.verified_account.section.home.content.section.NowPlayingMovieViewModel;
import com.example.cineverse.viewmodel.verified_account.section.home.content.section.PopularMovieViewModel;
import com.example.cineverse.viewmodel.verified_account.section.home.content.section.PopularTvViewModel;
import com.example.cineverse.viewmodel.verified_account.section.home.content.section.TodayTrendingMovieViewModel;
import com.example.cineverse.viewmodel.verified_account.section.home.content.section.TodayTrendingTvViewModel;
import com.example.cineverse.viewmodel.verified_account.section.home.content.section.TopRatedMovieViewModel;
import com.example.cineverse.viewmodel.verified_account.section.home.content.section.TopRatedTvViewModel;
import com.example.cineverse.viewmodel.verified_account.section.home.content.section.UpcomingMovieViewModel;
import com.example.cineverse.viewmodel.verified_account.section.home.content.section.WeekAiringTvViewModel;
import com.example.cineverse.viewmodel.verified_account.section.home.genre.section.MovieGenreViewModel;
import com.example.cineverse.viewmodel.verified_account.section.home.genre.section.TvGenreViewModel;

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

    public List<ContentSection> getAllContentSection() {
        List<ContentSection> sectionList = new ArrayList<>();
        sectionList.addAll(getMovieContentSection(false));
        sectionList.addAll(getTvContentSection(false));
        return sectionList;
    }

    public List<ContentSection> getMovieContentSection(boolean withGenreSection) {
        List<ContentSection> sectionList = new ArrayList<>();
        if (withGenreSection) {
            sectionList.add(new ContentSection(null, MovieGenreViewModel.class, ContentSection.GENRE_TYPE));
        }
        sectionList.add(new ContentSection(R.string.popular_movie, PopularMovieViewModel.class, ContentSection.POSTER_TYPE));
        sectionList.add(new ContentSection(R.string.top_rated_movie, TopRatedMovieViewModel.class, ContentSection.CAROUSEL_TYPE));
        sectionList.add(new ContentSection(R.string.now_playing_movie, NowPlayingMovieViewModel.class, ContentSection.POSTER_TYPE));
        sectionList.add(new ContentSection(R.string.upcoming_movie, UpcomingMovieViewModel.class, ContentSection.POSTER_TYPE));
        sectionList.add(new ContentSection(R.string.today_trending_movie, TodayTrendingMovieViewModel.class, ContentSection.POSTER_TYPE));
        return sectionList;
    }

    public List<ContentSection> getTvContentSection(boolean withGenreSection) {
        List<ContentSection> sectionList = new ArrayList<>();
        if (withGenreSection) {
            sectionList.add(new ContentSection(null, TvGenreViewModel.class, ContentSection.GENRE_TYPE));
        }
        sectionList.add(new ContentSection(R.string.popular_tv, PopularTvViewModel.class, ContentSection.POSTER_TYPE));
        sectionList.add(new ContentSection(R.string.top_rated_tv, TopRatedTvViewModel.class, ContentSection.CAROUSEL_TYPE));
        sectionList.add(new ContentSection(R.string.airing_today_tv, AiringTodayTvViewModel.class, ContentSection.POSTER_TYPE));
        sectionList.add(new ContentSection(R.string.week_airing_tv, WeekAiringTvViewModel.class, ContentSection.POSTER_TYPE));
        sectionList.add(new ContentSection(R.string.today_trending_tv, TodayTrendingTvViewModel.class, ContentSection.POSTER_TYPE));
        return sectionList;
    }

}
