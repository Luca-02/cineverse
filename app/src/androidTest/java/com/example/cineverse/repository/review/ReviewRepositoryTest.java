package com.example.cineverse.repository.review;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

import com.example.cineverse.data.model.User;
import com.example.cineverse.data.model.review.ContentUserReview;
import com.example.cineverse.data.model.review.UserReview;
import com.example.cineverse.data.source.review.ReviewFirebaseCallback;
import com.example.cineverse.utils.mapper.ContentTypeMappingManager;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class ReviewRepositoryTest {

    private Context context;
    private ReviewRepository reviewRepository;
    private final CountDownLatch latch = new CountDownLatch(1);

    @Before
    public void setup() {
        context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        User user = new User("ztat6FiSrNSs3fxfx0GmXRKMBlr2", null, null, null);
        reviewRepository = new ReviewRepository(context, user, new ReviewFirebaseCallback() {
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
            public void onUserReviewList(List<ContentUserReview> contentUserReviewList, String contentType) {
                assertNotNull(contentUserReviewList);
                System.out.println("-----");
                System.out.println(contentUserReviewList.size());
                for (ContentUserReview i : contentUserReviewList) {
                    System.out.println(i.getContent().getName() + " - " + i.getUser() + " - " + i.getReview());
                }
                System.out.println("-----");
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
                assertNotNull(userReview);
                assertTrue(added);
                System.out.println(userReview);
                latch.countDown();
            }

            @Override
            public void onRemovedLikeOfUserToUserReviewOfContent(UserReview userReview, boolean removed) {
                assertNotNull(userReview);
                assertTrue(removed);
                System.out.println(userReview);
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
    public void getUserReviewList() throws InterruptedException {
        reviewRepository.getUserReviewList(ContentTypeMappingManager.ContentType.MOVIE.getType(), null);
        latch.await(5, TimeUnit.SECONDS);
    }

}
