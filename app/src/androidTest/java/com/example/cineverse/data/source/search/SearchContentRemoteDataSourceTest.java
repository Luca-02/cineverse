package com.example.cineverse.data.source.search;

import static org.junit.Assert.assertNotNull;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

import com.example.cineverse.data.model.api.Failure;
import com.example.cineverse.data.model.search.SearchContentResponse;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class SearchContentRemoteDataSourceTest {

    private Context context;

    @Before
    public void setup() {
        context = InstrumentationRegistry.getInstrumentation().getTargetContext();
    }

    @Test
    public void searchContentTest() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);

        SearchContentRemoteDataSource remoteDataSource = new SearchContentRemoteDataSource(context, new SearchContentRemoteResponseCallback() {
            @Override
            public void onRemoteResponse(SearchContentResponse response) {
                System.out.println("response");
                assertNotNull(response);
                for (Object content : response.getParsedResults()) {
                    System.out.println(content);
                }
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

        remoteDataSource.fetch("padrino", 1);
        latch.await(5, TimeUnit.SECONDS);
    }

}