package com.example.cineverse.utils.mapper;

import com.example.cineverse.data.model.content.AbstractContent;
import com.example.cineverse.data.source.content.AbstractSectionContentRemoteDataSource;
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

import java.util.HashMap;
import java.util.Map;

/**
 * The {@link SectionTypeMappingManager} class is responsible for mapping
 * section types to their corresponding data source classes.
 */
public class SectionTypeMappingManager {

    /**
     * Enumeration of section types with their associated section names.
     */
    public enum SectionType {
        POPULAR_MOVIE("popular_movie"),
        TOP_RATED_MOVIE("top_rated_movie"),
        NOW_PLAYING_MOVIE("now_playing_movie"),
        UPCOMING_MOVIE("upcoming_movie"),
        TODAY_TRENDING_MOVIE("today_trending_movie"),
        POPULAR_TV("popular_tv"),
        TOP_RATED_TV("top_rated_tv"),
        AIRING_TODAY_TV("airing_today_tv"),
        WEEK_AIRING_TV("week_airing_tv"),
        TODAY_TRENDING_TV("today_trending_tv");

        private final String section;

        SectionType(String sectionName) {
            this.section = sectionName;
        }

        public String getSection() {
            return section;
        }
    }

    /**
     * A map that associates data source classes with their corresponding section types.
     */
    private static final Map<Class<? extends AbstractSectionContentRemoteDataSource
            <? extends AbstractContent>>, SectionType> sectionMap = new HashMap<>();

    // Static initialization block to populate the section map.
    static {
        sectionMap.put(PopularMovieRemoteDataSource.class, SectionType.POPULAR_MOVIE);
        sectionMap.put(TopRatedMovieRemoteDataSource.class, SectionType.TOP_RATED_MOVIE);
        sectionMap.put(NowPlayingMovieRemoteDataSource.class, SectionType.NOW_PLAYING_MOVIE);
        sectionMap.put(UpcomingMovieRemoteDataSource.class, SectionType.UPCOMING_MOVIE);
        sectionMap.put(TodayTrendingMovieRemoteDataSource.class, SectionType.TODAY_TRENDING_MOVIE);
        sectionMap.put(PopularTvRemoteDataSource.class, SectionType.POPULAR_TV);
        sectionMap.put(TopRatedTvRemoteDataSource.class, SectionType.TOP_RATED_TV);
        sectionMap.put(AiringTodayTvRemoteDataSource.class, SectionType.AIRING_TODAY_TV);
        sectionMap.put(WeekAiringTvRemoteDataSource.class, SectionType.WEEK_AIRING_TV);
        sectionMap.put(TodayTrendingTvRemoteDataSource.class, SectionType.TODAY_TRENDING_TV);
    }

    /**
     * Gets the section name associated with the provided data source class.
     *
     * @param contentClass The data source class.
     * @return The section name.
     */
    public static String getSection(
            Class<? extends AbstractSectionContentRemoteDataSource> contentClass) {
        SectionType sectionType = sectionMap.get(contentClass);
        return (sectionType != null) ? sectionType.getSection() : null;
    }

}
