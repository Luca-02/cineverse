package com.example.cineverse.data.source.content.remote;

import static junit.framework.TestCase.assertEquals;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

import com.example.cineverse.data.source.content.section.UpcomingMovieRemoteDataSource;

import org.junit.Before;
import org.junit.Test;

public class AbstractSectionContentRemoteDataSourceTest {

    private Context context;

    @Before
    public void setup() {
        context = InstrumentationRegistry.getInstrumentation().getTargetContext();
    }

    @Test
    public void getSection() {
        UpcomingMovieRemoteDataSource upcomingMovieRemoteDataSource = new UpcomingMovieRemoteDataSource(context);
        assertEquals(upcomingMovieRemoteDataSource.getSection(), "upcoming_movie");
    }

}