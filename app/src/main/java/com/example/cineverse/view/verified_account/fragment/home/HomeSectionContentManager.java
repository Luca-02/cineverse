package com.example.cineverse.view.verified_account.fragment.home;

import android.app.Application;

import com.example.cineverse.R;
import com.example.cineverse.data.model.ui.ContentSection;
import com.example.cineverse.data.source.content.section.AiringTodayTvRemoteDataSource;
import com.example.cineverse.data.source.content.section.NowPlayingMovieRemoteDataSource;
import com.example.cineverse.data.source.content.section.PopularMovieRemoteDataSource;
import com.example.cineverse.data.source.content.section.PopularTvRemoteDataSource;
import com.example.cineverse.data.source.content.section.TodayTrendingMovieRemoteDataSource;
import com.example.cineverse.data.source.content.section.TodayTrendingTvRemoteDataSource;
import com.example.cineverse.data.source.content.section.TopRatedMovieRemoteDataSource;
import com.example.cineverse.data.source.content.section.TopRatedTvRemoteDataSource;
import com.example.cineverse.data.source.content.section.UpcomingMovieRemoteDataSource;
import com.example.cineverse.data.source.content.section.WeekAiringTvRemoteDataSource;
import com.example.cineverse.viewmodel.verified_account.section.home.content.section.SectionMovieViewModelFactory;
import com.example.cineverse.viewmodel.verified_account.section.home.content.section.SectionTvViewModelFactory;

import java.util.ArrayList;
import java.util.List;

public class HomeSectionContentManager {

    /**
     * Retrieves a list of all content sections for the home screen.
     *
     * @return A list of {@link ContentSection} objects representing content sections.
     */
    public static List<ContentSection> getAllContentSection(Application application) {
        List<ContentSection> sectionList = new ArrayList<>();
        sectionList.addAll(getMovieContentSection(application));
        sectionList.addAll(getTvContentSection(application));
        return sectionList;
    }

    /**
     * Retrieves a list of content sections related to movies.
     *
     * @return A list of {@link ContentSection} objects representing movie content sections.
     */
    public static List<ContentSection> getMovieContentSection(Application application) {
        List<ContentSection> sectionList = new ArrayList<>();
        sectionList.add(new ContentSection(
                R.string.top_rated_movie,
                new SectionMovieViewModelFactory(application,
                        new TopRatedMovieRemoteDataSource(application.getApplicationContext())),
                ContentSection.ViewType.POSTER_TYPE));
        sectionList.add(new ContentSection(
                R.string.popular_movie,
                new SectionMovieViewModelFactory(application,
                        new PopularMovieRemoteDataSource(application.getApplicationContext())),
                ContentSection.ViewType.CAROUSEL_TYPE));
        sectionList.add(new ContentSection(
                R.string.now_playing_movie,
                new SectionMovieViewModelFactory(application,
                        new NowPlayingMovieRemoteDataSource(application.getApplicationContext())),
                ContentSection.ViewType.POSTER_TYPE));
        sectionList.add(new ContentSection(
                R.string.upcoming_movie,
                new SectionMovieViewModelFactory(application,
                        new UpcomingMovieRemoteDataSource(application.getApplicationContext())),
                ContentSection.ViewType.POSTER_TYPE));
        sectionList.add(new ContentSection(
                R.string.today_trending_movie,
                new SectionMovieViewModelFactory(application,
                        new TodayTrendingMovieRemoteDataSource(application.getApplicationContext())),
                ContentSection.ViewType.POSTER_TYPE));
        return sectionList;
    }

    /**
     * Retrieves a list of content sections related to TV shows.
     *
     * @return A list of {@link ContentSection} objects representing TV content sections.
     */
    public static List<ContentSection> getTvContentSection(Application application) {
        List<ContentSection> sectionList = new ArrayList<>();
        sectionList.add(new ContentSection(
                R.string.top_rated_tv,
                new SectionTvViewModelFactory(application,
                        new TopRatedTvRemoteDataSource(application.getApplicationContext())),
                ContentSection.ViewType.POSTER_TYPE));
        sectionList.add(new ContentSection(
                R.string.popular_tv,
                new SectionTvViewModelFactory(application,
                        new PopularTvRemoteDataSource(application.getApplicationContext())),
                ContentSection.ViewType.CAROUSEL_TYPE));
        sectionList.add(new ContentSection(
                R.string.airing_today_tv,
                new SectionTvViewModelFactory(application,
                        new AiringTodayTvRemoteDataSource(application.getApplicationContext())),
                ContentSection.ViewType.POSTER_TYPE));
        sectionList.add(new ContentSection(
                R.string.week_airing_tv,
                new SectionTvViewModelFactory(application,
                        new WeekAiringTvRemoteDataSource(application.getApplicationContext())),
                ContentSection.ViewType.POSTER_TYPE));
        sectionList.add(new ContentSection(
                R.string.today_trending_tv,
                new SectionTvViewModelFactory(application,
                        new TodayTrendingTvRemoteDataSource(application.getApplicationContext())),
                ContentSection.ViewType.POSTER_TYPE));
        return sectionList;
    }

}
