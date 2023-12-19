package com.example.cineverse.utils;

import static com.example.cineverse.utils.constant.Api.TMDB_API_BASE_URL;

import android.content.Context;

import com.example.cineverse.data.database.ContentSectionDatabase;
import com.example.cineverse.data.database.dao.ContentDao;
import com.example.cineverse.data.database.dao.SectionContentCrossRefDao;
import com.example.cineverse.data.database.dao.SectionDao;
import com.example.cineverse.service.api.GenreApiService;
import com.example.cineverse.service.api.MovieApiService;
import com.example.cineverse.service.api.SearchApiServices;
import com.example.cineverse.service.api.TvApiService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceLocator {

    private static volatile ServiceLocator instance = null;

    private ServiceLocator() {}

    public static synchronized ServiceLocator getInstance() {
        if (instance == null) {
            instance = new ServiceLocator();
        }
        return instance;
    }

    private Retrofit getRetrofitService() {
        return new Retrofit.Builder()
                .baseUrl(TMDB_API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public MovieApiService getMovieApiService() {
        return getRetrofitService().create(MovieApiService.class);
    }

    public TvApiService getTvApiService() {
        return getRetrofitService().create(TvApiService.class);
    }

    public GenreApiService getGenreApiService() {
        return getRetrofitService().create(GenreApiService.class);
    }

    public SearchApiServices getSearchApiServices() {
        return getRetrofitService().create(SearchApiServices.class);
    }

    public SectionDao getSectionDao(Context context) {
        return ContentSectionDatabase.getInstance(context).sectionDao();
    }

    public SectionContentCrossRefDao getSectionContentCrossRefDao(Context context) {
        return ContentSectionDatabase.getInstance(context).sectionContentCrossRefDao();
    }

    public ContentDao getContentDao(Context context) {
        return ContentSectionDatabase.getInstance(context).contentDao();
    }

}