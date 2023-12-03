package com.example.cineverse.utils.constant;

public class Api {

    public static final String TMDB_API_BASE_URL = "https://api.themoviedb.org/3/";
    public static final String TMDB_POSTER_SIZE_URL = "https://image.tmdb.org/t/p/original";

    public static final String LANGUAGE_PARAMETER = "language";
    public static final String PAGE_PARAMETER = "page";

    public static class Movie {
        public static final String POPULAR_MOVIE_ENDPOINT = "movie/popular";
        public static final String TOP_RATED_MOVIE_ENDPOINT = "movie/top_rated";
        public static final String UPCOMING_MOVIE_ENDPOINT = "movie/upcoming";
    }

    public static class Tv {
        public static final String POPULAR_TV_ENDPOINT = "tv/popular";
        public static final String TOP_RATED_TV_ENDPOINT = "tv/top_rated";
        public static final String ON_THE_AIR_TV_ENDPOINT = "tv/on_the_air";
    }

}
