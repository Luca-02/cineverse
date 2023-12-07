package com.example.cineverse.repository.content.poster.interfaces;

public interface IPosterMovieRepository {
    void fetchNowPlayingMovies(String language, int page);
    void fetchPopularMovies(String language, int page);
    void fetchTopRatedMovies(String language, int page);
    void fetchUpcomingMovies(String language, int page);
}
