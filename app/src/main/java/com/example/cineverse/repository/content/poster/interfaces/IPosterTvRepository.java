package com.example.cineverse.repository.content.poster.interfaces;

public interface IPosterTvRepository {
    void fetchAiringTodayTv(String language, int page);
    void fetchWeekAiringTv(String language, int page);
    void fetchPopularTv(String language, int page);
    void fetchTopRatedTv(String language, int page);
}
