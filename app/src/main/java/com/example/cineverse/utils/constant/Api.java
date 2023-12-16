package com.example.cineverse.utils.constant;

public class Api {

    public static final String TMDB_API_BASE_URL = "https://api.themoviedb.org/3/";
    public static final String TMDB_IMAGE_ORIGINAL_SIZE_URL = "https://image.tmdb.org/t/p/original";

    // Error
    public static final int UNEXPECTED_ERROR_CODE = -1;
    public static final String UNEXPECTED_ERROR_MESSAGE = "Unexpected error.";

    // General
    private static final String DISCOVER = "discover";
    private static final String TRENDING = "trending";
    private static final String GENRE = "genre";

    public static final String RESPONSE_DATE_FORMAT = "yyyy-MM-dd";
    public static final int STARTING_PAGE = 1;

    // Auth
    public static final String AUTHORIZATION_HEADER = "Authorization";

    // Query params
    public static final String LANGUAGE_PARAMETER = "language";
    public static final String PAGE_PARAMETER = "page";
    public static final String REGION_PARAMETER = "region";
    public static final String WITH_GENRES_PARAMETER = "with_genres";

    public static class Movie {
        private static final String MOVIE = "movie";
        public static final String NOW_PLAYING_MOVIE_ENDPOINT = MOVIE + "/now_playing";
        public static final String POPULAR_MOVIE_ENDPOINT = MOVIE + "/popular";
        public static final String TOP_RATED_MOVIE_ENDPOINT = MOVIE + "/top_rated";
        public static final String UPCOMING_MOVIE_ENDPOINT = MOVIE + "/upcoming";
        public static final String TODAY_TRENDING_MOVIE_ENDPOINT = TRENDING + "/" + MOVIE + "/day";
        public static final String GENRE_MOVIE_ENDPOINT = GENRE + "/" + MOVIE + "/list";
        public static final String DISCOVER_MOVIE_ENDPOINT = DISCOVER + "/" + MOVIE;
    }

    public static class Tv {
        private static final String TV = "tv";
        public static final String AIRING_TODAY_TV_ENDPOINT = TV + "/airing_today";
        public static final String ON_THE_AIR_TV_ENDPOINT = TV + "/on_the_air";
        public static final String POPULAR_TV_ENDPOINT = TV + "/popular";
        public static final String TOP_RATED_TV_ENDPOINT = TV + "/top_rated";
        public static final String TODAY_TRENDING_TV_ENDPOINT = TRENDING + "/" + TV + "/day";
        public static final String GENRE_TV_ENDPOINT = GENRE + "/" + TV + "/list";
        public static final String DISCOVER_TV_ENDPOINT = DISCOVER + "/" + TV;
    }

}
