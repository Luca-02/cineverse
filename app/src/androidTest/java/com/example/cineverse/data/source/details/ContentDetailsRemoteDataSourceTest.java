package com.example.cineverse.data.source.details;

import static org.junit.Assert.assertNotNull;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

import com.example.cineverse.data.model.api.Failure;
import com.example.cineverse.data.model.details.section.MovieDetails;
import com.example.cineverse.data.model.details.section.TvDetails;
import com.example.cineverse.data.source.details.section.MovieDetailsRemoteDataSource;
import com.example.cineverse.data.source.details.section.TvDetailsRemoteDataSource;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class ContentDetailsRemoteDataSourceTest {

    private Context context;

    @Before
    public void setup() {
        context = InstrumentationRegistry.getInstrumentation().getTargetContext();
    }

    @Test
    public void movieDetailsTest() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);

        MovieDetailsRemoteDataSource remoteDataSource = new MovieDetailsRemoteDataSource(context);
        remoteDataSource.setRemoteResponseCallback(new ContentDetailsRemoteResponseCallback<MovieDetails>() {
            @Override
            public void onRemoteResponse(MovieDetails response) {
                System.out.println("response");
                assertNotNull(response);
                System.out.println(response);
                System.out.println(response.getGenres().size());
                System.out.println(response.getCredits().getCast().size());
                System.out.println(response.getVideos().getResults().size());
                latch.countDown();
            }

            @Override
            public void onFailure(Failure failure) {
                System.out.println("failure");
                assertNotNull(failure);
                System.out.println(failure);
                latch.countDown();
            }
        });

        remoteDataSource.fetchDetails(238);
        latch.await(5, TimeUnit.SECONDS);
    }

    @Test
    public void tvDetailsTest() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);

        TvDetailsRemoteDataSource remoteDataSource = new TvDetailsRemoteDataSource(context);
        remoteDataSource.setRemoteResponseCallback(new ContentDetailsRemoteResponseCallback<TvDetails>() {
            @Override
            public void onRemoteResponse(TvDetails response) {
                System.out.println("response");
                assertNotNull(response);
                System.out.println(response);
                System.out.println(response.getGenres().size());
                System.out.println(response.getCredits().getCast().size());
                System.out.println(response.getSeasons().size());
                System.out.println(response.getVideos().getResults().size());
                latch.countDown();
            }

            @Override
            public void onFailure(Failure failure) {
                System.out.println("failure");
                assertNotNull(failure);
                System.out.println(failure);
                latch.countDown();
            }
        });

        remoteDataSource.fetchDetails(1396);
        latch.await(5, TimeUnit.SECONDS);
    }

}
