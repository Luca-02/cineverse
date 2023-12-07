package com.example.cineverse.repository.content.poster;

import android.content.Context;

import com.example.cineverse.data.model.content.poster.PosterMovie;
import com.example.cineverse.data.source.content.poster.PosterContentResponseCallback;
import com.example.cineverse.data.source.content.poster.PosterMovieRemoteDataSource;
import com.example.cineverse.repository.content.poster.interfaces.IPosterMovieRepository;

public class PosterMovieRepository
        extends AbstractPosterRepository<PosterMovie>
        implements IPosterMovieRepository {

    public PosterMovieRemoteDataSource remoteDataSource;

    public PosterMovieRepository(Context context,
                                 PosterContentResponseCallback<PosterMovie> callback) {
        super(context, callback);
        remoteDataSource = new PosterMovieRemoteDataSource(bearerAccessTokenAuth, region, callback);
    }

    @Override
    public void fetchNowPlayingMovies(String language, int page) {
        remoteDataSource.fetchNowPlayingMovies(language, page);
    }

    public void fetchPopularMovies(String language, int page) {
        remoteDataSource.fetchPopularMovies(language, page);
    }

    public void fetchTopRatedMovies(String language, int page) {
        remoteDataSource.fetchTopRatedMovies(language, page);
    }

    public void fetchUpcomingMovies(String language, int page) {
        remoteDataSource.fetchUpcomingMovies(language, page);
    }

}
