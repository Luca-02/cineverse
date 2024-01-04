package com.example.cineverse.utils.constant;

public class Api {

    public static final String TMDB_API_BASE_URL = "https://api.themoviedb.org/3/";
    public static final String TMDB_IMAGE_ORIGINAL_SIZE_URL = "https://image.tmdb.org/t/p/original";

    // Error
    public static final int UNEXPECTED_ERROR_CODE = -1;
    public static final String UNEXPECTED_ERROR_MESSAGE = "Unexpected error.";

    // General
    public static final String DISCOVER = "discover";
    public static final String TRENDING = "trending";
    public static final String GENRE = "genre";
    public static final String APPEND_TO_RESPONSE_PARAMETER = "append_to_response";
    public static final String APPEND_TO_RESPONSE_DEFAULT_PARAMETER = "credits,videos,similar";

    public static final String RESPONSE_DATE_FORMAT = "yyyy-mm-dd";
    public static final int STARTING_PAGE = 1;

    // Auth
    public static final String BEARER_AUTHORIZATION_TAG = "Bearer";
    public static final String AUTHORIZATION_HEADER = "Authorization";

    // Query params
    public static final String LANGUAGE_PARAMETER = "language";
    public static final String PAGE_PARAMETER = "page";
    public static final String REGION_PARAMETER = "region";
    public static final String WITH_GENRES_PARAMETER = "with_genres";
    public static final String QUERY_PARAMETER = "query";

    public static class Movie {
        public static final String MOVIE = "movie";
        public static final String MOVIE_ID = "movie_id";
        public static final String NOW_PLAYING_MOVIE_ENDPOINT = MOVIE + "/now_playing";
        public static final String POPULAR_MOVIE_ENDPOINT = MOVIE + "/popular";
        public static final String TOP_RATED_MOVIE_ENDPOINT = MOVIE + "/top_rated";
        public static final String UPCOMING_MOVIE_ENDPOINT = MOVIE + "/upcoming";
        public static final String TODAY_TRENDING_MOVIE_ENDPOINT = TRENDING + "/" + MOVIE + "/day";
        public static final String GENRE_MOVIE_ENDPOINT = GENRE + "/" + MOVIE + "/list";
        public static final String DISCOVER_MOVIE_ENDPOINT = DISCOVER + "/" + MOVIE;
        public static final String MOVIE_DETAILS_ENDPOINT = MOVIE + "/{" + MOVIE_ID + "}";
    }

    public static class Tv {
        public static final String TV = "tv";
        public static final String TV_ID = "series_id";
        public static final String AIRING_TODAY_TV_ENDPOINT = TV + "/airing_today";
        public static final String ON_THE_AIR_TV_ENDPOINT = TV + "/on_the_air";
        public static final String POPULAR_TV_ENDPOINT = TV + "/popular";
        public static final String TOP_RATED_TV_ENDPOINT = TV + "/top_rated";
        public static final String TODAY_TRENDING_TV_ENDPOINT = TRENDING + "/" + TV + "/day";
        public static final String GENRE_TV_ENDPOINT = GENRE + "/" + TV + "/list";
        public static final String DISCOVER_TV_ENDPOINT = DISCOVER + "/" + TV;
        public static final String TV_DETAILS_ENDPOINT = TV + "/{" + TV_ID + "}";
    }

    public static class Search {
        public static final String SEARCH = "search";
        public static final String SEARCH_KEYWORD_ENDPOINT = SEARCH + "/keyword";
        public static final String SEARCH_MULTI_ENDPOINT = SEARCH + "/multi";
    }

}
