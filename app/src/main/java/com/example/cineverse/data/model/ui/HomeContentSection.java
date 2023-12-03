package com.example.cineverse.data.model.ui;

import com.example.cineverse.R;
import com.example.cineverse.data.model.content.AbstractPoster;
import com.example.cineverse.viewmodel.logged.verified_account.section.home.AbstractSectionViewModel;
import com.example.cineverse.viewmodel.logged.verified_account.section.home.section.movie.PopularMovieViewModel;
import com.example.cineverse.viewmodel.logged.verified_account.section.home.section.movie.TopRatedMovieViewModel;
import com.example.cineverse.viewmodel.logged.verified_account.section.home.section.movie.UpcomingMovieViewModel;
import com.example.cineverse.viewmodel.logged.verified_account.section.home.section.tv.WeekAiringTvViewModel;
import com.example.cineverse.viewmodel.logged.verified_account.section.home.section.tv.PopularTvViewModel;
import com.example.cineverse.viewmodel.logged.verified_account.section.home.section.tv.TopRatedTvViewModel;

public enum HomeContentSection {

    POPULAR_MOVIE(R.string.popular_movie, PopularMovieViewModel.class),
    TOP_RATED_MOVIE(R.string.top_rated_movie, TopRatedMovieViewModel.class),
    UPCOMING_RATED_MOVIE(R.string.upcoming_movie, UpcomingMovieViewModel.class),
    POPULAR_TV(R.string.popular_tv, PopularTvViewModel.class),
    TOP_RATED_TV(R.string.top_rated_tv, TopRatedTvViewModel.class),
    ON_THE_AIR_TV(R.string.week_airing_tv, WeekAiringTvViewModel.class);

    public final int sectionTitleStringId;
    public final Class<? extends AbstractSectionViewModel<? extends AbstractPoster>> viewModelClass;

    <T extends AbstractPoster> HomeContentSection(int sectionTitleStringId,
                                                  Class<? extends AbstractSectionViewModel<T>> viewModelClass) {
        this.sectionTitleStringId = sectionTitleStringId;
        this.viewModelClass = viewModelClass;
    }

}
