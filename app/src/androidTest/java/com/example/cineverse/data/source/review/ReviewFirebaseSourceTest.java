package com.example.cineverse.data.source.review;

import static com.example.cineverse.utils.constant.GlobalConstant.START_TIMESTAMP_VALUE;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

import com.example.cineverse.data.model.User;
import com.example.cineverse.data.model.content.section.Movie;
import com.example.cineverse.data.model.review.UserReview;
import com.example.cineverse.service.firebase.FirebaseCallback;

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
            public void onContentReviewOfUser(UserReview userReview) {
                assertNotNull(userReview);
                System.out.println(userReview);
                latch.countDown();
            }

            @Override
            public void onAddedContentReviewOfUser(UserReview userReview) {
                assertNotNull(userReview);
                System.out.println(userReview);
                latch.countDown();
            }

            @Override
            public void onRemovedContentReviewOfUser(boolean removed) {
                assertTrue(removed);
                latch.countDown();
            }

            @Override
            public void onPagedContentReviewOfContent(List<UserReview> userReviewList, long lastTimestamp) {
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
        reviewFirebaseSource.getContentReviewOfUser(user, movie);
        latch.await(5, TimeUnit.SECONDS);
    }

    @Test
    public void getContentReviewOfContentTest() throws InterruptedException {
        Movie movie = new Movie(238, null, null, null,
                null, null, null);
        reviewFirebaseSource.getPagedContentReviewOfContent(movie, 5, START_TIMESTAMP_VALUE);
        latch.await(5, TimeUnit.SECONDS);
    }

    @Test
    public void getContentRatingTest() throws InterruptedException {
        Movie movie = new Movie(238, null, null, null,
                null, null, null);
        reviewFirebaseSource.getContentRating(movie);
        latch.await(5, TimeUnit.SECONDS);
    }

    @Test
    public void addLikeOfUserToContentReviewTest() throws InterruptedException {
        User user1 = new User("test", null, null, null);
        User user2 = new User("ztat6FiSrNSs3fxfx0GmXRKMBlr2", null, null, null);
        Movie movie = new Movie(238, null, null, null,
                null, null, null);
        UserReview userReview = new UserReview(user2, null);
        reviewFirebaseSource.addLikeOfUserToContentReview(user1, movie, userReview, new FirebaseCallback<Boolean>() {
            @Override
            public void onCallback(Boolean data) {
                assertNotNull(data);
                System.out.println(data);
                latch.countDown();
            }

            @Override
            public void onNetworkUnavailable() {
                System.out.println("NetworkUnavailable");
                latch.countDown();
            }
        });
        latch.await(5, TimeUnit.SECONDS);
    }

    @Test
    public void removeLikeOfUserToContentReviewTest() throws InterruptedException {
        User user1 = new User("test2", null, null, null);
        User user2 = new User("ztat6FiSrNSs3fxfx0GmXRKMBlr2", null, null, null);
        Movie movie = new Movie(238, null, null, null,
                null, null, null);
        UserReview userReview = new UserReview(user2, null);
        reviewFirebaseSource.removeLikeOfUserToContentReview(user1, movie, userReview, new FirebaseCallback<Boolean>() {
            @Override
            public void onCallback(Boolean data) {
                assertNotNull(data);
                System.out.println(data);
                latch.countDown();
            }

            @Override
            public void onNetworkUnavailable() {
                System.out.println("NetworkUnavailable");
                latch.countDown();
            }
        });
        latch.await(5, TimeUnit.SECONDS);
    }

}
