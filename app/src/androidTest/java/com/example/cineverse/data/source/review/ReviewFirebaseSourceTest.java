package com.example.cineverse.data.source.review;

import static com.example.cineverse.utils.constant.GlobalConstant.RECENT_LIMIT_COUNT;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

import com.example.cineverse.data.model.User;
import com.example.cineverse.data.model.content.section.Movie;
import com.example.cineverse.data.model.review.Review;
import com.example.cineverse.data.model.review.UserReview;

import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class ReviewFirebaseSourceTest {

    private Context context;

    @Before
    public void setup() {
        context = InstrumentationRegistry.getInstrumentation().getTargetContext();
    }

    @Test
    public void getContentReviewOfUserTest() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);
        ReviewFirebaseSource reviewFirebaseSource = new ReviewFirebaseSource(context, new ReviewFirebaseCallback() {
            @Override
            public void onContentReviewOfUser(Review review) {
                assertNotNull(review);
                System.out.println(review);
                latch.countDown();
            }

            @Override
            public void onAddedContentReviewOfUser(boolean added) {
                assertTrue(added);
                latch.countDown();
            }

            @Override
            public void onRemovedContentReviewOfUser(boolean removed) {
                assertTrue(removed);
                latch.countDown();
            }

            @Override
            public void onRecentContentReview(List<UserReview> recentReviews) {
                assertNotNull(recentReviews);
                System.out.println(recentReviews);
                latch.countDown();
            }

            @Override
            public void onContentReviewOfContent(List<UserReview> userReviewList, int pageSize, long lastTimestamp) {
                assertNotNull(userReviewList);
                for (UserReview i : userReviewList) {
                    System.out.println(i.getReview().getTimestamp() + " - " + i.getReview().getReview());
                }
                System.out.println(pageSize);
                System.out.println(lastTimestamp);
                latch.countDown();
            }

            @Override
            public void onNetworkUnavailable() {
                System.out.println("NetworkUnavailable");
                latch.countDown();
            }
        });

        User user = new User("ztat6FiSrNSs3fxfx0GmXRKMBlr2", null, null, null);
        Movie movie = new Movie(238, null, null, null,
                null, null, null);
        reviewFirebaseSource.getContentReviewOfUser(user, movie);
        latch.await(5, TimeUnit.SECONDS);
    }

    @Test
    public void getRecentContentReview() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);
        ReviewFirebaseSource reviewFirebaseSource = new ReviewFirebaseSource(context, new ReviewFirebaseCallback() {
            @Override
            public void onContentReviewOfUser(Review review) {
                assertNotNull(review);
                System.out.println(review);
                latch.countDown();
            }

            @Override
            public void onAddedContentReviewOfUser(boolean added) {
                assertTrue(added);
                latch.countDown();
            }

            @Override
            public void onRemovedContentReviewOfUser(boolean removed) {
                assertTrue(removed);
                latch.countDown();
            }

            @Override
            public void onRecentContentReview(List<UserReview> recentReviews) {
                assertNotNull(recentReviews);
                for (UserReview i : recentReviews) {
                    System.out.println(i.getReview().getTimestamp() + " - " + i.getReview().getReview());
                }
                latch.countDown();
            }

            @Override
            public void onContentReviewOfContent(List<UserReview> userReviewList, int pageSize, long lastTimestamp) {
                assertNotNull(userReviewList);
                for (UserReview i : userReviewList) {
                    System.out.println(i.getReview().getTimestamp() + " - " + i.getReview().getReview());
                }
                System.out.println(pageSize);
                System.out.println(lastTimestamp);
                latch.countDown();
            }

            @Override
            public void onNetworkUnavailable() {
                System.out.println("NetworkUnavailable");
                latch.countDown();
            }
        });

        Movie movie = new Movie(238, null, null, null,
                null, null, null);
        reviewFirebaseSource.getRecentContentReview(movie, RECENT_LIMIT_COUNT);
        latch.await(5, TimeUnit.SECONDS);
    }

    @Test
    public void getContentReviewOfContentTest() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);
        ReviewFirebaseSource reviewFirebaseSource = new ReviewFirebaseSource(context, new ReviewFirebaseCallback() {
            @Override
            public void onContentReviewOfUser(Review review) {
                assertNotNull(review);
                System.out.println(review);
                latch.countDown();
            }

            @Override
            public void onAddedContentReviewOfUser(boolean added) {
                assertTrue(added);
                latch.countDown();
            }

            @Override
            public void onRemovedContentReviewOfUser(boolean removed) {
                assertTrue(removed);
                latch.countDown();
            }

            @Override
            public void onRecentContentReview(List<UserReview> recentReviews) {
                assertNotNull(recentReviews);
                System.out.println(recentReviews);
                latch.countDown();
            }

            @Override
            public void onContentReviewOfContent(List<UserReview> userReviewList, int pageSize, long lastTimestamp) {
                assertNotNull(userReviewList);
                System.out.println("-----");
                for (UserReview i : userReviewList) {
                    System.out.println(i.getReview().getTimestamp() + " - " + i.getUserId());
                }
                System.out.println(pageSize);
                System.out.println(lastTimestamp);
                System.out.println("-----");
                latch.countDown();
            }

            @Override
            public void onNetworkUnavailable() {
                System.out.println("NetworkUnavailable");
                latch.countDown();
            }
        });

        Movie movie = new Movie(238, null, null, null,
                null, null, null);
        reviewFirebaseSource.getContentReviewOfContent(movie, 2, 2);
        latch.await(5, TimeUnit.SECONDS);
    }

}
