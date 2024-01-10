package com.example.cineverse.data.source.review;

import static com.example.cineverse.utils.constant.GlobalConstant.START_TIMESTAMP_VALUE;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

import com.example.cineverse.data.model.User;
import com.example.cineverse.data.model.content.section.Movie;
import com.example.cineverse.data.model.review.UserReview;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class ReviewFirebaseSourceTest {

    private Context context;
    private ReviewFirebaseSource reviewFirebaseSource;
    private final CountDownLatch latch = new CountDownLatch(1);

    @Before
    public void setup() {
        context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        reviewFirebaseSource = new ReviewFirebaseSource(context, new ReviewFirebaseCallback() {
            @Override
            public void onContentRating(Double rating) {
                assertNotNull(rating);
                System.out.println(rating);
                latch.countDown();
            }

            @Override
            public void onUserReviewOfContent(UserReview userReview) {
                assertNotNull(userReview);
                System.out.println(userReview);
                latch.countDown();
            }

            @Override
            public void onAddedUserReviewOfContent(UserReview userReview) {
                assertNotNull(userReview);
                System.out.println(userReview);
                latch.countDown();
            }

            @Override
            public void onRemovedUserReviewOfContent(boolean removed) {
                assertTrue(removed);
                latch.countDown();
            }

            @Override
            public void onPagedUserReviewOfContent(List<UserReview> userReviewList, long lastTimestamp) {
                assertNotNull(userReviewList);
                System.out.println("-----");
                System.out.println(userReviewList.size());
                for (UserReview i : userReviewList) {
                    System.out.println(i.getUser() + " - " + i.getReview());
                }
                System.out.println(lastTimestamp);
                System.out.println("-----");
                latch.countDown();
            }

            @Override
            public void onAddedLikeOfUserToUserReviewOfContent(UserReview userReview, boolean added) {

            }

            @Override
            public void onRemovedLikeOfUserToUserReviewOfContent(UserReview userReview, boolean removed) {

            }

            @Override
            public void onNetworkUnavailable() {
                System.out.println("NetworkUnavailable");
                latch.countDown();
            }
        });
    }

    @Test
    public void getContentReviewOfUserTest() throws InterruptedException {
        User user = new User("ztat6FiSrNSs3fxfx0GmXRKMBlr2", null, null, null);
        Movie movie = new Movie(238, null, null, null,
                null, null, null);
        reviewFirebaseSource.getUserReviewOfContent(user, movie);
        latch.await(5, TimeUnit.SECONDS);
    }

    @Test
    public void getContentReviewOfContentTest() throws InterruptedException {
        User user = new User("ztat6FiSrNSs3fxfx0GmXRKMBlr2", null, null, null);
        Movie movie = new Movie(238, null, null, null,
                null, null, null);
        reviewFirebaseSource.getPagedUserReviewOfContent(user, movie, 5, START_TIMESTAMP_VALUE);
        latch.await(5, TimeUnit.SECONDS);
    }

    @Test
    public void getContentRatingTest() throws InterruptedException {
        Movie movie = new Movie(238, null, null, null,
                null, null, null);
        reviewFirebaseSource.getContentRating(movie);
        latch.await(5, TimeUnit.SECONDS);
    }

}
