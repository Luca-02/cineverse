package com.example.cineverse.data.source.content.remote;

import com.example.cineverse.data.model.content.AbstractContent;
import com.example.cineverse.data.source.content.remote.section.AiringTodayTvRemoteDataSource;
import com.example.cineverse.data.source.content.remote.section.NowPlayingMovieRemoteDataSource;
import com.example.cineverse.data.source.content.remote.section.PopularMovieRemoteDataSource;
import com.example.cineverse.data.source.content.remote.section.PopularTvRemoteDataSource;
import com.example.cineverse.data.source.content.remote.section.TodayTrendingMovieRemoteDataSource;
import com.example.cineverse.data.source.content.remote.section.TodayTrendingTvRemoteDataSource;
import com.example.cineverse.data.source.content.remote.section.TopRatedMovieRemoteDataSource;
import com.example.cineverse.data.source.content.remote.section.TopRatedTvRemoteDataSource;
import com.example.cineverse.data.source.content.remote.section.UpcomingMovieRemoteDataSource;
import com.example.cineverse.data.source.content.remote.section.WeekAiringTvRemoteDataSource;

import java.util.HashMap;
import java.util.Map;

public class SectionContentMapper {

    private static final Map<Class<? extends AbstractSectionContentRemoteDataSource
            <? extends AbstractContent>>, String> sectionMap = new HashMap<>();

    static {
        sectionMap.put(PopularMovieRemoteDataSource.class, "popular_movie");
        sectionMap.put(TopRatedMovieRemoteDataSource.class, "top_rated_movie");
        sectionMap.put(NowPlayingMovieRemoteDataSource.class, "now_playing_movie");
        sectionMap.put(UpcomingMovieRemoteDataSource.class, "upcoming_movie");
        sectionMap.put(TodayTrendingMovieRemoteDataSource.class, "today_trending_movie");
        sectionMap.put(PopularTvRemoteDataSource.class, "popular_tv");
        sectionMap.put(TopRatedTvRemoteDataSource.class, "top_rated_tv");
        sectionMap.put(AiringTodayTvRemoteDataSource.class, "airing_today_tv");
        sectionMap.put(WeekAiringTvRemoteDataSource.class, "week_airing_tv");
        sectionMap.put(TodayTrendingTvRemoteDataSource.class, "today_trending_tv");
    }

    public static String getSection(
            Class<? extends AbstractSectionContentRemoteDataSource<? extends AbstractContent>> contentClass) {
        return sectionMap.get(contentClass);
    }

}
