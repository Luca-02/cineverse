package com.example.cineverse.data.source.genre;

/**
 * The {@link IGenresRemoteDataSource} interface defines the contract for fetching genres remotely.
 */
public interface IGenresRemoteDataSource {
    /**
     * Fetches genres remotely.
     */
    void fetch();
}