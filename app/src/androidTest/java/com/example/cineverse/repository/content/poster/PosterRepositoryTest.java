package com.example.cineverse.repository.content.poster;

import static org.junit.Assert.assertNotNull;

import android.content.Context;
import android.util.Log;

import androidx.test.platform.app.InstrumentationRegistry;

import com.example.cineverse.R;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class PosterRepositoryTest {

    private static final String TAG = PosterRepositoryTest.class.getSimpleName();

    private Context context;

    @Before
    public void setup() {
        context = InstrumentationRegistry.getInstrumentation().getTargetContext();
    }

    @Test
    public void repositoryFetchTest() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);

        PosterTvRepository repository = new PosterTvRepository(context, response -> {
            assertNotNull(response);
            Log.d(TAG, response.toString());
            latch.countDown();
        });
        repository.fetchPopularTv(context.getString(R.string.language), 1);
        latch.await(5, TimeUnit.SECONDS);
    }

}
