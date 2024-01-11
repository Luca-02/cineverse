package com.example.cineverse.repository.watchlist;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

import com.example.cineverse.data.model.User;
import com.example.cineverse.data.model.content.AbstractContent;
import com.example.cineverse.data.model.details.section.IContentDetails;
import com.example.cineverse.data.source.watchlist.WatchlistFirebaseCallback;
import com.example.cineverse.utils.mapper.ContentTypeMappingManager;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class WatchlistRepositoryTest {

    private Context context;
    private WatchlistRepository watchlistRepository;
    private final CountDownLatch latch = new CountDownLatch(1);

    @Before
    public void setup() {
        context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        User user = new User("ztat6FiSrNSs3fxfx0GmXRKMBlr2", null, null, null);
        watchlistRepository = new WatchlistRepository(context, user, new WatchlistFirebaseCallback() {
            @Override
            public void onTimestampForContentInWatchlist(Long timestamp) {
                assertNotNull(timestamp);
                System.out.println(timestamp);
                latch.countDown();
            }

            @Override
            public void onUserContentWatchlist(List<AbstractContent> watchlistList, String contentType) {
                assertNotNull(watchlistList);
                System.out.println("-----");
                System.out.println(watchlistList.size());
                for (AbstractContent i : watchlistList) {
                    System.out.println(i.getId() + " " + i.getName() + " " + i.getWatchlistTimestamp());
                }
                System.out.println("-----");
                latch.countDown();
            }

            @Override
            public void onAddedContentToWatchlist(Long newTimestamp) {
                assertNotNull(newTimestamp);
                System.out.println(newTimestamp);
                latch.countDown();
            }

            @Override
            public void onRemovedContentToWatchlist(boolean removed) {
                assertTrue(removed);
                latch.countDown();
            }

            @Override
            public void onNetworkUnavailable() {
                System.out.println("NetworkUnavailable");
                latch.countDown();
            }
        });
    }

    @Test
    public void getUserContentWatchlistTest() throws InterruptedException {
        watchlistRepository.getUserContentWatchlist(ContentTypeMappingManager.ContentType.MOVIE.getType());
        latch.await(5, TimeUnit.SECONDS);
    }

}
