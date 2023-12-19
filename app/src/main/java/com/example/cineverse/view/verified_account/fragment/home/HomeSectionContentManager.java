package com.example.cineverse.view.verified_account.fragment.home;

import com.example.cineverse.R;
import com.example.cineverse.data.model.ui.ContentSection;
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

import java.util.ArrayList;
import java.util.List;

public class HomeSectionContentManager {

    /**
     * Retrieves a list of all content sections for the home screen.
     *
     * @return A list of {@link ContentSection} objects representing content sections.
     */
    public static List<ContentSection> getAllContentSection() {
        List<ContentSection> sectionList = new ArrayList<>();
        sectionList.addAll(getMovieContentSection());
        sectionList.addAll(getTvContentSection());
        return sectionList;
    }

    /**
     * Retrieves a list of content sections related to movies.
     *
     * @param withGenreSection Flag indicating whether to include a genre section.
     * @return A list of {@link ContentSection} objects representing movie content sections.
     */
    public static List<ContentSection> getMovieContentSection() {
        List<ContentSection> sectionList = new ArrayList<>();
        sectionList.add(new ContentSection(R.string.top_rated_movie, TopRatedMovieViewModel.class, ContentSection.ViewType.POSTER_TYPE));
        sectionList.add(new ContentSection(R.string.popular_movie, PopularMovieViewModel.class, ContentSection.ViewType.CAROUSEL_TYPE));
        sectionList.add(new ContentSection(R.string.now_playing_movie, NowPlayingMovieViewModel.class, ContentSection.ViewType.POSTER_TYPE));
        sectionList.add(new ContentSection(R.string.upcoming_movie, UpcomingMovieViewModel.class, ContentSection.ViewType.POSTER_TYPE));
        sectionList.add(new ContentSection(R.string.today_trending_movie, TodayTrendingMovieViewModel.class, ContentSection.ViewType.POSTER_TYPE));
        return sectionList;
    }

    /**
     * Retrieves a list of content sections related to TV shows.
     *
     * @param withGenreSection Flag indicating whether to include a genre section.
     * @return A list of {@link ContentSection} objects representing TV content sections.
     */
    public static List<ContentSection> getTvContentSection() {
        List<ContentSection> sectionList = new ArrayList<>();
        sectionList.add(new ContentSection(R.string.top_rated_tv, TopRatedTvViewModel.class, ContentSection.ViewType.POSTER_TYPE));
        sectionList.add(new ContentSection(R.string.popular_tv, PopularTvViewModel.class, ContentSection.ViewType.CAROUSEL_TYPE));
        sectionList.add(new ContentSection(R.string.airing_today_tv, AiringTodayTvViewModel.class, ContentSection.ViewType.POSTER_TYPE));
        sectionList.add(new ContentSection(R.string.week_airing_tv, WeekAiringTvViewModel.class, ContentSection.ViewType.POSTER_TYPE));
        sectionList.add(new ContentSection(R.string.today_trending_tv, TodayTrendingTvViewModel.class, ContentSection.ViewType.POSTER_TYPE));
        return sectionList;
    }

}
